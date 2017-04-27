package ru.qa.rtsoft.prospector.tests;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.qa.rtsoft.prospector.appmanager.HelperBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by korvin on 17.04.2017.
 */
public class SummaryPageTests extends TestBase {

  @BeforeClass
  public void login() throws InterruptedException {
    app.session().loginAsAdmin();
    Thread.sleep(3000);
  }

  @AfterClass
  public void logout() {
    app.session().logout();
  }


  // Sections
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

  //Pie charts
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


  //Pie charts legends
  @Test
  public void testGatewayPieLegendPresent() {
    assertTrue(app.find().findGatewayPieLegend());
  }

  @Test
  public void testGatewayBarsLegendPresent() {
    assertTrue(app.find().findGatewayBarsLegend());
  }

  @Test
  public void testMeterPieLegendPresent() {
    assertTrue(app.find().findMeterPieLegend());
  }



  @Test(enabled = false)
  public void checkSummarySettings() {
    app.goTo().openSummarySettings();
    app.data().getSummarySettings();
    int timeFrameValue = Integer.parseInt(app.data().getSummarySettings().get(0).toString());
    int subIntervalValue = Integer.parseInt(app.data().getSummarySettings().get(1).toString());
    String timeFrameDimention = app.data().getSummarySettings().get(2).toString();
    String subIntervalDimention = app.data().getSummarySettings().get(3).toString();

  }

  // Bars
  @Test
  public void checkGatewayBarsCount() throws InterruptedException {
    app.goTo().openSummarySettings();
    int countGatewayBarsFromUI = Integer.parseInt(app.data()
            .getBarsCountFromUI(By.cssSelector("svg[chart-data='scope.cumulativeDcCommunicationStatus']"))
            .get(0).toString()) / 2; // делим пополам потому что каждый бар состоит из двух частей
    int barCountFromSettings = app.data().getBarChartCount();
    app.goTo().closeSummarySettingsByCancel();
    assertEquals(countGatewayBarsFromUI, barCountFromSettings);
  }

  @Test
  public void checkMeterBarsCount() throws InterruptedException {
    app.goTo().openSummarySettings();
    List<Boolean> profilesFromSettings = app.data().getMeterProfilesFromSettings();
    List<Boolean> profilesFiltered = new ArrayList<Boolean>();
    for (int i=0; i < profilesFromSettings.size(); i++ ){
      boolean count = profilesFromSettings.get(i);
      if (count) {
        profilesFiltered.add(profilesFromSettings.get(i));
      }
    }
    int countMeterBarsFromUI = Integer.parseInt(app.data()
            .getBarsCountFromUI(By.cssSelector("svg[chart-data='scope.cumulativeMeterData']"))
            .get(0).toString()) / (profilesFiltered.size() + 1) / 2; // дополнительный бар - количество счетчиков, делим на 2 потому что каждый бар состоит из двух частей
    int barCountFromSettings = app.data().getBarChartCount();
    app.goTo().closeSummarySettingsByCancel();
    assertEquals(countMeterBarsFromUI, barCountFromSettings);
  }

  @Test
  public void checkMeterProfilesState() throws InterruptedException {
    app.goTo().openSummarySettings();
    List<Boolean> profilesFromSettings = app.data().getMeterProfilesFromSettings();
    app.goTo().closeSummarySettingsByCancel();
    List<Boolean> profilesFromUI = app.data().getMeterProfilesFromUI();
    assertEquals(profilesFromSettings, profilesFromUI);
  }


  // Counts
  @Test
  public void checkGatewaySummaryCount() throws InterruptedException, SQLException, ClassNotFoundException {
    int gatewayCountFromUI = Integer.parseInt(app.data().getCountsFromUI(By.cssSelector("div[ng-bind='scope.enabledDataConcentrationsCount']")));
    int gatewayCountFromDB = 0;
    ResultSet result = app.sql().requestResult("select count (*) as count from Gateways");
    while (result.next()) {
      gatewayCountFromDB = result.getInt("count");
    }
    assertEquals(gatewayCountFromUI, gatewayCountFromDB);
  }

  @Test
  public void checkMeterSummaryCount() throws InterruptedException, SQLException, ClassNotFoundException {
    int meterCountFromUI = Integer.parseInt(app.data().getCountsFromUI(By.cssSelector("div[ng-bind='scope.metersCount']")));
    int meterCountFromDB = 0;
    ResultSet result = app.sql().requestResult("select count (*) as count from Devices_Meter");
    while (result.next()) {
      meterCountFromDB = result.getInt("count");
    }
    assertEquals(meterCountFromUI, meterCountFromDB);
  }

  @Test
  public void checkEventsCount() throws InterruptedException, SQLException, ClassNotFoundException {
    int eventsCountFromUI = Integer.parseInt(app.data().getCountsFromUI(By.cssSelector("div[ng-bind='scope.eventsCount']")));
    int eventsCountFromDB = 0;
    ResultSet result = app.sql().requestResult("SELECT count (*) as count FROM EventHistory");
    while (result.next()) {
      eventsCountFromDB = result.getInt("count");
    }
    assertEquals(eventsCountFromUI, eventsCountFromDB);
  }

  @Test
  public void checkCriticalEventsCount() throws SQLException, ClassNotFoundException, InterruptedException {
    int criticalEventsCountFromUI = Integer.parseInt(app.data().getCountsFromUI(By.cssSelector("div[ng-bind='scope.criticalEventsCount']")));
    int criticalEventsCountFromDB = 0;
    ResultSet result = app.sql().requestResult("SELECT count (*) as count\n" +
            "FROM\t[EventHistory]\n" +
            "\tINNER JOIN [www].[CriticalEventDefinitions] ON [CriticalEventDefinitions].[EventDefinitionID]=[EventHistory].[EventDefinitionID]\n" +
            "WHERE (CriticalEventDefinitions.[IsCritical] = 1)");
    while (result.next()) {
      criticalEventsCountFromDB = result.getInt("count");
    }
    assertEquals(criticalEventsCountFromUI, criticalEventsCountFromDB);
  }


  //Toolbar items

  @Test
  public void checkRefreshButton() {

  }
}
