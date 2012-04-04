package it.snova.web.gui.faces.bean;

import it.snova.appframework.membership.data.UserManager;
import it.snova.dbschema.defaults.Defaults;
import it.snova.dbschema.table.Domain;
import it.snova.dbschema.table.Group;
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
  
  private GroupUsersDialogBean dialog;

  @PostConstruct
  public void init()
  {
    model = new GroupListModel();
    model.setUserSession(userSession);
    
    selectedGroup = null;
    dialog = null;
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
    dialog = new GroupUsersDialogBean(getSelectedGroup());
    dialog.setUserSession(getUserSession());
    dialog.init();
  }
  
  public void deleteGroup()
  {
    logger.info("removing group " + selectedGroup.getName());
    
    UserManager userMgr = getUserSession().getUserManager();
    Group g = userMgr.findGroup(selectedGroup.getId());
    userMgr.deleteGroup(g);
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
  
  public GroupUsersDialogBean getGroupUsersDialog()
  {
    return dialog;
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
