package ru.qa.rtsoft.prospector.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by korvin on 20.02.2017.
 */
public class NavigationHelper extends HelperBase{

  public NavigationHelper(ApplicationManager app) {
    super(app);
  }

  public void openSummarySettings(){
    click(By.cssSelector("a[ng-click='scope.showEditSettingsWindow()']"));
  }



  public void groupPage() {
    if (isElementPresent(By.tagName("h1"))
            && wd.findElement(By.tagName("h1")).getText().equals("Groups")
            && isElementPresent(By.name("new"))) {
      return;
    }
    click(By.linkText("groups"));
  }

  public void toHomePage() {
    if (isElementPresent(By.id("maintable"))) {
      return;
    }
    click(By.linkText("home"));
  }

  public void manageUsers(String username) {
      click(By.xpath("//i[@class='menu-icon fa fa-gears']"));
      click(By.xpath("//a[contains(@href, 'manage_user_page.php')]"));
      click(By.xpath("//a[contains(text(),'" + username + "')]"));
      click(By.cssSelector("input[value='Reset Password']"));
  }
}
