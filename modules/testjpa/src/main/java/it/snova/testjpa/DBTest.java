package it.snova.testjpa;

import it.snova.appframework.context.ConnectorOptions;
import it.snova.appframework.context.Context;
import it.snova.appinstall.AppInstaller;

public class DBTest
{

  public void setArguments(String[] args)
  {
  }

  public int execute()
  {
    ConnectorOptions options = new ConnectorOptions()
      .dbname("app")
      .username("root")
      .password("root");
  
    Context context = new Context()
      .setOptions(options)
      .init();
    
    try {
      AppInstaller.install(context);
    } catch (Exception e) {
      e.printStackTrace();
    }

//    PersistenceUnitMetaData pumd = new PersistenceUnitMetaData("dynamic-unit", "RESOURCE_LOCAL", null);
//    Initialization.initPersistenceClasses(pumd);
//    pumd.setExcludeUnlistedClasses();
//    pumd.addProperty("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
//    pumd.addProperty("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/app");
//    pumd.addProperty("javax.persistence.jdbc.user", "root");
//    pumd.addProperty("javax.persistence.jdbc.password", "root");
//    pumd.addProperty("datanucleus.autoCreateSchema", "false");
//    pumd.addProperty("datanucleus.identifier.case", "PreserveCase");
//
//    EntityManagerFactory emf = new JPAEntityManagerFactory(pumd, null);
//    EntityManager em = emf.createEntityManager();
    
    
//    Query q = em.createQuery("SELECT d FROM Domain d where id=" + Defaults.ADMIN_DOMAIN_ID);
//    Domain domain = (Domain) q.getSingleResult();
//    System.out.println(domain);
//
//    List<User> domainUsers = domain.getUsers();
//    for (int i = 0; i < 10; i++) {
//      User u = new User("UserLogin" + i, "User " + i);
//      domainUsers.add(u);
//    }
//    em.persist(domain);
//
//    
////    Query q = em.createQuery("SELECT u FROM User u where domain=0");
////    List<User> users = (List<User>) q.getResultList();
//    List<User> users = domain.getUsers();
//    System.out.println(users);
//
////    Query q = em.createQuery("SELECT u FROM User u where domain=0");
////    List<User> users = (List<User>) q.getResultList();
////    System.out.println(users);
//    
//    em.close();
//    emf.close();

    return 0;
  }

}
