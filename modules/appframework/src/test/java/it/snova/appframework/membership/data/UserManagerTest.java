package it.snova.appframework.membership.data;

import it.snova.dbschema.table.Domain;

import java.util.List;

public class UserManagerTest extends BaseUnitTest
{
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
}
