package it.snova.hbaselib.schema;

import it.snova.hbaselib.framework.ZContext;
import it.snova.hbaselib.schema.annotation.Sequence;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.security.InvalidParameterException;

import org.apache.hadoop.hbase.util.Bytes;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.data.Stat;

import com.netflix.curator.framework.CuratorFramework;

public class SequenceBuilder
{
  protected static final String sequenceNS = "/sequences/";
  
  ZContext context;
  String name;
  long startingIndex;
  
  public SequenceBuilder(ZContext context, String name)
  {
    init(context, name, 0);
  }

  public SequenceBuilder(ZContext context, Class c)
  {
    String fieldName = null;
    Field[] fields = c.getDeclaredFields();
    for (Field f: fields) {
      if (f.getAnnotation(Sequence.class) != null) {
        if (fieldName != null) {
          throw new InvalidParameterException("The class " + c.getCanonicalName() + " contains more than one sequence");
        }
        fieldName = f.getName();
      }
    }
    if (fieldName == null) {
      throw new InvalidParameterException("The class " + c.getCanonicalName() + " does not contains sequences");        
    }
    initFromClass(context, c, fieldName);
  }
  
  public SequenceBuilder(ZContext context, Class c, String field)
  {
    initFromClass(context, c, field);
  }
  
  private void initFromClass(ZContext context, Class c, String field)
  {
    String name = "";
    long startingIndex = 100;
    try {
      Field f = c.getDeclaredField(field);
      Sequence s = f.getAnnotation(Sequence.class);
      if (s != null) {
        startingIndex = s.startingIndex();
        name = s.name();
        if (name.length() == 0) {
          name = field;
        }
      } else {
        name = field;
      }
      name = c.getName() + "." + name + ".counter";
    } catch (Exception e) {
      e.printStackTrace();
      throw new InvalidParameterException("The given field " + field + " does not exists or it is inaccessible");
    }
    init(context, name, startingIndex);
  }

  public SequenceBuilder(ZContext context, String name, long startingIndex)
  {
    init(context, name, startingIndex);
  }
  
  private void init(ZContext context, String name, long startingIndex)
  {
    System.out.println("init seq " + name + " with idx " + startingIndex);
    
    this.context = context;
    this.name = name;
    this.startingIndex = startingIndex;    
  }
  
  public SequenceBuilder withStartingIndex(long startingIndex)
  {
    this.startingIndex = startingIndex;
    return this;
  }
  
  public SequenceBuilder create() throws Exception
  {
    CuratorFramework client = null;
    try {
      client = context.start();
      client.create().creatingParentsIfNeeded().forPath(sequenceNS + name);
    } catch(KeeperException e) {
      if (!e.code().equals(Code.NODEEXISTS)) {
        throw e;
      }
    } finally {
      client.close();
    }
    return this;
  }
  
  public SequenceBuilder delete() throws Exception
  {
    CuratorFramework client = null;
    try {
      client = context.start();
      client.delete().forPath(sequenceNS + name);
    } catch(KeeperException e) {
      if (!e.code().equals(Code.NONODE)) {
        throw e;
      }
    } finally {
      client.close();
    }
    return this;    
  }
  
  public long getNextId() throws Exception
  {
    CuratorFramework client = context.start();
    boolean keepGoing = true;
    while (keepGoing) {
      try {
        Stat nodeStat = new Stat();
        Stat setDataStat = null;
        byte[] bytes = client.getData().storingStatIn(nodeStat).forPath(sequenceNS + name);
        ByteBuffer buf = ByteBuffer.wrap(bytes);
        long value = 0;
        if (bytes.length == 0) {
          value = startingIndex;
          value++;
          bytes = Bytes.toBytes(value);
          buf = ByteBuffer.wrap(bytes);
        } else {
          value = buf.getLong();
          value++;
          buf.rewind();
          buf.putLong(value);
        }
        try {
          setDataStat = client.setData().forPath(sequenceNS + name, buf.array());
          return value;
        } catch (KeeperException e) {
          e.printStackTrace();
          if(e.code().equals(Code.BADVERSION)) {
            nodeStat = setDataStat;
          }
        }
       } finally {
         client.close();
       }      
    }
    return -1;
  }

}
