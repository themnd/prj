package it.snova.hbaselib.framework;

import it.snova.hbaselib.schema.SequenceBuilder;

import java.io.IOException;

import com.netflix.curator.framework.CuratorFramework;

public class ZContext
{
  ZClient client;
  String path;
  
  protected ZContext(ZClient client, String path)
  {
    this.client = client;
    this.path = path;
  }
  
  private CuratorFramework getClient() throws IOException
  {
    return client.getClient(path);
  }
  
  public CuratorFramework start() throws IOException
  {
    CuratorFramework client = getClient();
    client.start();
    return client;
  }

  public SequenceBuilder getSequence(Class c)
  {
    return new SequenceBuilder(this, c);    
  }
  
  public SequenceBuilder getSequence(Class c, String field)
  {
    return new SequenceBuilder(this, c, field);    
  }

  public SequenceBuilder getSequence(String name)
  {
    return new SequenceBuilder(this, name);
  }
}
