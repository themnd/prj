package it.snova.appframework.security;

public class SimplePasswordPolicy
  implements
    IPasswordPolicy
{
  static private final int PWDLEN = 8;

  @Override
  public int getMinLength()
  {
    return PWDLEN;
  }

  @Override
  public int getMaxLength()
  {
    return PWDLEN;
  }

}
