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
      TypedQuery<User> q = em.createQuery("SELECT u FROM User u where login=:login", User.class).setParameter("login", login);
      return q.getSingleResult();
    } catch (NoResultException e) {
      logger.fine("no user with login " + login);
      return null;
    } finally {
      em.close();
    }
  }

  public User getUser(long id)
  {
    EntityManager em = context.createEntityManager();
    try {
      User u = em.find(User.class, id);
      if (u != null) {
        em.detach(u);
      }
      return u;
    } finally {
      em.close();
    }
  }

  public int getUserCount(final Domain d)
  {
    EntityManager em = context.createEntityManager();
    try {
      String sql = "SELECT count(u) FROM User u";
      if (d != null) {
        sql += " where domain=:domain";
      }
      Query q = em.createQuery(sql);
      if (d != null) {
        q.setParameter("domain", d);
      }
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }

  public void iterate(final DBIterator<User> iterator) throws Exception
  {
    iterate(null, iterator);
  }

  public void iterate(Domain d, final DBIterator<User> iterator) throws Exception
  {
    EntityManager em = context.createEntityManager();
    try {
      String sql = "SELECT u FROM User u";
      if (d != null) {
        sql += " where domain=:domain";
      }
      TypedQuery<User> q = em.createQuery(sql, User.class);
      if (d != null) {
        q.setParameter("domain", d);
      }
      iterator.setQueryParams(q);
      for (User u : q.getResultList()) {
        if (iterator.getIterator().process(u)) {
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

  public String resetPassword(User u)
  {
    EntityManager em = context.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    try {
      tx.begin();

      User user = em.find(User.class, u.getId());

      final String newPwd = new String(pgen.generatePassword());

      PasswordEncrypter e = new PasswordEncrypter();
      char[] pwd = e.encrypt(newPwd.toCharArray());
      user.setPwd(new String(pwd));
      em.persist(user);

      tx.commit();

      return newPwd;
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      em.close();
    }
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
      if (user != null) {
        em.remove(user);
      }

      tx.commit();
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      em.close();
    }
  }

  public Domain getDomain(String name)
  {
    EntityManager em = context.createEntityManager();
    try {
      TypedQuery<Domain> q = em.createQuery("SELECT d FROM Domain d where name=:name", Domain.class).setParameter(
          "name", name);
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
      TypedQuery<Domain> q = em
          .createQuery("SELECT d FROM Domain d where id=" + Defaults.ADMIN_DOMAIN_ID, Domain.class);
      return q.getSingleResult();
    } finally {
      em.close();
    }
  }

  private List<Domain> getDomainsQuery(Domain master, boolean alldomains)
  {
    EntityManager em = context.createEntityManager();
    try {
      String sql;
      if (alldomains) {
        sql = "SELECT d FROM Domain d";
      } else {
        sql = "SELECT d FROM Domain d where domain=:domain";
      }
      TypedQuery<Domain> q = em.createQuery(sql, Domain.class);
      if (!alldomains) {
        if (master != null) {
          q.setParameter("domain", master);
        } else {
          q.setParameter("domain", null);
        }
      }
      return q.getResultList();
    } finally {
      em.close();
    }
  }

  public List<Domain> getDomains()
  {
    return getDomainsQuery(null, false);
  }

  public List<Domain> getSubDomains(Domain master)
  {
    return getDomainsQuery(master, false);
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

  public Group getGroup(long id)
  {
    EntityManager em = context.createEntityManager();
    try {
      Group g = em.find(Group.class, id);
      if (g != null) {
        em.detach(g);
      }
      return g;
    } finally {
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

  public int getGroupCount(final Domain d)
  {
    EntityManager em = context.createEntityManager();
    try {
      String sql = "SELECT count(g) FROM Group g";
      if (d != null) {
        sql += " where domain=:domain";
      }
      Query q = em.createQuery(sql);
      if (d != null) {
        q.setParameter("domain", d);
      }
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }

  public List<Group> getAllGroups()
  {
    return getGroups(null);
  }

  public Group findGroup(final Long id)
  {
    EntityManager em = context.createEntityManager();
    try {
      return em.find(Group.class, id);
    } catch (NoResultException e) {
      logger.fine("no group with id " + id);
      return null;
    } finally {
      em.close();
    }
  }

  public void deleteGroup(Group g)
  {
    EntityManager em = context.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    try {
      tx.begin();

      Group group = em.find(Group.class, g.getId());
      em.remove(group);

      tx.commit();
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      em.close();
    }
  }

  public void setGroupMembers(Group g, long[] userIds)
  {
    EntityManager em = context.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    try {
      tx.begin();

      Group group = em.find(Group.class, g.getId());

      group.getMembers().clear();
      for (long id: userIds) {
        User u = em.find(User.class, id);
        group.addMember(u);
      }
      em.persist(group);

      tx.commit();
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      em.close();
    }
  }

  public void iterateGroups(Domain d, final DBIterator<Group> iterator)
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
      iterator.setQueryParams(q);
      for (Group g : q.getResultList()) {
        if (iterator.getIterator().process(g)) {
          break;
        }
      }
    } finally {
      em.close();
    }
  }

  public void iterateGroups(final DBIterator<Group> iterator)
  {
    iterateGroups(null, iterator);
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

}
