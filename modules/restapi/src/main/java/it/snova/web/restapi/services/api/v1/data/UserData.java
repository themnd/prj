package it.snova.web.restapi.services.api.v1.data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserData
{
  public String sessionId;
  public long id;
  public String name;
}
