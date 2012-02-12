package it.snova.web.gui.servlet.response;


public class RedirectResponse extends AppResponse
{
  protected RedirectResponse()
  {
    super(302);
  }

  static public RedirectResponse build()
  {
    return new RedirectResponse();
  }
  
}
