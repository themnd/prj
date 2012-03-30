package it.snova.web.gui.faces.bean.model;

import it.snova.appframework.membership.data.UserManager;
import it.snova.appframework.membership.data.UserManager.UserIterable;
import it.snova.dbschema.defaults.Defaults;
import it.snova.dbschema.table.Domain;
import it.snova.dbschema.table.User;
import it.snova.web.gui.faces.bean.UserBean;
import it.snova.web.gui.faces.bean.UserSessionBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

@ManagedBean(name = "userListModel")
@ViewScoped
public class UserListModel extends LazyDataModel<UserBean>
{
  private static final Logger logger = Logger.getLogger(UserListModel.class.getName());

  private static final long serialVersionUID = 1L;
  
  private UserSessionBean userSession;

  private List<UserBean> users = new ArrayList<UserBean>();
  private String loginFilter;
  private String selectedDomain = Defaults.ALL_DOMAINS;

  public UserListModel()
  {
    loginFilter = "";
  }

  @Override
  public List<UserBean> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters)
  {
    System.out.println("first: " + first);
    System.out.println("pageSize: " + pageSize);
    System.out.println("sortField: " + sortField);
    System.out.println("sortOrder: " + sortOrder.toString());
    System.out.println("filter: " + filters.toString());

    users.clear();
    
    try {
      UserManager userMgr = getUserSession().getUserManager();
      
      if (selectedDomain.equals(Defaults.ALL_DOMAINS)) {
        userMgr.iterate(new UserIterable() {
  
          @Override
          public boolean processUser(User u)
          {
            if (loginFilter.isEmpty() || u.getName().toLowerCase().contains(loginFilter.toLowerCase())) {
              users.add(create(u));
            }
            return false;
          }
          
        });
      } else {
        Domain d = userMgr.getDomain(selectedDomain);
        userMgr.iterateForDomain(d, new UserIterable() {
          
          @Override
          public boolean processUser(User u)
          {
            if (loginFilter.isEmpty() || u.getName().toLowerCase().contains(loginFilter.toLowerCase())) {
              users.add(create(u));
            }
            return false;
          }
          
        });
      }
      logger.info("End Users size: " + users.size());
      setRowCount(users.size());
    } catch (Exception e) {
      e.printStackTrace();
    }

    return users;
  }
  
  private UserBean create(User u)
  {
    UserBean bean = new UserBean();
    bean.setDomain(u.getDomain().getName());
    bean.setLogin(u.getLogin());
    bean.setName(u.getName());
    bean.setEmail(u.getEmail());
    bean.setPwd(u.getPwd());
    return bean;
  }
  
  public String getLoginFilter()
  {
    return loginFilter;
  }
  
  public void setLoginFilter(String loginFilter)
  {
    this.loginFilter = loginFilter;
  }
  
  public String getSelectedDomain()
  {
    return selectedDomain;
  }
  
  public void setSelectedDomain(String selectedDomain)
  {
    this.selectedDomain = selectedDomain;
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
