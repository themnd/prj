package it.snova.hbaselib.framework;

import java.io.IOException;

import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.retry.RetryOneTime;

public class ZClient
{
  String connectString;
  
  public ZClient()
  {
    this.connectString = "localhost:2181";
  }
  
  public ZClient(String connectString)
  {
    this.connectString = connectString;
  }
  
  public ZContext getContext() throws IOException
  {
    return getContext("/");
  }

  public ZContext getContext(String path) throws IOException
  {
    return new ZContext(this, path);
  }
  
  public CuratorFramework getClient(String path) throws IOException
  {
    return CuratorFrameworkFactory.builder()
        .namespace(path)
        .connectString(connectString)
        .retryPolicy(new RetryOneTime(10))
        .build();
  }

}
