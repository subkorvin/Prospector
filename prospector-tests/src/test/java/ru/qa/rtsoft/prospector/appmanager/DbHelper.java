package ru.qa.rtsoft.prospector.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.qa.rtsoft.prospector.model.UserData;
import ru.qa.rtsoft.prospector.model.Users;

import java.util.List;

/**
 * Created by Korvin on 25.03.2017.
 */
public class DbHelper {

  private final SessionFactory sessionFactory;
  private final ApplicationManager app;

  public DbHelper(ApplicationManager app) {
    this.app = app;
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();
      sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
  }


  public Users users () {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<UserData> result = session.createQuery("from UserData").list();
    session.getTransaction().commit();
    session.close();
    return new Users(result);
  }
}
