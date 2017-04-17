package ru.qa.rtsoft.prospector.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.qa.rtsoft.prospector.model.MailMessage;
import ru.qa.rtsoft.prospector.model.UserData;
import ru.qa.rtsoft.prospector.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * Created by korvin on 04.04.2017.
 */
public class ResetUserPasswordTests extends TestBase {

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testResetingPassword() throws IOException, MessagingException {
    Users users = app.db().users();
    String username = null;
    String email = null;
    int id = 0;
    for (UserData user : users) {
      if (user.getId() == users.stream().mapToInt((g) -> g.getId()).max().getAsInt()) {
        username = user.getUsername();
        email = user.getEmail();
        id = user.getId();
      }
    }
    if (id == 1){
      System.out.println("No users found, please register at least one user");
      return;
    }
    app.session().loginAsAdmin();
    app.goTo().manageUsers(username);
    List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
    String confirmationLink = findConfirmationLink(mailMessages, email);
    String password = "password";
    app.session().changePassword(confirmationLink, password);
    assertTrue( app.newSession().login(username, password));
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findAny().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }


  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}
