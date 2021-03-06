package it.snova.hbaselib.schema;

import java.io.IOException;

public abstract class ScanProcessorSingleResult<T> extends ScanProcessor<T>
{
  public T result;
  
  public ScanProcessorSingleResult(Class<T> c)
  {
    super(c);
  }
  
  public void processResult(T result) throws IOException
  {
    boolean r = processSingleResult(result);
    if (r) {
      this.result = result;
      setStop(true);
    }
  }
  
  public abstract boolean processSingleResult(T result) throws IOException;
}
