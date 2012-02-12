package it.snova.web.gui.faces.bean;

import it.snova.appframework.membership.data.UserManager;
import it.snova.appframework.membership.data.UserManager.UserIterable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "userList")
@ViewScoped
public class UserListBean implements Serializable
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  @ManagedProperty(value="#{" +UserSessionBean.BEAN_NAME + "")
  private UserSessionBean userSession;
  
  private List<UserBean> users;
  private String filter;

  @PostConstruct
  public void init()
  {
    System.out.println("UserListBean init");
    
    filter = "";
    
    users = new ArrayList<UserBean>();
    
    try {
      UserManager userMgr = userSession.getUserManager();
      userMgr.iterate(new UserIterable() {

        @Override
        public boolean processUser(it.snova.appframework.membership.data.UserBean u)
        {
          users.add(new UserBean().setName(u.getName()));
          return false;
        }
        
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
    users.add(new UserBean().setName("marco"));
    users.add(new UserBean().setName("pippo"));
  }
  
  public List<UserBean> getUsers()
  {
    String f = getFilter().trim().toLowerCase();
    if (f.isEmpty()) {
      return users;
    }
    List<UserBean> ret = new ArrayList<UserBean>();
    for (UserBean u: users) {
      if (u.getName().trim().toLowerCase().contains(f)) {
        ret.add(u);
      }
    }
    return ret;
  }
  
  public String getFilter()
  {
    return filter;
  }
  
  public void setFilter(String filter)
  {
    this.filter = filter;
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
