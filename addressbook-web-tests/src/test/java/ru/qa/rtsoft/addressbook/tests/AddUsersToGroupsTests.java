package ru.qa.rtsoft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.qa.rtsoft.addressbook.model.GroupData;
import ru.qa.rtsoft.addressbook.model.Groups;
import ru.qa.rtsoft.addressbook.model.UserData;
import ru.qa.rtsoft.addressbook.model.Users;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by korvin on 29.03.2017.
 */
public class AddUsersToGroupsTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) { // проверяем наличие хотя бы одной группы, если нет - создаем группу
      app.goTo().groupPage();
      app.group().create(new GroupData().withGroupname("Test1").withGroupheader("Test2").withGroupfooter("Test3"));
      app.goTo().toHomePage();
    } else if (app.db().users().size() == 0) { //проверяем наличие хотя бы одного пользователя, если нет - создаем (в группу не присваиваем)
      app.user().create(new UserData()
              .withFirst_name("Vasya")
              .withMiddle_name("Yu")
              .withFamily_name("Pupkin")
              .withNickname("VasyaPro")
              .withCompany("NIICHAVO")
              .withAddress("Moscow, Leninsky tupik, 13")
              .withHome_phone("+7 435 1234567")
              .withCell_phone("+7 916 1234567")
              .withWork_phone("+7 495 1234567")
              .withEmail("vasya@pupkin.ru")
              .withPhoto(new File("src/test/resources/11698799_crop.jpg")));
    }
  }

  @Test
  public void testAddingUserToGroup() {

    Groups oldGroupsList = app.db().groups();
    Users usersBefore = app.db().users();
    UserData selectedUser = usersBefore.iterator().next();
    Groups userGroupsBefore = selectedUser.getGroups();
    int userId = selectedUser.getId();
    if (selectedUser.getGroups() == null) {
      app.user().addToGroup(selectedUser, oldGroupsList.iterator().next());
    } else {
      boolean all = true;
      for (GroupData selectedGroup : oldGroupsList) {           //пробегаем по всем существующим группам
        boolean found = false;
        for (GroupData userGroup : selectedUser.getGroups()) {  // пробегаем по всем группам, к которым принадлежит пользователь
          if (userGroup.equals(selectedGroup)) {                // сравниваем группы
            found = true;                                       // если совпали выходим из внутреннего цикла
            break;
          }
        }
        if (!found) {                                           // если не совпали - присваиваем пользователя в группу, после чего выходим из внешнего цикла
          all = false;
          app.user().addToGroup(selectedUser, selectedGroup);
          selectedUser = refreshUserData(selectedUser, userId);
          assertThat(selectedUser.getGroups().without(selectedGroup), equalTo(userGroupsBefore));
          break;
        }
      }
      if (all) {                                                // пробежали оба цикла, пользователь во всех группах
        app.goTo().groupPage();
        app.group().create(new GroupData()                      // создаем новую группу
                .withGroupname("Test_new")
                .withGroupheader("Test_new")
                .withGroupfooter("Test_new"));
        app.goTo().toHomePage();
        Groups newGroupsList = app.db().groups();                                                                                       // берем новый список групп из базы
        for (GroupData newGroup : newGroupsList) {                                                                                      // пробегаем новый список и находим группу с наибольшим ID
          if (newGroup.getId() == newGroupsList.stream().mapToInt((g) -> g.getId()).max().getAsInt()) {
            app.user().addToGroup(selectedUser, newGroup);                                                                              // присваиваем пользователя в новую группу
            selectedUser = refreshUserData(selectedUser, userId);
            assertThat(selectedUser.getGroups().without(newGroup), equalTo(userGroupsBefore));
          }
        }
      }
    }
  }
}