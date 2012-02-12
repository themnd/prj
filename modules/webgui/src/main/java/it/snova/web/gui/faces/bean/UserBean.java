package it.snova.web.gui.faces.bean;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "user")
public class UserBean
{
  private String name;
  
  public String getName()
  {
    return name;
  }

  public UserBean setName(String name)
  {
    this.name = name;
    return this;
  }
  
}
