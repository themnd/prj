package it.snova.appframework.membership.data;

import it.snova.dbschema.table.Domain;
import it.snova.dbschema.table.Group;
import it.snova.dbschema.table.User;

import java.util.List;

public class UserManagerTest extends BaseUnitTest
{
  private static final String TEST_DOMAIN_NAME = "testDomain";
  private static final String TEST_GROUP_NAME = "testGroup";
  private static final String TEST_USER_NAME = "testUser";
  private static final String TEST_USER_PWD = "testPwd";

  UserManager mgr;

  @Override
  protected void setUp() throws Exception
  {
    super.setUp();

    mgr = new UserManager(context);
  }

  public void test_getAdminDomain()
  {
    Domain d = mgr.getAdminDomain();
    assertNotNull(d);

    System.out.println("name: " + d.getName());
    System.out.println("id: " + d.getId());
  }

  public void test_getDomainUserCount()
  {
    Domain d = mgr.getAdminDomain();
    assertNotNull(d);

    int count = mgr.getUserCount(d);
    assertTrue(count > 0);

    System.out.println(String.format("domain %s have %d users", d.getName(), count));
  }

  public void test_getDomains()
  {
    List<Domain> domains = mgr.getDomains();
    assertNotNull(domains);
    assertTrue(domains.size() >= 1);

    for (final Domain d: domains) {

      System.out.println("name: " + d.getName());
      System.out.println("id: " + d.getId());
    }
  }

  public void test_getDomain()
  {
    Domain ad = mgr.getAdminDomain();
    assertNotNull(ad);

    Domain d = mgr.getDomain(ad.getName());
    assertNotNull(d);

    assertEquals(ad.getId(), d.getId());
    assertEquals(ad.getName(), d.getName());
    assertEquals(ad.getDomain(), d.getDomain());
  }

  public void test_getSubDomains()
  {
    Domain ad = mgr.getAdminDomain();
    assertNotNull(ad);

    List<Domain> domains = mgr.getSubDomains(ad);
    assertNotNull(domains);
    assertEquals(0, domains.size());

    domains = mgr.getDomains();
    assertNotNull(domains);
    assertTrue(domains.size() >= 1);

    for (final Domain d: domains) {
      List<Domain> l = mgr.getSubDomains(d);
      assertNotNull(l);
      assertTrue(l.size() >= 0);
    }
  }

  public void test_getGroups()
  {
    Domain ad = mgr.getAdminDomain();
    assertNotNull(ad);

    List<Group> groups = mgr.getGroups(ad);
    assertNotNull(groups);
    assertTrue(groups.size() >= 0);

    int count = mgr.getGroupCount(ad);
    assertEquals(groups.size(), count);
  }

  public void test_getAllGroups()
  {
    List<Group> groups = mgr.getAllGroups();
    assertNotNull(groups);
    assertTrue(groups.size() >= 0);
  }

  public void test_createAndDeleteGroup()
  {
    Domain d = getTestDomain();

    Group g = recreateTestGroup(d);

    List<Group> groups = mgr.getGroups(d);
    assertNotNull(groups);
    assertEquals(1, groups.size());

    mgr.deleteGroup(g);

    groups = mgr.getGroups(d);
    assertNotNull(groups);
    assertEquals(0, groups.size());
  }

  public void test_getGroup()
  {
    Domain d = getTestDomain();

    Group g = recreateTestGroup(d);

    List<Group> groups = mgr.getGroups(d);
    assertNotNull(groups);
    assertEquals(1, groups.size());

    long groupId = g.getId();
    assertTrue(groupId >= 0);

    g = mgr.getGroup(groupId);
    assertNotNull(g);
    assertEquals(groupId, g.getId());

    mgr.deleteGroup(g);
  }

  public void test_getGroupCount()
  {
    Domain d = getTestDomain();

    Group g = recreateTestGroup(d);

    List<Group> groups = mgr.getGroups(d);
    assertNotNull(groups);
    assertEquals(1, groups.size());

    long groupCount = mgr.getGroupCount(d);
    assertEquals(groups.size(), groupCount);

    mgr.deleteGroup(g);
  }

  public void test_createAndDeleteUser()
  {
    Domain d = getTestDomain();

    User u = recreateTestUser(d);

    int userCount = mgr.getUserCount(d);
    assertTrue(userCount >= 1);

    mgr.deleteUser(u);

    u = mgr.getUser(TEST_USER_NAME);
    assertNull(u);
  }

  public void test_getUser()
  {
    Domain d = getTestDomain();
    User u = recreateTestUser(d);

    long userId = u.getId();

    mgr.deleteUser(u);

    u = mgr.getUser(userId);
    assertNull(u);
  }

  public void test_loginUser() throws Exception
  {
    recreateTestUser(getTestDomain());

    User u = mgr.loginUser(TEST_USER_NAME, TEST_USER_PWD);
    assertNotNull(u);
    assertEquals(TEST_USER_NAME, u.getName());

    mgr.deleteUser(u);
  }

  public void test_resetPassword() throws Exception
  {
    User u = recreateTestUser(getTestDomain());

    String newPwd = mgr.resetPassword(u);
    System.out.println("New pwd: " + newPwd);

    u = mgr.loginUser(TEST_USER_NAME, newPwd);
    assertNotNull(u);
    assertEquals(TEST_USER_NAME, u.getName());

    mgr.deleteUser(u);
  }

  private Domain getTestDomain()
  {
    Domain d = mgr.getDomain(TEST_DOMAIN_NAME);
    if (d == null) {
      d = new Domain();
      d.setName(TEST_DOMAIN_NAME);
      mgr.createDomain(d);

      d = mgr.getDomain(TEST_DOMAIN_NAME);
      assertNotNull(d);
    }
    return d;
  }

  private Group recreateTestGroup(Domain d)
  {
    List<Group> groups = mgr.getGroups(d);
    if (groups.size() > 0) {
      for (final Group g: groups) {
        if (g.getName().equals(TEST_GROUP_NAME)) {
          mgr.deleteGroup(g);
        }
      }
    }
    Group g = new Group();
    g.setName(TEST_GROUP_NAME);
    g.setDomain(d);

    mgr.createGroup(g);

    return g;
  }

  private User recreateTestUser(Domain d)
  {
    User u = mgr.getUser(TEST_USER_NAME);
    if (u == null) {
      u = new User(TEST_USER_NAME, TEST_USER_NAME);
      u.setPwd(TEST_USER_PWD);
      u.setDomain(d);

      mgr.createUser(u);
    }
    return u;
  }
}
