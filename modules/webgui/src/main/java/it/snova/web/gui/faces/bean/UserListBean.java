package it.snova.web.gui.faces.bean;

import it.snova.dbschema.defaults.Defaults;
import it.snova.dbschema.table.Domain;
import it.snova.web.gui.faces.bean.model.UserListModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.model.LazyDataModel;

@ManagedBean(name = "userList")
@ViewScoped
public class UserListBean implements Serializable
{
  private static final Logger logger = Logger.getLogger(UserListBean.class.getName());

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  @ManagedProperty(value="#{" + UserSessionBean.BEAN_NAME + "}")
  private UserSessionBean userSession;
  
  private UserListModel model;
  private UserBean selectedUser;

  @PostConstruct
  public void init()
  {
    model = new UserListModel();
    model.setUserSession(userSession);
    
    selectedUser = null;
  }
  
  public LazyDataModel<UserBean> getModel()
  {
    return model;
  }
  
  public UserBean getSelectedUser()
  {
    return selectedUser;
  }
  
  public void setSelectedUser(UserBean selectedUser)
  {
    this.selectedUser = selectedUser;
  }
  
  public void deleteUser()
  {
    
  }
  
  public List<SelectItem> getAvailableDomains()
  {
    List<SelectItem> domains = new ArrayList<SelectItem>();
    domains.add(new SelectItem(Defaults.ALL_DOMAINS));
    
    List<Domain> dbDomains = getUserSession().getUserManager().getDomains();
    for (Domain d: dbDomains) {
      domains.add(new SelectItem(d.getName()));      
    }
    return domains;
  }
  
  public List<SelectItem> getExistingDomains()
  {
    List<SelectItem> domains = new ArrayList<SelectItem>();

    List<Domain> dbDomains = getUserSession().getUserManager().getDomains();
    for (Domain d: dbDomains) {
      domains.add(new SelectItem(d.getName()));      
    }
    return domains;
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
