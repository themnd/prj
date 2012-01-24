package it.snova.appframework.security;

public interface IPasswordGenerator
{
  public char[] generatePassword(int minLength, int maxLength);
}
