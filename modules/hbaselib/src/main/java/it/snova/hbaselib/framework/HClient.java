package it.snova.hbaselib.framework;

import java.io.IOException;

import it.snova.hbaselib.schema.SchemaBuilder;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;


public class HClient
{
  Configuration hc;
  HBaseAdmin hba;
  ZClient zc;
  
  public HClient()
  {
    initHC(null);
  }
  
  public HClient(Configuration hc)
  {
    initHC(hc);
  }
  
  private void initHC(Configuration hc)
  {
    if (hc == null) {
      System.out.println("getting config...");
      hc = HBaseConfiguration.create();
    }
    this.hc = hc;
    this.hba = null;
    this.zc = null;
  }

  public HClient init() throws Exception
  {
    if (hba == null) {
      System.out.println("connecting...");
      hba = new HBaseAdmin(hc);
    }
    if (zc == null) {
      zc = new ZClient();
    }
    return this;
  }
  
  public <T> SchemaBuilder<T> getSchemaBuilder(Class<T> table)
  {
    return new SchemaBuilder<T>(this, table);
  }
  
  public HBaseAdmin getHBA()
  {
    return hba;
  }
  
  public ZContext getZContext(String path) throws IOException
  {
    return zc.getContext(path);
  }
  
  public ZContext getZContext() throws IOException
  {
    return zc.getContext();
  }
  
}
