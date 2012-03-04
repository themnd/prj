package it.snova.web.gui.faces.bean;

import it.snova.appframework.membership.data.UserManager;
import it.snova.hbaselib.framework.HClient;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = UserSessionBean.BEAN_NAME)
@SessionScoped
public class UserSessionBean
{
  static public final String BEAN_NAME = "userSession";
  
  HClient client;
  
  @PostConstruct
  public void init()
  {
    System.out.println("UserSessionBean init");
    try {
      client = new HClient().init();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public UserManager getUserManager()
  {
    return new UserManager(client);
  }

}
