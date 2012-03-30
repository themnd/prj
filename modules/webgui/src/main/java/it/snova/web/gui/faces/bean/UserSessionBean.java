package it.snova.web.gui.faces.bean;

import it.snova.appframework.context.Context;
import it.snova.appframework.membership.data.UserManager;
import it.snova.web.gui.config.utils.Configuration;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = UserSessionBean.BEAN_NAME)
@SessionScoped
public class UserSessionBean implements Serializable
{
  private static final Logger logger = Logger.getLogger(UserSessionBean.class.getName());

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  static public final String BEAN_NAME = "userSession";

  @PostConstruct
  public void init()
  {
    logger.info("UserSessionBean init");
  }
  
  public UserManager getUserManager()
  {
    Context context = Configuration.getInstance().getContext();
    return new UserManager(context);
  }

}
