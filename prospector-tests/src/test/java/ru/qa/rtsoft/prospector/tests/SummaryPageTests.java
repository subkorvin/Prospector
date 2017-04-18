package ru.qa.rtsoft.prospector.tests;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.qa.rtsoft.prospector.appmanager.HelperBase;

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
    if (gatewaySection) {
      System.out.println("Gateways section is present");
    } else {
      System.out.println("Error! Gateways section is absent!");
    }
  }

  @Test
  public void testMetersSectionPresent() {
    HelperBase hb = new HelperBase(app);
    boolean meterSection = hb.isElementPresent(By.xpath("//tr[.//div[. = 'Meter Status']]"));
    if (meterSection) {
      System.out.println("Meters section is present");
    } else {
      System.out.println("Error! Meters section is absent!");
    }
  }

  @Test
  public void testEventsSectionPresent() {
    HelperBase hb = new HelperBase(app);
    boolean eventsSection = hb.isElementPresent(By.xpath("//tr[.//div[. = 'Events Status']]"));
    if (eventsSection) {
      System.out.println("Events section is present");
    } else {
      System.out.println("Error! Events section is absent!");
    }
  }

  @Test
  public void testGatewayPiePresent() {
    if (app.find().findGatewayPieCharts()) {
      System.out.println("Gateway pie chart is present");
    } else {
      System.out.println("Gateway pie chart is absent!");
    }
  }

  @Test
  public void testMeterPiePresent() {
    if (app.find().findMeterPieCharts()) {
      System.out.println("Meter pie chart is present");
    } else {
      System.out.println("Meter pie chart is absent!");
    }
  }

  @Test
  public void testEventPiePresent() {
    if (app.find().findEventPieCharts()) {
      System.out.println("Event pie chart is present");
    } else {
      System.out.println("Event pie chart is absent!");
    }
  }

  @Test
  public void checkSummarySettings() {
    app.goTo().openSummarySettings();
    app.find().getSummarySettings();
    String time = app.find().getSummarySettings().get(0).toString();
    String sub = app.find().getSummarySettings().get(1).toString();
    System.out.println(time + "\n");
    System.out.println(sub);
  }
}
