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
import static org.testng.Assert.assertEquals;

/**
 * Created by korvin on 21.02.2017.
 */
public class UserModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) { // проверяем наличие хотя бы одной группы, если нет - создаем группу
      app.goTo().groupPage();
      app.group().create(new GroupData().withGroupname("Test1").withGroupheader("Test2").withGroupfooter("Test3"));
      app.goTo().toHomePage();
    }
  }


  @Test
  public void testUserModification() {
    /*
    * проверяем наличие хотя бы одного пользователя
    * если пользователь есть - модифицируем
    * если нет - создаем пользователя
    */
    if (app.db().users().size() != 0) {
      Users before = app.db().users();
      UserData modifiedUser = before.iterator().next();
      UserData user = new UserData()
              .withId(modifiedUser.getId())
              .withFirst_name("Petya")
              .withMiddle_name("A")
              .withFamily_name("Ivanov")
              .withNickname("PIvanov")
              .withCompany("RTSoft")
              .withAddress("Tula, tup. Kommunizma, 13")
              .withHome_phone("+7 815 1234567")
              .withCell_phone("+7 812 1452365")
              .withWork_phone("+7 845 2365486")
              .withEmail("p_ivanov@microsoft.com")
              .withEmail2("")
              .withEmail3("")
              .withPhoto(new File("src/test/resources/35_45.jpg"));
      app.user().modify(user);
      assertEquals(app.user().count(), before.size()); //сравнение размеров списков до и после удаления
      Users after = app.db().users();
      assertThat(after, equalTo(before.without(modifiedUser).withAdded(user)));
      verifyUserListInUI();
    } else {
        /*
        * пользователя нет,
        * создаем пользователя и модифицируем его
        */
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
      Users before = app.db().users();
      UserData modifiedUser = before.iterator().next();
      UserData user = new UserData()
              .withId(modifiedUser.getId())
              .withFirst_name("Petya")
              .withMiddle_name("A")
              .withFamily_name("Ivanov")
              .withNickname("PIvanov")
              .withCompany("RTSoft")
              .withAddress("Tula, tup. Kommunizma, 13")
              .withHome_phone("+7 815 1234567")
              .withCell_phone("+7 812 1452365")
              .withWork_phone("+7 845 2365486")
              .withEmail("p_ivanov@microsoft.com")
              .withEmail2("")
              .withEmail3("")
              .withPhoto(new File("src/test/resources/11698799_crop.jpg"));
      app.user().modify(user);
      assertEquals(app.user().count(), before.size()); //сравнение размеров списков до и после удаления
      Users after = app.db().users();
      assertThat(after, equalTo(before.without(modifiedUser).withAdded(user)));
      verifyUserListInUI();
    }
  }
}
