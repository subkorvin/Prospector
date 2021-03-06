package ru.qa.rtsoft.prospector.appmanager;

import org.openqa.selenium.*;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created by korvin on 21.02.2017.
 */
public class HelperBase {

  protected ApplicationManager app;
  protected WebDriver wd;

  public HelperBase(ApplicationManager app) {
    this.app = app;
    this.wd = app.getDriver();
  }

  protected void click(By locator) {
    wd.findElement(locator).click();
  }

  protected void type(By locator, String text) {
    click(locator);
    if (text != null) {
      String existingText = wd.findElement(locator).getAttribute("value");
      if (!Objects.equals(text, existingText)) {
        wd.findElement(locator).clear();
        wd.findElement(locator).sendKeys(text);
      }
    }
  }

  protected void attach(By locator, File file) {
    if (file != null) {
      wd.findElement(locator).sendKeys(file.getAbsolutePath());
    }
  }


  protected void confirm() {
    wd.switchTo().alert().accept();
  }

  public boolean isAlertPresent() {
    try {
      wd.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  public boolean isElementPresent(By locator) {
    try {
      wd.findElement(locator);
      return true;
    } catch (NoSuchElementException ex) {
      return false;
    }
  }

  public boolean isElementChecked(By locator) {
    isElementPresent(locator);
    //input[type="checkbox"][ng-model^="scope.SummaryConfiguration"] локаторы для чекбоксов
    if (Objects.equals(wd.findElement(locator).getAttribute("checked"), "true")) {

      return true;
    } else return false;
  }

  public boolean isElementDisabled(By locator) {
    isElementPresent(locator);
    if (Objects.equals(wd.findElement(locator).getAttribute("disabled"), "disabled")) {
      return true;
    } else return false;
  }

  public boolean isHierarchySelected(){
    wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    if (isElementPresent(By.cssSelector("div[class='part right active'][ng-click='scope.applyHierarchyFilterDefault()']"))){
      return true;
    } else return false;
  }

}
