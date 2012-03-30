package it.snova.web.gui.servlet;

import it.snova.appframework.context.Context;
import it.snova.appframework.membership.data.UserManager;
import it.snova.dbschema.table.User;
import it.snova.web.gui.config.utils.Configuration;
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
import javax.servlet.http.HttpSession;

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
      
      logger.info("logged in " + userName);
      
      User user = loginUser(userName, userPwd);
      if (user != null) {

        setSessionUser(request, user);
        
        OKResponse.build()
        .redirect(authOK)
        .process(response);
      } else {
        OKResponse.build()
        .redirect(authKO)
        .process(response);
      }
    } catch (AppServletException e) {
      logger.log(Level.SEVERE, e.getMessage(), e);
      AppResponse r = e.response();
      if (!StringUtils.isEmpty(authKO)) {
        r.redirect(authKO);
      }
      r.process(response);
    }
  }
  
  private User loginUser(String name, String pwd) throws AppServletException
  {
    try {
      Context context = Configuration.getInstance().getContext();
      UserManager userManager = new UserManager(context);

      return userManager.loginUser(name, pwd);
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage(), e);
      throw new AppServletException(ClientErrorResponse.build());
    }
  }
  
  static public User getSessionUser(HttpServletRequest request)
  {
    HttpSession session = ((HttpServletRequest)request).getSession(false);
    if (session != null) {
      return (User)session.getAttribute(LoginServlet.class.getName() + ".user");
    }
    return null;
  }
  
  private void setSessionUser(HttpServletRequest request, User user)
  {
    HttpSession session = ((HttpServletRequest)request).getSession(true);
    session.setAttribute(LoginServlet.class.getName() + ".user", user);
  }
}
