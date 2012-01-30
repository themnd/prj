package it.snova.web.gui.servlet.parameters;

import it.snova.web.gui.servlet.exceptions.AppServletException;
import it.snova.web.gui.servlet.response.InvalidArgumentResponse;
import it.snova.web.gui.servlet.response.InvalidParameterResponse;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class ServletParameter
{
  HttpServletRequest request;
  
  boolean required;
  String name;
  
  private ServletParameter(HttpServletRequest request)
  {
    this.request = request;
    
    required = false;
    name = null;
  }
  
  public ServletParameter required()
  {
    required = true;
    return this;
  }
  
  public ServletParameter name(String name)
  {
    this.name = name;
    return this;
  }
  
  public String value() throws AppServletException 
  {
    if (StringUtils.isEmpty(name)) {
      throw new AppServletException(
          InvalidArgumentResponse.build());
    }
    
    String value = request.getParameter(name);
    if (required && value == null) {
      throw new AppServletException(
          InvalidParameterResponse.build());
    }
    
    return value;
  }
  
  static public ServletParameter build(HttpServletRequest request)
  {
    return new ServletParameter(request);
  }
}
