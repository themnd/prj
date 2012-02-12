package it.snova.web.gui.servlet.response;


public class InvalidParameterResponse extends AppResponse
{
  private InvalidParameterResponse()
  {
    super(400);
  }
  
  static public InvalidParameterResponse build()
  {
    return new InvalidParameterResponse();
  }
  
}
