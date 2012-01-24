package it.snova.appframework.security;

import java.security.MessageDigest;
import java.security.SecureRandom;

import org.apache.hadoop.hbase.util.Base64;
import org.apache.hadoop.hbase.util.Bytes;

public class PasswordEncrypter
{
  final static int ITERATIONS = 1000;
  final static byte fixSalt[] = Bytes.toBytes("s!$dkjhqw12yeiuqy98");
  
  final static SecureRandom random = new SecureRandom();
  
  public char[] encrypt(char[] passwd)
  {
    try {
      MessageDigest messageDigest = getMessageDigest();
      
      byte salt[] = new byte[8];
      random.nextBytes(salt);
      
      messageDigest.update(fixSalt);
      messageDigest.update(salt);
      messageDigest.update(new String(passwd).getBytes("UTF-8"));
      
      byte[] pwd = null;
      for (int count = ITERATIONS; count > 0; count--) {
        if (pwd == null) {
          pwd = messageDigest.digest();          
        } else {
          pwd = messageDigest.digest(pwd);
        }
      }
      
      StringBuffer saltR = new StringBuffer(Base64.encodeBytes(salt));
      String pwdComplete = saltR.reverse().toString() + Base64.encodeBytes(pwd);
      return pwdComplete.toCharArray();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  
  public char[] decrypt(char[] salt, char[] passwd)
  {
    try {
      MessageDigest messageDigest = getMessageDigest();
      
      byte[] decodedSalt = Base64.decode(new String(salt));
      byte[] decodedPwd = new String(passwd).getBytes("UTF-8");
      
      messageDigest.update(fixSalt);
      messageDigest.update(decodedSalt);
      messageDigest.update(decodedPwd);
      
      byte[] pwd = null;
      for (int count = ITERATIONS; count > 0; count--) {
        if (pwd == null) {
          pwd = messageDigest.digest();          
        } else {
          pwd = messageDigest.digest(pwd);
        }
      }
      
      return new String(Base64.encodeBytes(pwd)).toCharArray();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  
  public boolean verifyPassword(String encPasswd, String pwd)
  {
    if (encPasswd.length() > 12) {
      StringBuffer salt = new StringBuffer(encPasswd.substring(0, 12)).reverse();
      String origPwd = encPasswd.substring(12);
      String encNewPwd = new String(decrypt(salt.toString().toCharArray(), pwd.toCharArray()));
      return origPwd.equals(encNewPwd);
    }
    return false;
  }
  
  private MessageDigest getMessageDigest() throws Exception
  {
    return MessageDigest.getInstance("SHA-512");
  }
  
}
