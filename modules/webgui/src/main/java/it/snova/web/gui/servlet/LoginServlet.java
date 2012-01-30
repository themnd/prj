package it.snova.web.gui.servlet;

import it.snova.appframework.membership.data.UserBean;
import it.snova.appframework.membership.data.UserManager;
import it.snova.hbaselib.framework.HClient;
import it.snova.web.gui.servlet.exceptions.AppServletException;
import it.snova.web.gui.servlet.parameters.ServletParameter;
import it.snova.web.gui.servlet.response.AppResponse;
import it.snova.web.gui.servlet.response.ClientErrorResponse;
import it.snova.web.gui.servlet.response.OKResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class LoginServlet extends HttpServlet
{
  protected static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    String authOK = null;
    String authKO = null;
    try {
      authOK = ServletParameter.build(request)
          .name("j_authokurl")
          .value();
      authKO = ServletParameter.build(request)
          .name("j_authkourl")
          .value();

      String userName = ServletParameter.build(request)
          .name("j_username")
          .required()
          .value();
      String userPwd = ServletParameter.build(request)
          .name("j_password")
          .required()
          .value();
      
      loginUser(userName, userPwd);
      
      OKResponse.build()
        .redirect(authOK)
        .process(response);
    } catch (AppServletException e) {
      logger.log(Level.SEVERE, e.getMessage(), e);
      AppResponse r = e.response();
      if (!StringUtils.isEmpty(authKO)) {
        r.redirect(authKO);
      }
      r.process(response);
    }
  }
  
  private boolean loginUser(String name, String pwd) throws AppServletException
  {
    try {
      HClient client = new HClient().init();
      UserManager userManager = new UserManager(client);

      UserBean user = userManager.loginUser(name, pwd);
      return user != null;
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage(), e);
      throw new AppServletException(ClientErrorResponse.build());
    }
  }
}
