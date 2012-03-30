package it.snova.appinstall;

import it.snova.appframework.context.Context;
import it.snova.appframework.membership.data.UserManager;
import it.snova.appframework.security.PasswordEncrypter;
import it.snova.dbschema.table.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class DefaultDataInstaller
{
  Context context;
  List<Object> objects;
  
  public DefaultDataInstaller(Context context)
  {
    this.context = context;
    objects = new ArrayList<Object>();
  }
  
  public DefaultDataInstaller addObject(Object o) throws IOException
  {
    objects.add(o);
    return this;
  }
  
  public DefaultDataInstaller createObjects() throws Exception
  {
    if (objects.size() > 0) {
      EntityManager em = context.createEntityManager();
      EntityTransaction tx = em.getTransaction();
      try {
        tx.begin();
        
        for (Object o: objects) {
          createObject(em, o);
        }      
      } finally {
        if (tx.isActive()) {
          tx.commit();
        } else {
          tx.rollback();
        }
      }
    }
    return this;
  }
  
  private void createObject(EntityManager em, Object o) throws Exception
  {
    em.persist(o);
  }

  static public DefaultDataInstaller install(Context context) throws Exception
  {
//    PasswordEncrypter e = new PasswordEncrypter();
//    char[] pwd = e.encrypt(new String("admin").toCharArray());

    UserManager usrMgr = new UserManager(context);
    if (usrMgr.getUser("admin") == null) {      
      User admin = new User("admin", "Administrator");
      admin.setDomain(usrMgr.getAdminDomain());
//      admin.setPwd(new String(pwd));
      admin.setPwd("admin");
      admin.setEmail("admin@admin.it");
      
      return new DefaultDataInstaller(context)
        .addObject(admin)
        .createObjects();
    }
    return null;
  }

}
