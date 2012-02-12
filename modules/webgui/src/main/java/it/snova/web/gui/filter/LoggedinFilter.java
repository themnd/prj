package it.snova.web.gui.filter;

import it.snova.web.gui.config.utils.ConfigParser;
import it.snova.web.gui.config.utils.IConfigParser;
import it.snova.web.gui.servlet.LoginServlet;
import it.snova.web.gui.servlet.response.RedirectResponse;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class LoggedinFilter implements Filter
{
  protected static final Logger logger = Logger.getLogger(LoggedinFilter.class.getName());
  
  String loginFormUrl;

  @Override
  public void init(FilterConfig config) throws ServletException
  {
    IConfigParser parser = ConfigParser.getParser(config);
    loginFormUrl = parser.getInitParameter("login");
  }

  @Override
  public void destroy()
  {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
  {
    boolean authorized = false;
    if (request instanceof HttpServletRequest) {
      if (LoginServlet.getSessionUser((HttpServletRequest) request) != null) {
        authorized = true;
      }
    }
    
    if (authorized) {
      chain.doFilter(request, response);      
    } else {
      logger.info("filter - not authorized");
      if (!StringUtils.isEmpty(loginFormUrl)) {
        RedirectResponse
          .build()
          .redirect(loginFormUrl)
          .process((HttpServletResponse) response);
      } else {
        throw new ServletException("Unauthorized access, unable to forward to login page");        
      }
    }
  }

}
