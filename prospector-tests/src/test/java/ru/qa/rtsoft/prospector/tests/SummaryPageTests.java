package ru.qa.rtsoft.prospector.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
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
    boolean gatewayStatus = hb.isElementPresent(By.xpath("//tr[.//div[. = 'Gateway Communication Status']]"));
    Assert.assertTrue(gatewayStatus);
  }

  @Test
  public void testMetersSectionPresent() {
    HelperBase hb = new HelperBase(app);
    boolean meterStatus = hb.isElementPresent(By.xpath("//tr[.//div[. = 'Meter Status']]"));
    Assert.assertTrue(meterStatus);
  }

  @Test
  public void testEventsSectionPresent() {
    HelperBase hb = new HelperBase(app);
    boolean eventsStatus = hb.isElementPresent(By.xpath("//tr[.//div[. = 'Events Status']]"));
    Assert.assertTrue(eventsStatus);
  }
}
