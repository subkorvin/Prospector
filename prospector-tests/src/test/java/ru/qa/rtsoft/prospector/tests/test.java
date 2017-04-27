package ru.qa.rtsoft.prospector.tests;

import org.testng.annotations.Test;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class test extends TestBase{

  @Test
  public void meterProfiles() throws InterruptedException, AWTException {
    app.session().loginAsAdmin();
    Thread.sleep(3000);
    app.data().getUserSettings();
//    String pro = app.find().getMeterProfilesFromUI();
//    System.out.println(pro);
  }
}
