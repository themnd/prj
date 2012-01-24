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
  
  public PasswordEntity generatePassword()
  {
    return encrypter.encrypt(gen.generatePassword(policy.getMinLength(), policy.getMaxLength()));
  }
  
  public boolean verifyPassword(PasswordEntity p, String pwd)
  {
    return encrypter.verifyPassword(pwd, p.getPwd());
  }
  
  static public PasswordGenerator factory()
  {
    return new PasswordGenerator(
        new SimplePasswordGenerator(),
        new SimplePasswordPolicy());
  }

}
