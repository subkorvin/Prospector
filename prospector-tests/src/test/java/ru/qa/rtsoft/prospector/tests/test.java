package ru.qa.rtsoft.prospector.tests;

import org.testng.annotations.Test;
import ru.qa.rtsoft.prospector.appmanager.ApplicationManager;
import ru.qa.rtsoft.prospector.appmanager.HelperBase;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class test extends TestBase{

  @Test
  public void meterProfiles() throws InterruptedException, AWTException {
    app.session().loginAsAdmin();
    Thread.sleep(3000);
    HelperBase hb = new HelperBase(app);

    System.out.println(hb.isHierarchySelected());
    getApp().wd.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//    String pro = app.find().getMeterProfilesFromUI();
//    System.out.println(pro);
  }
}
