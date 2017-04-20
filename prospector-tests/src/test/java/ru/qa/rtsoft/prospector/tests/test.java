package ru.qa.rtsoft.prospector.tests;

import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class test extends TestBase{

  @Test
  public void getGatewayNames() throws SQLException, ClassNotFoundException {

    String request = "select count (*) as count from gateways";
    ResultSet gateways = app.sql().requestResult(request);
    while (gateways.next()) {
//      String name = gateways.getString("Name");
//      String id = gateways.getString("GatewayID");
//      System.out.println(id + " " + name);
      int count = gateways.getInt("count");
      System.out.println(count);
    }
  }
}
