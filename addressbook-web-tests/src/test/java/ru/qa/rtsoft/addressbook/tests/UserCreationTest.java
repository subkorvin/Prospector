package ru.qa.rtsoft.addressbook.tests;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.qa.rtsoft.addressbook.model.GroupData;
import ru.qa.rtsoft.addressbook.model.Groups;
import ru.qa.rtsoft.addressbook.model.UserData;
import ru.qa.rtsoft.addressbook.model.Users;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class UserCreationTest extends TestBase {

  @DataProvider
  public Iterator<Object[]> validUsersFromXml() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/users.xml")))) {
      String xml = "";
      String line = reader.readLine();
      while (line != null) {
        xml += line;
        line = reader.readLine();
      }
      XStream xstream = new XStream();
      xstream.processAnnotations(UserData.class);
      List<UserData> users = (List<UserData>) xstream.fromXML(xml);
      return users.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> validUsersFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/users_1.json")))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<UserData> users = gson.fromJson(json, new TypeToken<List<UserData>>() {
      }.getType());
      return users.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }


  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) { // проверяем наличие хотя бы одной группы, если нет - создаем группу
      app.goTo().groupPage();
      app.group().create(new GroupData().withGroupname("Test1").withGroupheader("Test2").withGroupfooter("Test3"));
    }
  }

  @Test(dataProvider = "validUsersFromJson")
  public void testUserCreation(UserData user) {
    Groups groups = app.db().groups();
    GroupData selectedGroup = groups.iterator().next();
    Users before = app.db().users();
    app.user().create(user.inGroup(selectedGroup));
    Users after = app.db().users();
    assertThat(after.size(), equalTo(before.size() + 1)); //сравнение размеров списков до и после удаления
    assertThat(after, equalTo(before.withAdded(user.withId(after.stream().mapToInt((u) -> u.getId()).max().getAsInt()))));
    verifyUserListInUI();
  }
}
