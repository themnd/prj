package it.snova.web.gui.servlet.response;


public class InvalidParameterResponse extends AppResponse
{
  private InvalidParameterResponse()
  {
    status = 400;
  }
  
  static public InvalidParameterResponse build()
  {
    return new InvalidParameterResponse();
  }
  
}
