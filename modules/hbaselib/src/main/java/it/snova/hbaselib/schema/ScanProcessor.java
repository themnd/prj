package it.snova.hbaselib.schema;

import java.io.IOException;

public abstract class ScanProcessor<T>
{
  Class<T> c;
  boolean stop;
  
  public ScanProcessor(Class<T> c)
  {
    this.c = c;
    this.stop = false;
  }
  
  protected T createObject() throws Exception
  {
    return c.newInstance();
  }
  
  public void setStop(boolean stop)
  {
    this.stop = stop;
  }
  
  public boolean isStop()
  {
    return this.stop;
  }
  
  public abstract void processResult(T result) throws IOException;
}
