package it.snova.web.gui.faces.bean;

import it.snova.appframework.membership.data.UserManager;
import it.snova.dbschema.table.Domain;
import it.snova.dbschema.table.User;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean(name = "user")
public class UserBean implements Serializable
{
  private static final Logger logger = Logger.getLogger(UserBean.class.getName());

  @ManagedProperty(value="#{" + UserSessionBean.BEAN_NAME + "}")
  private UserSessionBean userSession;

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private long id;
  
  private String domain;
  
  private String login;
  private String name;
  private String pwd;
  private String email;

  public UserBean()
  {
    id = 0;
  }
  
  public UserBean(long id)
  {
    this.id = id;
  }
  
  public long getId()
  {
    return this.id;
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
  
  public String getLogin()
  {
    return login;
  }

  public void setLogin(String login)
  {
    this.login = login;
  }

  public String getPwd()
  {
    return pwd;
  }

  public void setPwd(String pwd)
  {
    this.pwd = pwd;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }
  
  public void save()
  {
    logger.info("Save user " + name);
    
    UserManager mgr = getUserSession().getUserManager();
    
    Domain d = mgr.getDomain(domain);
    
    User u = new User(login, name);
    u.setEmail(email);
    u.setPwd(pwd);
    u.setDomain(d);
    
    mgr.createUser(u);
    
    login = "";
    name = "";
    pwd = "";
    email = "";    
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
