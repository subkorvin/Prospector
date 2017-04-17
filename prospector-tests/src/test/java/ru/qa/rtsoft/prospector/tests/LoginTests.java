package ru.qa.rtsoft.prospector.tests;

import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by korvin on 03.04.2017.
 */
public class LoginTests extends TestBase {

  @Test
  public void testLogin() throws IOException {
    app.session().loginAsAdmin();
//    HttpSession session = app.newSession();
//    assertTrue(session.login("v.shchapov", "123qwe"));
//    assertTrue(session.isLoggedIn("v.shchapov"));
  }
}
