package it.snova.appframework.security;

import java.util.Random;

public class SimplePasswordGenerator
  implements
    IPasswordGenerator
{
  static Random rnd = new Random();
  static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnoprstuvwxyz![]$";

  @Override
  public char[] generatePassword(int minLength, int maxLength)
  {
    int len = -1;
    int range = maxLength - minLength;
    if (range <= 0) {
      len = maxLength;
    } else {
      len = rnd.nextInt(range) + minLength;
    }
    char[] array = new char[len];
    for (int idx = 0; idx < len; idx++) {
      array[idx] = AB.charAt(rnd.nextInt(AB.length()));
    }
    return array;
  }

}
