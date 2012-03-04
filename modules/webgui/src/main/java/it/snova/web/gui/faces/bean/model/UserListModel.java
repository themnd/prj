package it.snova.web.gui.faces.bean.model;

import it.snova.appframework.membership.data.UserManager;
import it.snova.web.gui.faces.bean.UserBean;
import it.snova.web.gui.faces.bean.UserSessionBean;

import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedProperty;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class UserListModel extends LazyDataModel<UserBean>
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  @ManagedProperty(value="#{" +UserSessionBean.BEAN_NAME + "}")
  private UserSessionBean userSession;

  private UserManager userManager;
  private String lastRowKey;
  
  public UserListModel()
  {
    this.userManager = null;
    this.lastRowKey = null;
  }

  @Override
  public List<UserBean> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters)
  {
    System.out.println("first: " + first);
    System.out.println("pageSize: " + pageSize);
    System.out.println("sortField: " + sortField);
    System.out.println("sortOrder: " + sortOrder.toString());
    System.out.println("filter: " + filters.toString());
    // TODO Auto-generated method stub
    return null;
  }
  
  public UserSessionBean getUserSession()
  {
    return userSession;
  }

  public void setUserSession(UserSessionBean userSession)
  {
    this.userSession = userSession;
    try {
      userManager = userSession.getUserManager();
      int rowCount = userManager.getUserCount();
      System.out.println("there are " + rowCount + " users");
      setRowCount(rowCount);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }


}
