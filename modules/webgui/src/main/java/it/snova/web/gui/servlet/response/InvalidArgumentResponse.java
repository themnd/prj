package it.snova.web.gui.servlet.response;


public class InvalidArgumentResponse extends AppResponse
{
  private InvalidArgumentResponse()
  {
    super(400);
  }
  
  static public InvalidArgumentResponse build()
  {
    return new InvalidArgumentResponse();
  }
  
}
