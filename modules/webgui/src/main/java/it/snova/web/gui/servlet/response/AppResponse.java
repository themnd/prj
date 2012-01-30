package it.snova.web.gui.servlet.response;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public abstract class AppResponse
{
  int status;
  String redirectUrl;

  protected AppResponse()
  {
    status = 200;
    redirectUrl = null;
  }

  public AppResponse status(int status)
  {
    this.status = status;
    return this;
  }
  
  public AppResponse redirect(String url)
  {
    redirectUrl = url;
    return this;
  }

  public void process(HttpServletResponse response) throws IOException
  {
    response.setStatus(status);
    if (redirectUrl != null) {
      response.sendRedirect(response.encodeRedirectURL(redirectUrl));      
    }
  }
  
}
