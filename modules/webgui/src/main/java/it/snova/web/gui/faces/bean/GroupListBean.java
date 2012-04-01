package it.snova.web.gui.faces.bean;

import it.snova.dbschema.defaults.Defaults;
import it.snova.dbschema.table.Domain;
import it.snova.web.gui.faces.bean.model.GroupListModel;

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

@ManagedBean(name = "groupList")
@ViewScoped
public class GroupListBean implements Serializable
{
  private static final Logger logger = Logger.getLogger(GroupListBean.class.getName());

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  @ManagedProperty(value="#{" + UserSessionBean.BEAN_NAME + "}")
  private UserSessionBean userSession;
  
  private GroupListModel model;
  private GroupBean selectedGroup;

  @PostConstruct
  public void init()
  {
    model = new GroupListModel();
    model.setUserSession(userSession);
    
    selectedGroup = null;
  }
  
  public LazyDataModel<GroupBean> getModel()
  {
    return model;
  }
  
  public GroupBean getSelectedGroup()
  {
    return selectedGroup;
  }
  
  public void setSelectedGroup(GroupBean selectedGroup)
  {
    this.selectedGroup = selectedGroup;
  }
  
  public void deleteUser()
  {
//    logger.info("removing user " + selectedGroup.getLogin());
//    
//    UserManager userMgr = getUserSession().getUserManager();
//    User u = userMgr.getUser(selectedGroup.getLogin());
//    userMgr.deleteUser(u);
  }
  
  public List<SelectItem> getAvailableDomains()
  {
    List<SelectItem> domains = new ArrayList<SelectItem>();
    domains.add(new SelectItem("", Defaults.ALL_DOMAINS));
    
    List<Domain> dbDomains = getUserSession().getUserManager().getDomains();
    for (Domain d: dbDomains) {
      domains.add(new SelectItem(d.getName()));      
    }
    return domains;
  }

  public List<SelectItem> getDomains()
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
