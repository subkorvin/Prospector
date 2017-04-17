import java.util.concurrent.TimeUnit;
import java.util.Date;
import java.io.File;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.*;
import static org.openqa.selenium.OutputType.*;

public class test {
    public static void main(String[] args) throws Exception {
        FirefoxDriver wd;
        wd = new FirefoxDriver();
        wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        wd.get("https://win-nes-test.prlab.test/idsrv/account/signin?ReturnUrl=%2fidsrv%2fissue%2fwsfed%3fwa%3dwsignin1.0%26wtrealm%3durn%253amvcjwtrp%26wctx%3drm%253d0%2526id%253dpassive%2526ru%253d%25252fEM5%25252f%26wct%3d2017-04-17T13%253a27%253a19Z&wa=wsignin1.0&wtrealm=urn%3amvcjwtrp&wctx=rm%3d0%26id%3dpassive%26ru%3d%252fEM5%252f&wct=2017-04-17T13%3a27%3a19Z");
        wd.findElement(By.id("UserName")).click();
        wd.findElement(By.id("UserName")).clear();
        wd.findElement(By.id("UserName")).sendKeys("v.shchapov");
        wd.findElement(By.id("Password")).click();
        wd.findElement(By.id("Password")).clear();
        wd.findElement(By.id("Password")).sendKeys("123qwe");
        wd.findElement(By.xpath("//div[@class='buttons']/input[2]")).click();
        wd.quit();
    }
    
    public static boolean isAlertPresent(FirefoxDriver wd) {
        try {
            wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
}
