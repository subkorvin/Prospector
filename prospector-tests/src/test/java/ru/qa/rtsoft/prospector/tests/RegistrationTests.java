package ru.qa.rtsoft.prospector.tests;

import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.qa.rtsoft.prospector.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * Created by korvin on 03.04.2017.
 */
public class RegistrationTests extends TestBase{

//  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testRegistration() throws IOException, MessagingException {
    Long now = System.currentTimeMillis();
    String email = String.format("user%s@localhost.localdomain", now);
    String username = String.format("user%s", now);
    String password = "password";
    app.james().createUser(username, password);
    app.registration().start(username, email);
//    List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
    List<MailMessage> mailMessages = app.james().waitForMail(username, password, 60000);
    String confirmationLink = findConfirmationLink(mailMessages, email);
    app.registration().finish(confirmationLink, password);
    assertTrue( app.newSession().login(username, password));
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findAny().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

//  @AfterMethod (alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}
