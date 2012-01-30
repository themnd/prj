package it.snova.web.gui.servlet.exceptions;

import it.snova.web.gui.servlet.response.AppResponse;

public class AppServletException extends Exception
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  AppResponse response;
  
  public AppServletException(AppResponse response)
  {
    this.response = response;
  }
  
  public AppResponse response()
  {
    return response;
  }

}
