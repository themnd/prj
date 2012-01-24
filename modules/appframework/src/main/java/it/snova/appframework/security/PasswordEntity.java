package it.snova.appframework.security;

public class PasswordEntity
{
  protected String salt;
  protected String pwd;
  
  public PasswordEntity(String salt, String pwd)
  {
    this.salt = salt;
    this.pwd = pwd;
  }
  
  public String getSalt()
  {
    return salt;
  }
  
  public String getPwd()
  {
    return pwd;
  }

}
