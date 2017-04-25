package ru.qa.rtsoft.prospector.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.lanwen.verbalregex.VerbalExpression;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    } else return false;
  }

  public boolean findMeterPieCharts() {
    WebElement section = wd.findElement(By.xpath("//div[. = 'Meter Status']/.."));
    WebElement pie = section.findElement(By.cssSelector("svg[class='pro-chart-pie']"));
    if (pie != null) {
      return true;
    } else return false;
  }

  public boolean findEventPieCharts() {
    WebElement section = wd.findElement(By.xpath("//div[. = 'Events Status']/.."));
    WebElement pie = section.findElement(By.cssSelector("svg[class='pro-chart-pie']"));
    if (pie != null) {
      return true;
    } else return false;
  }

  public boolean findGatewayPieLegend() {
    if (isElementPresent(By.xpath("//td[. = 'Communicated Today']")) && isElementPresent(By.xpath("//td[. = 'Not Communicated Today']"))) {
      return true;
    } else return false;
  }

  public boolean findGatewayBarsLegend() {
    if (isElementPresent(By.xpath("//span[. = 'Succeeded']")) && isElementPresent(By.xpath("//span[. = 'Failed']"))) {
      return true;
    } else return false;
  }

  public String getCountsFromUI(By locator) throws InterruptedException {
    String gatewayCount = wd.findElement(locator).getAttribute("innerHTML");
    return gatewayCount;
  }

  public List<Boolean> getMeterProfilesFromUI() throws InterruptedException {
    Thread.sleep(3000);
    WebElement foundElement = null;
    for (WebElement element : wd.findElements(By.xpath("//span[@class='ng-binding']"))) {
      String innerHTML = element.getAttribute("innerHTML");
      if(innerHTML.contains("Load Profiles")) {
        foundElement = element;
        break;
      }
    }
    String meterProfilesFromUI = foundElement.getAttribute("innerHTML");
    VerbalExpression primary = VerbalExpression.regex().find("Primary").build();
    VerbalExpression first = VerbalExpression.regex().find("1").build();
    VerbalExpression second = VerbalExpression.regex().find("2").build();
    VerbalExpression third = VerbalExpression.regex().find("3").build();
    VerbalExpression fourth = VerbalExpression.regex().find("4").build();
    boolean primaryPresent;
    boolean firstPresent;
    boolean secondPresent;
    boolean thirdPresent;
    boolean fourthPresent;
    if (Objects.equals(primary.getText(meterProfilesFromUI), "Primary")){
      primaryPresent = true;
    } else primaryPresent = false;
    if (Objects.equals(first.getText(meterProfilesFromUI), "1")){
      firstPresent = true;
    } else firstPresent = false;
    if (Objects.equals(second.getText(meterProfilesFromUI), "2")){
      secondPresent = true;
    } else secondPresent = false;
    if (Objects.equals(third.getText(meterProfilesFromUI), "3")){
      thirdPresent = true;
    } else thirdPresent = false;
    if (Objects.equals(fourth.getText(meterProfilesFromUI), "4")){
      fourthPresent = true;
    } else fourthPresent = false;
    List<Boolean> profiles = new ArrayList<Boolean>();
    profiles.add(primaryPresent);
    profiles.add(firstPresent);
    profiles.add(secondPresent);
    profiles.add(thirdPresent);
    profiles.add(fourthPresent);
    return profiles;
  }

  public String getMeterCountFromUI() throws InterruptedException {
    Thread.sleep(3000);
    String meterCount = wd.findElement(By.cssSelector("div[ng-bind='scope.metersCount']")).getAttribute("innerHTML");
    return meterCount;
  }

  public List getSummarySettings() {
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

  public int barChartCount() {
    String timeFrameDimension = getSummarySettings().get(2).toString();
    String subIntervalDimention = getSummarySettings().get(3).toString();
    int timeFrameDimensionMultiplier = 0;
    int subIntervalDimentionMultiplier = 0;
    switch (timeFrameDimension) {
      case "Minute(s)":
        timeFrameDimensionMultiplier = 1;
        break;
      case "Hour(s)":
        timeFrameDimensionMultiplier = 60;
        break;
      case "Day(s)":
        timeFrameDimensionMultiplier = 60 * 24;
        break;
    }
    switch (subIntervalDimention) {
      case "Minute(s)":
        subIntervalDimentionMultiplier = 1;
        break;
      case "Hour(s)":
        subIntervalDimentionMultiplier = 60;
        break;
      case "Day(s)":
        subIntervalDimentionMultiplier = 60 * 24;
        break;
    }
    int timeFrame = Integer.parseInt(getSummarySettings().get(0).toString()) * timeFrameDimensionMultiplier;
    int subInterval = Integer.parseInt(getSummarySettings().get(1).toString()) * subIntervalDimentionMultiplier;
    int barsChartCount = timeFrame / subInterval;
    return barsChartCount;
  }

  public List barsCountFromUI(By locator) throws InterruptedException {
    Thread.sleep(3000);
//    WebDriverWait wait = new WebDriverWait(wd, 10/*seconds*/);
//    WebElement element = wait.until((WebDriver d) -> d.findElement(By.cssSelector("svg[chart-data='scope.cumulativeDcCommunicationStatus']")));
    List<Integer> counts = new ArrayList<Integer>();
    WebElement barSection = wd.findElement(locator);
//    WebElement barSection = wd.findElement(By.cssSelector("svg[chart-data='scope.cumulativeDcCommunicationStatus']"));
    List<WebElement> bars = barSection.findElements(By.cssSelector("rect"));
    int countFull = bars.size();
    int countEnabled = 0;
    for (WebElement bar : bars) {
      int height = Integer.parseInt(bar.getAttribute("height"));
      if (height > 0) {
        countEnabled++;
      }
    }
    counts.add(countFull);
    counts.add(countEnabled);
    return counts;
  }

//  public List meterBarsCount() throws InterruptedException {
//    Thread.sleep(3000);
//    List<Integer> counts = new ArrayList<Integer>();
//    WebElement barSection = wd.findElement(By.cssSelector("svg[chart-data='scope.cumulativeMeterData']"));
//    List<WebElement> bars = barSection.findElements(By.cssSelector("rect"));
//    int countFull = bars.size();
//    int countEnabled = 0;
//    for (WebElement bar : bars) {
//      int height = Integer.parseInt(bar.getAttribute("height"));
//      if (height > 0) {
//        countEnabled++;
//      }
//    }
//    counts.add(countFull);
//    counts.add(countEnabled);
//    return counts;
//  }

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

  public boolean findMeterPieLegend() {
    if (isElementPresent(By.cssSelector("td[class='legend-box'][title^='Enabled:']"))
            && isElementPresent(By.cssSelector("td[class='pro-page-summary-chart-legenda cursor-pointer'][title^='Enabled:']"))
            && isElementPresent(By.cssSelector("td[class='legend-box'][title^='Add Pending:']"))
            && isElementPresent(By.cssSelector("td[class='pro-page-summary-chart-legenda cursor-pointer'][title^='Add Pending:']"))
            && isElementPresent(By.cssSelector("td[class='legend-box'][title^='Commissioned:']"))
            && isElementPresent(By.cssSelector("td[class='pro-page-summary-chart-legenda cursor-pointer'][title^='Commissioned:']"))
            && isElementPresent(By.cssSelector("td[class='legend-box'][title^='Add Failed:']"))
            && isElementPresent(By.cssSelector("td[class='pro-page-summary-chart-legenda cursor-pointer'][title^='Add Failed:']"))
            && isElementPresent(By.cssSelector("td[class='legend-box'][title^='Remove Pending:']"))
            && isElementPresent(By.cssSelector("td[class='pro-page-summary-chart-legenda cursor-pointer'][title^='Remove Pending:']"))) {
      return true;
    } else return false;
  }



  public List summarySettingsCheckboxesState() {
    List<String> summarySettingsCheckboxes = new ArrayList<String>();
    String showAllGateways = null;
    String showDCN3000 = null;
    String showDCN1000 = null;
    String showUnknownDC = null;
    String showAllMeters = null;
    String showCTMeters = null;
    String showCPM = null;
    String showMTR = null;
    String showUnknownMeters = null;
    if (isElementChecked(By.cssSelector("input[type='checkbox'][ng-model^='scope.SummaryConfiguration'][ng-model='scope.SummaryConfiguration.ShowAllGateways']"))) {
      showAllGateways = "checked";
    } else showAllGateways = "unchecked";
    if (isElementDisabled(By.cssSelector("input[type='checkbox'][ng-model^='scope.SummaryConfiguration'][ng-model='scope.SummaryConfiguration.ShowDCN3000']"))) {
      showDCN3000 = "disabled";
    } else if (isElementChecked(By.cssSelector("input[type='checkbox'][ng-model^='scope.SummaryConfiguration'][ng-model='scope.SummaryConfiguration.ShowDCN3000']"))) {
      showDCN3000 = "checked";
    } else showDCN3000 = "unchecked";
    if (isElementDisabled(By.cssSelector("input[type='checkbox'][ng-model^='scope.SummaryConfiguration'][ng-model='scope.SummaryConfiguration.ShowDCN1000']"))){
      showDCN1000 = "disabled";
    } else if (isElementChecked(By.cssSelector("input[type='checkbox'][ng-model^='scope.SummaryConfiguration'][ng-model='scope.SummaryConfiguration.ShowDCN1000']"))){
      showDCN1000 = "checked";
    } else showDCN1000 = "unchecked";
    if (isElementDisabled(By.cssSelector("input[type='checkbox'][ng-model^='scope.SummaryConfiguration'][ng-model='scope.SummaryConfiguration.ShowUnknownDCs']"))){
      showUnknownDC = "disabled";
    } else if (isElementChecked(By.cssSelector("input[type='checkbox'][ng-model^='scope.SummaryConfiguration'][ng-model='scope.SummaryConfiguration.ShowUnknownDCs']"))){
      showUnknownDC = "checked";
    } else showUnknownDC = "unchecked";
    if (isElementChecked(By.cssSelector("input[type='checkbox'][ng-model^='scope.SummaryConfiguration'][ng-model='scope.SummaryConfiguration.ShowAllMeters']"))) {
      showAllMeters = "checked";
    } else showAllMeters = "unchecked";
    if (isElementDisabled(By.cssSelector("input[type='checkbox'][ng-model^='scope.SummaryConfiguration'][ng-model='scope.SummaryConfiguration.ShowCTMeter']"))) {
      showCTMeters = "disabled";
    } else if (isElementChecked(By.cssSelector("input[type='checkbox'][ng-model^='scope.SummaryConfiguration'][ng-model='scope.SummaryConfiguration.ShowCTMeter']"))) {
      showCTMeters = "checked";
    } else showCTMeters = "unchecked";
    if (isElementDisabled(By.cssSelector("input[type='checkbox'][ng-model^='scope.SummaryConfiguration'][ng-model='scope.SummaryConfiguration.ShowCPM']"))) {
      showCPM = "disabled";
    } else if (isElementChecked(By.cssSelector("input[type='checkbox'][ng-model^='scope.SummaryConfiguration'][ng-model='scope.SummaryConfiguration.ShowCPM']"))) {
      showCPM = "checked";
    } else showCPM = "unchecked";
    if (isElementDisabled(By.cssSelector("input[type='checkbox'][ng-model^='scope.SummaryConfiguration'][ng-model='scope.SummaryConfiguration.ShowMTR']"))) {
      showMTR = "disabled";
    } else if (isElementChecked(By.cssSelector("input[type='checkbox'][ng-model^='scope.SummaryConfiguration'][ng-model='scope.SummaryConfiguration.ShowMTR']"))) {
      showMTR = "checked";
    } else showMTR = "unchecked";
    if (isElementDisabled(By.cssSelector("input[type='checkbox'][ng-model^='scope.SummaryConfiguration'][ng-model='scope.SummaryConfiguration.ShowUnknownMeters']"))) {
      showUnknownMeters = "disabled";
    } else if (isElementChecked(By.cssSelector("input[type='checkbox'][ng-model^='scope.SummaryConfiguration'][ng-model='scope.SummaryConfiguration.ShowUnknownMeters']"))) {
      showUnknownMeters = "checked";
    } else showUnknownMeters = "unchecked";
    summarySettingsCheckboxes.add(showAllGateways);
    summarySettingsCheckboxes.add(showDCN3000);
    summarySettingsCheckboxes.add(showDCN1000);
    summarySettingsCheckboxes.add(showUnknownDC);
    summarySettingsCheckboxes.add(showAllMeters);
    summarySettingsCheckboxes.add(showCTMeters);
    summarySettingsCheckboxes.add(showCPM);
    summarySettingsCheckboxes.add(showMTR);
    summarySettingsCheckboxes.add(showUnknownMeters);
    return summarySettingsCheckboxes;
  }

  public List<Boolean> meterProfilesFromSettings() {
    List<Boolean> profiles = new ArrayList<Boolean>();
    boolean profilePrimary;
    boolean profile1;
    boolean profile2;
    boolean profile3;
    boolean profile4;
    if (isElementChecked(By.cssSelector("input[type='checkbox'][ng-model^='scope.SummaryConfiguration'][ng-model='scope.SummaryConfiguration.UseProfilePrimary']"))) {
      profilePrimary = true;
    } else profilePrimary = false;
    if (isElementChecked(By.cssSelector("input[type='checkbox'][ng-model^='scope.SummaryConfiguration'][ng-model='scope.SummaryConfiguration.UseProfile1']"))) {
      profile1 = true;
    } else profile1 = false;
    if (isElementChecked(By.cssSelector("input[type='checkbox'][ng-model^='scope.SummaryConfiguration'][ng-model='scope.SummaryConfiguration.UseProfile2']"))) {
      profile2 = true;
    } else profile2 = false;
    if (isElementChecked(By.cssSelector("input[type='checkbox'][ng-model^='scope.SummaryConfiguration'][ng-model='scope.SummaryConfiguration.UseProfile3']"))) {
      profile3 = true;
    } else profile3 = false;
    if (isElementChecked(By.cssSelector("input[type='checkbox'][ng-model^='scope.SummaryConfiguration'][ng-model='scope.SummaryConfiguration.UseProfile4']"))) {
      profile4 = true;
    } else profile4 = false;
    profiles.add(profilePrimary);
    profiles.add(profile1);
    profiles.add(profile2);
    profiles.add(profile3);
    profiles.add(profile4);
    return profiles;
  }
}
