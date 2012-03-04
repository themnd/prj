package it.snova.hbaselib.schema;

public class Timetrack
{
  String title;
  long time;
  
  public Timetrack(String title)
  {
    this.title = title;
    this.time = System.nanoTime();
  }
  
  public void end()
  {
    long end = System.nanoTime();
    time = end - time;
    
    System.out.println(title + " took " + time);
  }
}
