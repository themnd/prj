package it.snova.appframework.context;

import it.snova.dbschema.init.Initialization;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.datanucleus.api.jpa.JPAEntityManagerFactory;
import org.datanucleus.metadata.PersistenceUnitMetaData;

public class Context
{
  ConnectorOptions options;
  EntityManagerFactory emf;
  
  public Context()
  {
    this.options = null;
    this.emf = null;
  }

  public Context setOptions(ConnectorOptions options)
  {
    this.options = options;
    return this;
  }
  
  public Context init()
  {
    PersistenceUnitMetaData pumd = new PersistenceUnitMetaData("dynamic-unit", "RESOURCE_LOCAL", null);
    Initialization.initPersistenceClasses(pumd);
    pumd.setExcludeUnlistedClasses();
//    pumd.addProperty("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
//    pumd.addProperty("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/app");
//    pumd.addProperty("javax.persistence.jdbc.user", "root");
//    pumd.addProperty("javax.persistence.jdbc.password", "root");
//    pumd.addProperty("datanucleus.autoCreateSchema", "false");
//    pumd.addProperty("datanucleus.identifier.case", "PreserveCase");
    pumd.addProperty("javax.persistence.jdbc.driver", options.driver());
    pumd.addProperty("javax.persistence.jdbc.url", options.url());
    pumd.addProperty("javax.persistence.jdbc.user", options.username());
    pumd.addProperty("javax.persistence.jdbc.password", options.password());
    pumd.addProperty("datanucleus.autoCreateSchema", "false");
    pumd.addProperty("datanucleus.identifier.case", "PreserveCase");
    
    emf = new JPAEntityManagerFactory(pumd, null);
    
    return this;
  }
  
  public EntityManager createEntityManager()
  {
    return emf.createEntityManager();
  }

  @Override
  protected void finalize() throws Throwable
  {
    super.finalize();
    
    if (emf != null) {
      emf.close();
    }
  }
}
