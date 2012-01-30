package it.snova.web.gui.servlet.response;


public class ClientErrorResponse extends AppResponse
{
  private ClientErrorResponse()
  {
    status = 500;
  }
  
  static public ClientErrorResponse build()
  {
    return new ClientErrorResponse();
  }
  
}
