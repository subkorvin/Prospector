package ru.qa.rtsoft.prospector.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by korvin on 20.02.2017.
 */
public class ApplicationManager {

  private final Properties properties;
  public WebDriver wd;

  private String browser;
  private SessionHelper sessionHelper;
  private NavigationHelper navigationHelper;
  private SqlServerDbHelper sqlServerDbHelper;
  private FindHelper findHelper;
  private DataHelper dataHelper;


  public ApplicationManager(String browser) {
    this.browser = browser;
    properties = new Properties();
  }


  public void init() throws IOException {
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }

  public void stop() {
    if (wd != null) {
      wd.quit();
    }
  }

  public String getProperty(String key) {
    return properties.getProperty(key);
  }


  public SessionHelper session() {
    if (sessionHelper == null) {
      sessionHelper = new SessionHelper(this);
    }
    return sessionHelper;
  }

  public FindHelper find() {
    if (findHelper == null) {
      findHelper = new FindHelper(this);
    }
    return findHelper;
  }

  public NavigationHelper goTo() {
    if (navigationHelper == null) {
      navigationHelper = new NavigationHelper(this);
    }
    return navigationHelper;
  }

  public DataHelper data(){
    if (dataHelper == null) {
      dataHelper = new DataHelper(this);
    }
    return dataHelper;
  }


  public SqlServerDbHelper sql() throws SQLException, ClassNotFoundException {
    if (sqlServerDbHelper == null) {
      sqlServerDbHelper = new SqlServerDbHelper(this);
    }
    return sqlServerDbHelper;
  }

  public WebDriver getDriver() {
    if (wd == null) {
      if (Objects.equals(browser, BrowserType.FIREFOX)) {
        wd = new FirefoxDriver();
        wd.manage().window().maximize();
      } else if (Objects.equals(browser, BrowserType.CHROME)) {
        Map<String, Object> prefs =new HashMap<String, Object>();
        ChromeOptions option = new ChromeOptions();
        option.addArguments("disable-infobars");
        prefs .put("credentials_enable_service", false);
        prefs .put("profile.password_manager_enabled", false);
        option.setExperimentalOption("prefs", prefs);
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, option);
        wd = new ChromeDriver(capabilities);
        wd.manage().window().maximize();
      } else if (Objects.equals(browser, BrowserType.IE)) {
        wd = new InternetExplorerDriver();
      }
      wd.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);  //установка тайм-аута для ожидания загрузки страницы
      wd.get(properties.getProperty("web.baseUrl"));
    }
    return wd;
  }
}
