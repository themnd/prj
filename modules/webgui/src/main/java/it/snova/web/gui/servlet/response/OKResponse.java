package it.snova.web.gui.servlet.response;


public class OKResponse extends AppResponse
{
  protected OKResponse()
  {
    super(200);
  }

  static public OKResponse build()
  {
    return new OKResponse();
  }
  
}
