package ru.qa.rtsoft.prospector.tests;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.qa.rtsoft.prospector.appmanager.HelperBase;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by korvin on 17.04.2017.
 */
public class SummaryPageTests extends TestBase {

  @BeforeClass
  public void login() {
    app.session().loginAsAdmin();
  }

  @AfterClass
  public void logout() {
    app.session().logout();
  }

  @Test
  public void testGatewaysSectionPresent() {
    HelperBase hb = new HelperBase(app);
    boolean gatewaySection = hb.isElementPresent(By.xpath("//tr[.//div[. = 'Gateway Communication Status']]"));
    assertTrue(gatewaySection);
  }

  @Test
  public void testMetersSectionPresent() {
    HelperBase hb = new HelperBase(app);
    boolean meterSection = hb.isElementPresent(By.xpath("//tr[.//div[. = 'Meter Status']]"));
    assertTrue(meterSection);
  }

  @Test
  public void testEventsSectionPresent() {
    HelperBase hb = new HelperBase(app);
    boolean eventsSection = hb.isElementPresent(By.xpath("//tr[.//div[. = 'Events Status']]"));
    assertTrue(eventsSection);
  }

  @Test
  public void testGatewayPiePresent() {
    assertTrue(app.find().findGatewayPieCharts());
  }

  @Test
  public void testMeterPiePresent() {
    assertTrue(app.find().findMeterPieCharts());
  }

  @Test
  public void testEventPiePresent() {
    assertTrue(app.find().findEventPieCharts());
  }

  @Test (enabled = false)
  public void checkSummarySettings() {
    app.goTo().openSummarySettings();
    app.find().getSummarySettingsFromUI();
    int timeFrameValue = Integer.parseInt(app.find().getSummarySettingsFromUI().get(0).toString());
    int subIntervalValue = Integer.parseInt(app.find().getSummarySettingsFromUI().get(1).toString());
    String timeFrameDimention = app.find().getSummarySettingsFromUI().get(2).toString();
    String subIntervalDimention = app.find().getSummarySettingsFromUI().get(3).toString();

  }

  @Test
  public void testGatewaysLegendPresent() {
    assertTrue(app.find().findGatewaysLegend());
  }

  @Test
  public void checkGatewayBarsCount() throws InterruptedException {
    app.goTo().openSummarySettings();
    app.find().getSummarySettingsFromUI();
    int countGatewayBarsFromUI = Integer.parseInt(app.find().gatewayBarsCount().get(0).toString()) / 2; // each bar-chart has two bars
    int timeFrameInMinutes = Integer.parseInt(app.find().getSummarySettingsFromUI().get(0).toString()) * Integer.parseInt(app.find().makeMultipliers().get(0).toString());
    int subIntervalInMinutes = Integer.parseInt(app.find().getSummarySettingsFromUI().get(1).toString()) * Integer.parseInt(app.find().makeMultipliers().get(1).toString());
    int countGatewayBarsFromSettings = timeFrameInMinutes / subIntervalInMinutes;
    app.goTo().closeSummarySettingsByCancel();
    assertEquals(countGatewayBarsFromUI, countGatewayBarsFromSettings);
  }

  @Test
  public void checkGatewaySummaryCounts() throws InterruptedException, SQLException, ClassNotFoundException {
    int gatewayCountFromUI = Integer.parseInt(app.find().getGatewayCountFromUI());
    int gatewayCountFromDB = 0;
    ResultSet result = app.sql().requestResult("select count (*) as count from Gateways");
    while (result.next()) {
      gatewayCountFromDB = result.getInt("count");
    }
    assertEquals(gatewayCountFromUI, gatewayCountFromDB);
  }
}
