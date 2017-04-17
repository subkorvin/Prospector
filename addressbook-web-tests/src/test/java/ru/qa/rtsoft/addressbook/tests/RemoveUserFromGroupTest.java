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
 * Created by Korvin on 02.04.2017.
 */
public class RemoveUserFromGroupTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) { // проверяем наличие хотя бы одной группы, если нет - создаем группу
      app.goTo().groupPage();
      app.group().create(new GroupData().withGroupname("Test1").withGroupheader("Test2").withGroupfooter("Test3"));
      app.goTo().toHomePage();
    } else if (app.db().users().size() == 0) { //проверяем наличие хотя бы одного пользователя, если нет - создаем и присваиваем в группу
      Groups groups = app.db().groups();
      GroupData selectedGroup = groups.iterator().next();
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
              .inGroup(selectedGroup)
              .withPhoto(new File("src/test/resources/11698799_crop.jpg")));
    }
  }

  @Test
  public void testRemovingUserToGroup() {

    Users usersBefore = app.db().users();
    Groups groups = app.db().groups();
    UserData selectedUser = usersBefore.iterator().next();
    int userId = selectedUser.getId();
    Groups userGroupsBefore = selectedUser.getGroups();
    if (userGroupsBefore.size() == 0){
      app.user().addToGroup(selectedUser, groups.iterator().next());
      selectedUser = refreshUserData(selectedUser, userId);
      userGroupsBefore = selectedUser.getGroups();
    }
    GroupData selectedGroup = selectedUser.getGroups().iterator().next();
    app.goTo().toHomePage();
    app.user().removeFromGroup(selectedUser, selectedGroup);
    selectedUser = refreshUserData(selectedUser, userId);
    assertThat(selectedUser.getGroups().withAdded(selectedGroup), equalTo(userGroupsBefore));
  }

}