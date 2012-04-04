package it.snova.web.gui.faces.bean;

import it.snova.appframework.membership.data.UserManager;
import it.snova.dbschema.table.Domain;
import it.snova.dbschema.table.Group;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean(name = "group")
public class GroupBean implements Serializable
{
  private static final Logger logger = Logger.getLogger(GroupBean.class.getName());

  @ManagedProperty(value="#{" + UserSessionBean.BEAN_NAME + "}")
  private UserSessionBean userSession;

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private long id;
  private String domain;  
  private String name;

  public GroupBean()
  {
    this.id = 0;
  }
  
  public GroupBean(long id)
  {
    this.id = id;
  }
  
  public long getId()
  {
    return id;
  }
  
  public String getDomain()
  {
    return domain;
  }
  
  public void setDomain(String domain)
  {
    this.domain = domain;
  }
  
  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }
  
  public void save()
  {
    logger.info("Save group " + name);
    
    UserManager mgr = getUserSession().getUserManager();
    
    Domain d = mgr.getDomain(domain);
    Group g = new Group();
    g.setDomain(d);
    g.setName(name);
    
    mgr.createGroup(g);

    domain = "";
    name = "";
  }

  public UserSessionBean getUserSession()
  {
    return userSession;
  }

  public void setUserSession(UserSessionBean userSession)
  {
    this.userSession = userSession;
  }

}
