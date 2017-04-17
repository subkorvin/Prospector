package ru.qa.rtsoft.prospector.tests;

import org.testng.annotations.Test;
import ru.qa.rtsoft.prospector.model.Issue;
import ru.qa.rtsoft.prospector.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

/**
 * Created by korvin on 06.04.2017.
 */
public class SoapTests extends TestBase {

  public int issueId;


  @Test
  public void testGetProject() throws MalformedURLException, ServiceException, RemoteException {
    issueId = 1;
    skipTest();
    Set<Project> projects = app.soap().getProjects();
    for (Project project : projects) {
      System.out.println("Project name is " + project.getName());
    }
  }

  @Test
  public void testCreateIssue() throws RemoteException, ServiceException, MalformedURLException {
    issueId = 2;
    skipTest();
    Set<Project> projects = app.soap().getProjects();
    Issue issue = new Issue()
            .withSummary("Test issue")
            .withDescription("Test issue desription")
            .withProject(projects.iterator().next());
    Issue created = app.soap().addIssue(issue);
    assertEquals(issue.getSummary(), created.getSummary());
  }


  public int getIssueId(int issueId) {
    return issueId;
  }

  public void skipTest() throws RemoteException, ServiceException, MalformedURLException {
    TestBase tb = new TestBase();
    tb.skipIfNotFixed(issueId);
  }
}
