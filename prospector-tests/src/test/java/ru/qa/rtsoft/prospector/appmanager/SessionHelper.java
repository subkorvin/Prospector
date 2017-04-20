package ru.qa.rtsoft.prospector.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by korvin on 20.02.2017.
 */
public class SessionHelper extends HelperBase {

  public SessionHelper(ApplicationManager app) {
    super(app);
  }

  public void loginAsAdmin() {
    type(By.name("UserName"), app.getProperty("web.adminLogin"));
    type(By.name("Password"), app.getProperty("web.adminPassword"));
    click(By.cssSelector("input[value='Sign In']"));
  }

  public void logout() {
    click(By.cssSelector("a[ng-click='scope.logout()']"));
  }

  public void changePassword(String confirmationLink, String password) {
    wd.get(confirmationLink);
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    click(By.cssSelector("button[type='submit']"));
  }
}
