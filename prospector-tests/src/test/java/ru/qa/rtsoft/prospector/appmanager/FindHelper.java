package ru.qa.rtsoft.prospector.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

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

  public boolean findMeterBarLegend(){
    if (isElementPresent(By.xpath("//span[. = 'Load Profile Reads Available']"))
            && isElementPresent(By.xpath("//span[. = 'Register Reads Unavailable']"))
            && isElementPresent(By.xpath("//span[. = 'Load Profile Reads Available']"))
            && isElementPresent(By.xpath("//span[. = 'Load Profile Reads Unavailable']"))){
      return true;
    } else return false;
  }

  public boolean findEventPieLegend(){
    if (isElementPresent(By.xpath("//td[. = 'Critical']"))
            && isElementPresent(By.xpath("//td[. = 'Non-Critical']"))){
      return true;
    } else return false;
  }

  public boolean findEventBarLegend(){
    if (isElementPresent(By.xpath("//label[. = 'Gateway Events']"))
            && isElementPresent(By.xpath("//label[. = 'Device Events']"))
            && isElementPresent(By.xpath("//label[. = 'System Events']"))){
      return true;
    } else return false;
  }

  public boolean findRefreshButton(){
    if (isElementPresent(By.cssSelector("a[id='refresh']"))){
      return true;
    } else return false;
  }

  public boolean findFiltersPanel(){
    if (isElementPresent(By.cssSelector("div[class='pro-global-filter stretch ui-layout-row no-toggle']"))){
      return true;
    } else return false;
  }

  public boolean findSystemPanel() {
    if (isElementPresent(By.cssSelector("ul[class='system-menu']"))){
      return true;
    } else return false;
  }


}
