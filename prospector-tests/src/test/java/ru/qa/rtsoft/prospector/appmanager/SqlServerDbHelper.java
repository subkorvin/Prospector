package ru.qa.rtsoft.prospector.appmanager;

import java.sql.*;

/**
 * Created by Korvin on 25.03.2017.
 */
public class SqlServerDbHelper {


  private final ApplicationManager app;
  private final Connection conn;

  public SqlServerDbHelper(ApplicationManager app) throws SQLException, ClassNotFoundException {
    this.app = app;
    String userName = "sa";
    String password = "jvbrhjy_1";
    String url = "jdbc:sqlserver://192.168.14.31;databaseName=NES_Core";

//    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    conn = DriverManager.getConnection(url, userName, password);
  }

  public ResultSet requestResult(String request) throws SQLException {
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery(request);
    return rs;
  }





//  public Users users () {
//    Session session = sessionFactory.openSession();
//    session.beginTransaction();
//    List<UserData> result = session.createQuery("from UserData").list();
//    session.getTransaction().commit();
//    session.close();
//    return new Users(result);
//  }
}
