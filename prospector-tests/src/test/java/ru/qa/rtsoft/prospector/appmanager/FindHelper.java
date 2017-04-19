package ru.qa.rtsoft.prospector.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by korvin on 20.02.2017.
 */
public class FindHelper extends HelperBase {

  public FindHelper(ApplicationManager app) {
    super(app);
  }

  public boolean findGatewayPieCharts() {
    WebElement section = wd.findElement(By.xpath("//div[. = 'Gateway Communication Status']/.."));
    WebElement pie = section.findElement(By.cssSelector("svg[class='pro-chart-pie']"));
    if (pie != null) {
      return true;
    } else {
      return false;
    }
  }

  public boolean findMeterPieCharts() {
    WebElement section = wd.findElement(By.xpath("//div[. = 'Meter Status']/.."));
    WebElement pie = section.findElement(By.cssSelector("svg[class='pro-chart-pie']"));
    if (pie != null) {
      return true;
    } else {
      return false;
    }
  }

  public boolean findEventPieCharts() {
    WebElement section = wd.findElement(By.xpath("//div[. = 'Events Status']/.."));
    WebElement pie = section.findElement(By.cssSelector("svg[class='pro-chart-pie']"));
    if (pie != null) {
      return true;
    } else {
      return false;
    }
  }

  public boolean findGatewaysLegend() {
    if (isElementPresent(By.xpath("//td[. = 'Communicated Today']")) && isElementPresent(By.xpath("//td[. = 'Not Communicated Today']"))) {
      return true;
    } else {
      return false;
    }
  }

  public String getGatewayCountFromUI() {
    String gatewayCount = wd.findElement(By.cssSelector("div[ng-bind='scope.enabledDataConcentrationsCount']")).getAttribute("innerHTML");
    return gatewayCount;
  }

  public List getSummarySettingsFromUI() {
    List<String> listSettings = new ArrayList<String>();
    String timeFrameValue = wd.findElement(By.cssSelector("input[ng-model='scope.SummaryConfiguration.TimeframeInterval']")).getAttribute("value");
    String subIntervalValue = wd.findElement(By.cssSelector("input[ng-model='scope.SummaryConfiguration.TimeframeSubInterval']")).getAttribute("value");
    WebElement timeFrameSection = wd.findElement(By.xpath("//tr[.//span[. = 'Display Timeframe:']]"));
    String timeFrameDimension = timeFrameSection.findElement(By.cssSelector("div[ng-model='scope.SummaryConfiguration.TimeframeIntervalUnit']")).getText();
    WebElement subIntervalSection = wd.findElement(By.xpath("//tr[.//span[. = 'Sub-interval:']]"));
    String subIntervalDimention = subIntervalSection.findElement(By.cssSelector("div[ng-model='scope.SummaryConfiguration.TimeframeSubIntervalUnit']")).getText();
    listSettings.add(timeFrameValue);
    listSettings.add(subIntervalValue);
    listSettings.add(timeFrameDimension);
    listSettings.add(subIntervalDimention);
    return listSettings;
  }

  public int gatewayBars() throws InterruptedException {
    Thread.sleep(3000);
//    WebDriverWait wait = new WebDriverWait(wd, 10/*seconds*/);
//    WebElement element = wait.until((WebDriver d) -> d.findElement(By.cssSelector("a[ng-click='scope.openprospectorMenu()']")));
    WebElement barSection = wd.findElement(By.cssSelector("svg[chart-data='scope.cumulativeDcCommunicationStatus']"));
    List<WebElement> bars = barSection.findElements(By.cssSelector("rect"));
    int count = 0;
    for (WebElement bar : bars) {
      int height = Integer.parseInt(bar.getAttribute("height"));
      if (height > 0) {
        count++;
      }
    }
    return count;
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
