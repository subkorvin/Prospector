package ru.qa.rtsoft.prospector.tests;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.qa.rtsoft.prospector.appmanager.HelperBase;

import static org.testng.Assert.assertTrue;

/**
 * Created by korvin on 17.04.2017.
 */
public class SummaryPageTests extends TestBase {

  @BeforeClass
  public void login() {
    app.session().loginAsAdmin();
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
    System.out.println(timeFrameValue + " " + timeFrameDimention);
    System.out.println(subIntervalValue + " " + subIntervalDimention);
  }

  @Test
  public void testGatewaysLegendPresent(){
    assertTrue(app.find().findGatewaysLegend());
  }

  @Test
  public void barsCount() throws InterruptedException {
  int count = app.find().gatewayBars();
    System.out.println(count);
  }

  @Test (enabled = false)
  public void checkSummaryCounts() {
    String gatewayCount = app.find().getGatewayCountFromUI();
    System.out.println(gatewayCount);
  }
}
