package it.snova.hbaselib.schema;

import java.io.IOException;

public abstract class ScanProcessor<T>
{
  Class<T> c;
  
  public ScanProcessor(Class<T> c)
  {
    this.c = c;
  }
  
  protected T createObject() throws Exception
  {
    return c.newInstance();
  }
  
  public abstract boolean processResult(T result) throws IOException;
}
