package it.snova.hbaselib.schema;

import it.snova.hbaselib.schema.SchemaBuilder.ColumnDescriptor;
import it.snova.hbaselib.schema.annotation.RowId;

import java.io.IOException;
import java.lang.reflect.Field;
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

    ClassParser<T> parser = new ClassParser<T>((Class<T>) schema.tableClass);
    
    for (Field f: schema.tableClass.getDeclaredFields()) {
      
      RowId r = f.getAnnotation(RowId.class);
      if (r == null) {
        continue;
      }
      
      String s = (String) parser.getObjectValue(f, table);
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
    
    ClassParser<T> parser = new ClassParser<T>((Class<T>) schema.tableClass);

    byte[] rowId = getRowId(object);
    Put put = new Put(rowId);
    for (ColumnDescriptor d: schema.columns) {
      Field f = d.f;
      Object o = parser.getObjectValue(f, object);
      if (o != null) {
        String s = o.toString();
        put.add(Bytes.toBytes(d.family.getName()), Bytes.toBytes(d.qualifier), Bytes.toBytes(s));        
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
    
    ClassParser<T> parser = new ClassParser<T>((Class<T>) object.getClass());

    for (ColumnDescriptor d: schema.columns) {
      byte[] v = r.getValue(Bytes.toBytes(d.family.getName()), Bytes.toBytes(d.qualifier));
      parser.setObjectValue(d.f, object, new String(v));
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
    
}
