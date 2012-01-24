package it.snova.appframework.security;

public class PasswordGenerator
{
  static final PasswordEncrypter encrypter = new PasswordEncrypter();
  
  IPasswordGenerator gen;
  IPasswordPolicy policy;
  
  public PasswordGenerator(IPasswordGenerator gen, IPasswordPolicy policy)
  {
    this.gen = gen;
    this.policy = policy;
  }
  
  public char[] generatePassword()
  {
    return encrypter.encrypt(gen.generatePassword(policy.getMinLength(), policy.getMaxLength()));
  }
  
  public boolean verifyPassword(String encPwd, String pwd)
  {
    return encrypter.verifyPassword(encPwd, pwd);
  }
  
  static public PasswordGenerator factory()
  {
    return new PasswordGenerator(
        new SimplePasswordGenerator(),
        new SimplePasswordPolicy());
  }

}
