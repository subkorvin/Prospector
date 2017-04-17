package ru.qa.rtsoft.addressbook.rf;

import org.openqa.selenium.remote.BrowserType;
import ru.qa.rtsoft.addressbook.appmanager.ApplicationManager;
import ru.qa.rtsoft.addressbook.model.GroupData;

import java.io.IOException;

/**
 * Created by Korvin on 16.04.2017.
 */
public class AddressbookKeywords {

  public static final String ROBOT_LIBRARY_SCOPE = "GLOBAL";

  private ApplicationManager app;

  public void initApplicationManager() throws IOException {
    app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));
    app.init();
  }

  public void stopApplicationManager() {
    app.stop();
    app = null;
  }

  public int getGroupCount() {
    app.goTo().groupPage();
    return app.group().count();
  }

  public void createGroup(String name, String header, String footer) {
    app.goTo().groupPage();
    app.group().create(new GroupData()
            .withGroupname(name)
            .withGroupheader(header)
            .withGroupfooter(footer));
  }
}
