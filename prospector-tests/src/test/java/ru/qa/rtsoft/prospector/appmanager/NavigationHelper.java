package ru.qa.rtsoft.prospector.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.awt.*;

/**
 * Created by korvin on 20.02.2017.
 */
public class NavigationHelper extends HelperBase {

  public NavigationHelper(ApplicationManager app) {
    super(app);
  }

  public void openSummarySettings() {
    click(By.cssSelector("a[ng-click='scope.showEditSettingsWindow()']"));
  }

  public void closeSummarySettingsByCancel() {
    click(By.cssSelector("button[ng-click='scope.close()']"));
  }

  public void openUserSettings() throws AWTException, InterruptedException {
    click(By.cssSelector("a[ng-click='scope.openprospectorMenu()']"));
    click(By.xpath("//li[./*[text()[contains(.,'General Settings')]]]"));
    WebElement generalSettings = wd.findElement(By.xpath("//li[./*[text()[contains(.,'General Settings')]]]"));
    generalSettings.findElement(By.xpath("//a[text()[contains(.,'General Settings')]]")).click();
    WebElement element = generalSettings.findElement(By.xpath("//ul[@ng-mouseleave='closeSubMenu(1)']"));
    element.findElement(By.xpath("//li[./a[text()[contains(.,'User Preferences')]]][@class='pro-list-item ng-scope']")).click();
  }

  public void clickRefreshButton(){
    click(By.cssSelector("a[id='refresh']"));
  }

  public void clickFiltersPanelToggle(){
    click(By.xpath("//span[. = 'Filters']"));
  }

  public void clickSystemPanelToggle(){
    click(By.xpath("//span[. = 'System']"));
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
