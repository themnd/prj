package it.snova.appframework.membership.data;

import it.snova.appframework.context.Context;
import it.snova.appframework.security.PasswordEncrypter;
import it.snova.appframework.security.PasswordGenerator;
import it.snova.dbschema.defaults.Defaults;
import it.snova.dbschema.table.Domain;
import it.snova.dbschema.table.Group;
import it.snova.dbschema.table.User;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class UserManager
{
  static PasswordGenerator pgen = PasswordGenerator.factory();

  protected static final Logger logger = Logger.getLogger(UserManager.class.getName());

  Context context;

  public UserManager()
  {
    this.context(null);
  }
  
  public UserManager(Context context)
  {
    this.context(context);
  }
  
  public UserManager context(Context context)
  {
    this.context = context;
    return this;
  }
  
  public User getUser(final String login)
  {
    EntityManager em = context.createEntityManager();
    try {
      TypedQuery<User> q = em.createQuery("SELECT u FROM User u where login=:login", User.class)
        .setParameter("login", login);
      return q.getSingleResult();
    } catch (NoResultException e) {
      logger.fine("no user with login " + login);
      return null;
    } finally {
      em.close();
    }
  }
  
  public void iterate(final UserIterable iterable) throws Exception
  {
    EntityManager em = context.createEntityManager();
    try {
      TypedQuery<User> q = em.createQuery("SELECT u FROM User u", User.class);
      for (User u: q.getResultList()) {
        if (iterable.processUser(u)) {
          break;
        }
      }
    } finally {
      em.close();
    }
  }
  
  public void iterateForDomain(Domain d, final UserIterable iterable) throws Exception
  {
    EntityManager em = context.createEntityManager();
    try {
      TypedQuery<User> q = em.createQuery("SELECT u FROM User u where domain=:domain", User.class)
          .setParameter("domain", d);
      for (User u: q.getResultList()) {
        if (iterable.processUser(u)) {
          break;
        }
      }
    } finally {
      em.close();
    }
  }

  public User loginUser(final String name, final String pwd) throws Exception
  {
    return validateUser(getUser(name), pwd);
  }
  
  public User resetPassword(User u)
  {
    u.setPwd(new String(pgen.generatePassword()));
    return u;
  }
  
  public void createUser(User u)
  {
    EntityManager em = context.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    try {
      tx.begin();
    
      PasswordEncrypter e = new PasswordEncrypter();
      char[] pwd = e.encrypt(new String(u.getPwd()).toCharArray());
      u.setPwd(new String(pwd));
      
      em.persist(u);
      tx.commit();
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      em.close();
    }
  }
  
  public void deleteUser(User u)
  {
    EntityManager em = context.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    try {
      tx.begin();

      User user = em.find(User.class, u.getId());
      em.remove(user);
      
      tx.commit();
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      em.close();
    }
  }
  
  public int getUserCount() throws Exception
  {
    EntityManager em = context.createEntityManager();
    try {
      Query q = em.createQuery("SELECT count(id) FROM User u");
      return q.getFirstResult();
    } finally {
      em.close();
    }
  }
  
  public Domain getDomain(String name)
  {
    EntityManager em = context.createEntityManager();
    try {
      TypedQuery<Domain> q = em.createQuery("SELECT d FROM Domain d where name=:name", Domain.class)
          .setParameter("name", name);
      return q.getSingleResult();
    } catch (NoResultException e) {
      logger.fine("no domain with name " + name);
      return null;
    } finally {
      em.close();
    }    
  }


  public Domain getAdminDomain()
  {
    EntityManager em = context.createEntityManager();
    try {
      TypedQuery<Domain> q = em.createQuery("SELECT d FROM Domain d where id=" + Defaults.ADMIN_DOMAIN_ID, Domain.class);
      return q.getSingleResult();
    } finally {
      em.close();
    }
  }
  
  public List<Domain> getDomains()
  {
    EntityManager em = context.createEntityManager();
    try {
      TypedQuery<Domain> q = em.createQuery("SELECT d FROM Domain d", Domain.class);
      return q.getResultList();
    } finally {
      em.close();
    }    
  }
  
  public void createDomain(Domain d)
  {
    EntityManager em = context.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    try {
      tx.begin();
      
      em.persist(d);
      
      tx.commit();
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      em.close();
    }
  }

  public void createGroup(Group g)
  {
    EntityManager em = context.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    try {
      tx.begin();
    
      em.persist(g);
      
      tx.commit();
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      em.close();
    }
  }

  public List<Group> getGroups(Domain d)
  {
    EntityManager em = context.createEntityManager();
    try {
      String sql = "SELECT g FROM Group g";
      if (d != null) {
        sql += " where domain=:domain";
      }
      TypedQuery<Group> q = em.createQuery(sql, Group.class);
      if (d != null) {
        q.setParameter("domain", d);
      }
      return q.getResultList();
    } finally {
      em.close();
    }
  }
  
  public List<Group> getAllGroups()
  {
    return getGroups(null);
  }

  public void iterateGroups(Domain d, final GroupIterable iterable)
  {
    EntityManager em = context.createEntityManager();
    try {
      String sql = "SELECT g FROM Group g";
      if (d != null) {
        sql += " where domain=:domain";
      }
      TypedQuery<Group> q = em.createQuery(sql, Group.class);
      if (d != null) {
        q.setParameter("domain", d);
      }
      for (Group g: q.getResultList()) {
        if (iterable.process(g)) {
          break;
        }
      }
    } finally {
      em.close();
    }
  }

  public void iterateGroups(final GroupIterable iterable)
  {
    iterateGroups(null, iterable);
  }

  private User validateUser(User u, String pwd)
  {
    if (u != null) {
      if (pgen.verifyPassword(u.getPwd(), pwd)) {
        return u;
      }
    }
    return null;
  }

  public interface UserIterable
  {
    public boolean processUser(User u);
  }

  public interface GroupIterable
  {
    public boolean process(Group u);
  }

}
