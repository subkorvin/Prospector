package ru.qa.rtsoft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;
import ru.qa.rtsoft.addressbook.appmanager.ApplicationManager;
import ru.qa.rtsoft.addressbook.model.GroupData;
import ru.qa.rtsoft.addressbook.model.Groups;
import ru.qa.rtsoft.addressbook.model.UserData;
import ru.qa.rtsoft.addressbook.model.Users;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by korvin on 20.02.2017.
 */

@Listeners(TestsListener.class)
public class TestBase {

  Logger logger = LoggerFactory.getLogger(TestBase.class);


  protected static ApplicationManager app;

  @BeforeSuite
  public void setUp(ITestContext context) throws Exception {
    app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));
    app.init();
    context.setAttribute("app", app);
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() {
    app.stop();
  }


  public static ApplicationManager getApp() {
    return app;
  }

  @BeforeMethod
  public void logTestStart(Method m, Object[] p) {
    logger.info("Start test " + m.getName() + " with parameters " + Arrays.asList(p));
  }

  @AfterMethod(alwaysRun = true)
  public void logTestStop(Method m, Object[] p) {
    logger.info("Stop test " + m.getName() + " with parameters " + Arrays.asList(p));
  }

  public void verifyGroupListInUI() {
    if (Boolean.getBoolean("verifyUI")) {
      Groups dbGroups = app.db().groups();
      Groups uiGroups = app.group().set();
      assertThat(uiGroups, equalTo(dbGroups.stream()
              .map((g) -> new GroupData().withId(g.getId()).withGroupname(g.getGroupname()))
              .collect(Collectors.toSet())));
    }
  }

  public void verifyUserListInUI() {
    if (Boolean.getBoolean("verifyUI")) {
      Users dbUsers = app.db().users();
      Users uiUsers = app.user().set();
      assertThat(uiUsers, equalTo(dbUsers.stream()
              .map((u) -> new UserData()
                      .withId(u.getId())
                      .withFirst_name(u.getFirst_name())
                      .withFamily_name(u.getFamily_name())
                      .withAddress(u.getAddress())
                      .withAllPhones(u.getHome_phone() + u.getCell_phone() + u.getWork_phone())
                      .withAllEmails(u.getEmail() + u.getEmail2() + u.getEmail3()))
              .collect(Collectors.toSet())));
    }
  }

  protected UserData refreshUserData(UserData selectedUser, int userId) {
    Users usersAfter = app.db().users();
    for (UserData user : usersAfter) {
      if (user.getId() == userId) {
        selectedUser = user;
      }
    }
    return selectedUser;
  }
}
