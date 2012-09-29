package it.snova.testjpa;

public class Main
{

  /**
   * @param args
   */
  public static void main(String[] args)
  {
    DBTest test = new DBTest();
    test.setArguments(args);
    System.exit(test.execute());
  }

}
