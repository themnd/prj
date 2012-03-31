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
  private String selectedDomain = Defaults.ALL_DOMAINS;

  public UserListModel()
  {
  }

  @Override
  public List<UserBean> load(int first, int pageSize, String sortField, SortOrder sortOrder, final Map<String,String> filters)
  {
    System.out.println("first: " + first);
    System.out.println("pageSize: " + pageSize);
    System.out.println("sortField: " + sortField);
    System.out.println("sortOrder: " + sortOrder.toString());
    System.out.println("filter: " + filters.toString());

    users.clear();
    
    try {
      UserManager userMgr = getUserSession().getUserManager();
      
      UserIterable iter = new UserIterable() {
        
        @Override
        public boolean processUser(User u)
        {
          boolean add = true;
          if (filters.containsKey("login")) {
            add = u.getLogin() != null && u.getLogin().toLowerCase().contains(filters.get("login").toLowerCase());
          }
          if (add && filters.containsKey("name")) {
            add = u.getName() != null && u.getName().toLowerCase().contains(filters.get("name").toLowerCase());
          }
          if (add && filters.containsKey("email")) {
            add = u.getEmail() != null && u.getEmail().toLowerCase().contains(filters.get("email").toLowerCase());
          }
          if (add) {
            users.add(create(u));
          }
          return false;
        }
      };
      if (filters.containsKey("domain")) {
        String domain = filters.get("domain");
        Domain d = userMgr.getDomain(domain);
        userMgr.iterateForDomain(d, iter);
      } else {
        userMgr.iterate(iter);        
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
