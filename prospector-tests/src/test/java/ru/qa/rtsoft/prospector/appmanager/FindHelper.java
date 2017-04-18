package ru.qa.rtsoft.prospector.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by korvin on 20.02.2017.
 */
public class FindHelper extends HelperBase{

  public FindHelper(ApplicationManager app) {
    super(app);
  }

  public boolean findGatewayPieCharts() {
    WebElement section = wd.findElement(By.xpath("//div[. = 'Gateway Communication Status']/.."));
    WebElement pie = section.findElement(By.cssSelector("svg[class='pro-chart-pie']"));
    if (pie != null){
      return true;
    } else {
      return false;
    }
  }

  public boolean findMeterPieCharts() {
    WebElement section = wd.findElement(By.xpath("//div[. = 'Meter Status']/.."));
    WebElement pie = section.findElement(By.cssSelector("svg[class='pro-chart-pie']"));
    if (pie != null){
      return true;
    } else {
      return false;
    }
  }

  public boolean findEventPieCharts() {
    WebElement section = wd.findElement(By.xpath("//div[. = 'Events Status']/.."));
    WebElement pie = section.findElement(By.cssSelector("svg[class='pro-chart-pie']"));
    if (pie != null){
      return true;
    } else {
      return false;
    }
  }

  public List getSummarySettings(){
    List<String> listSettings = new ArrayList<String>();
    String timeFrame = wd.findElement(By.cssSelector("input[ng-model='scope.SummaryConfiguration.TimeframeInterval']")).getAttribute("value");
    String subInterval = wd.findElement(By.cssSelector("input[ng-model='scope.SummaryConfiguration.TimeframeSubInterval']")).getAttribute("value");
    listSettings.add(timeFrame);
    listSettings.add(subInterval);
    return listSettings;
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

  public void mainPage() {

  }
}
