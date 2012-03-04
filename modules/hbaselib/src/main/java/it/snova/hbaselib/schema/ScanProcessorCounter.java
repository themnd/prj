package it.snova.hbaselib.schema;

import java.io.IOException;

public abstract class ScanProcessorCounter<T> extends ScanProcessor<T>
{
  int count;
  
  public ScanProcessorCounter(Class<T> c)
  {
    super(c);
    count = 0;
  }
  
  public int getCount()
  {
    return count;
  }
  
  public void processResult(T result) throws IOException
  {
    boolean r = processSingleResult(result);
    if (r) {
      count += 1;
    }
  }
  
  public abstract boolean processSingleResult(T result) throws IOException;
}
