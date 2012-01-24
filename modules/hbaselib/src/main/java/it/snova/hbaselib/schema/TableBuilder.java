package it.snova.hbaselib.schema;

import it.snova.hbaselib.schema.SchemaBuilder.ColumnDescriptor;
import it.snova.hbaselib.schema.annotation.RowId;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.InvalidParameterException;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;


public class TableBuilder<T>
{
  SchemaBuilder<T> schema;

  public TableBuilder(SchemaBuilder<T> schemaBuilder)
  {
    schema = schemaBuilder;
  }
  
  protected HTable initTable() throws IOException
  {
    return new HTable(schema.name);
  }
  
  private byte[] getRowId(Object table) throws Exception
  {
    byte[] rowkey = null;

    for (Field f: schema.tableClass.getDeclaredFields()) {
      
      RowId r = f.getAnnotation(RowId.class);
      if (r == null) {
        continue;
      }
      
      String s = (String) getObjectValue(f, table);
      if (rowkey == null) {
        rowkey = Bytes.toBytes(s);
      } else {
        rowkey = Bytes.add(rowkey, Bytes.toBytes(s));
      }
    }
    return rowkey;
  }

  public void add(T object) throws Exception
  {
    if (!object.getClass().equals(schema.tableClass)) {
      throw new InvalidParameterException("The given object does not have the same class as the schema");
    }
    
    HTable table = initTable();
    
    byte[] rowId = getRowId(object);
    Put put = new Put(rowId);
    for (ColumnDescriptor d: schema.columns) {
      Field f = d.f;
      Object o = getObjectValue(f, object);
      if (o != null) {
        String s = o.toString();
        put.add(Bytes.toBytes(d.family), Bytes.toBytes(d.qualifier), Bytes.toBytes(s));        
      }
    }
    
    table.put(put);
    table.flushCommits();
    table.close();
  }
  
  public T get(T object) throws Exception
  {
    if (!object.getClass().equals(schema.tableClass)) {
      throw new InvalidParameterException("The given object does not have the same class as the schema");
    }
    
    HTable table = initTable();
    
    byte[] rowId = getRowId(object);
    
    Get get = new Get(rowId);
    Result r = table.get(get);
    if (r.isEmpty()) {
      return null;
    }
    
    for (ColumnDescriptor d: schema.columns) {
      byte[] v = r.getValue(Bytes.toBytes(d.family), Bytes.toBytes(d.qualifier));
      setObjectValue(d.f, object, new String(v));
    }
    
    return object;
  }
  
  public ScanBuilder<T> scan()
  {
    ScanBuilder<T> scan = new ScanBuilder<T>(this);
    return scan;
  }
  
  public SequenceBuilder getSequence() throws IOException
  {
    return schema.client.getZContext().getSequence(schema.tableClass);
  }
  
  private Object getObjectValue(Field f, Object o) throws Exception
  {
    if (Modifier.isPublic(f.getModifiers())) {
      return (String) f.get(o);
    } else {
      String getName = f.getName();
      getName = "get" + getName.substring(0, 1).toUpperCase() + getName.substring(1);
      Method m = schema.tableClass.getMethod(getName);
      if (m != null) {
        return m.invoke(o, null);
      }
      return null;
    }
  }
  
  private void setObjectValue(Field f, Object o, Object v) throws Exception
  {
    if (Modifier.isPublic(f.getModifiers())) {
      f.set(o, v);
    } else {
      String setName = f.getName();
      setName = "set" + setName.substring(0, 1).toUpperCase() + setName.substring(1);
      {
        try {
          Method m = schema.tableClass.getMethod(setName, String.class);
          m.invoke(o, v);
          return;
        } catch (java.lang.NoSuchMethodException e) {
        }
      }
      {
        try {
          long vl = Long.parseLong((String) v);
          try {
            Method m = schema.tableClass.getMethod(setName, Long.TYPE);
            m.invoke(o, vl);
            return;
          } catch (java.lang.NoSuchMethodException e) {
          }
        } catch (NumberFormatException e1) {
        }
      }
      {
        try {
          int vi = Integer.parseInt((String) v);
          try {
            Method m = schema.tableClass.getMethod(setName, Integer.TYPE);
            m.invoke(o, vi);
            return;
          } catch (java.lang.NoSuchMethodException e) {
          }
        } catch (NumberFormatException e1) {
        }
      }
      
      throw new InvalidParameterException("cannot find a suitable method for " + setName);
    }
  }

}
