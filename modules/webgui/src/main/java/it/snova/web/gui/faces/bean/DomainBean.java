package it.snova.web.gui.faces.bean;

import it.snova.appframework.membership.data.UserManager;
import it.snova.dbschema.table.Domain;

import java.util.List;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

@ManagedBean(name = "domain")
public class DomainBean
{
  private static final Logger logger = Logger.getLogger(DomainBean.class.getName());

  @ManagedProperty(value="#{" + UserSessionBean.BEAN_NAME + "}")
  private UserSessionBean userSession;

  private String name;
  
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
    logger.info("Save domain " + name);
    
    name = name.toLowerCase();
    
    UserManager mgr = getUserSession().getUserManager();
    
    FacesMessage msg = null;
    
    List<Domain> domains = mgr.getDomains();
    for (Domain d: domains) {
      if (d.getName().equals(name)) {
        msg = new FacesMessage(
            FacesMessage.SEVERITY_ERROR,
            "Domain with name " + name + " already exists!",
            null);
        break;
      }
    }
    if (msg == null) {
      Domain d = new Domain(name);
      mgr.createDomain(d);
      msg = new FacesMessage("Domain " + name + " has been created");
      name = "";
    }
    FacesContext.getCurrentInstance().addMessage(null, msg);
  }
  
  public UserSessionBean getUserSession()
  {
    return userSession;
  }

  public void setUserSession(UserSessionBean userSession)
  {
    logger.info("set session");
    this.userSession = userSession;
  }

}
