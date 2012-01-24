package it.snova.hbaselib.schema;

import it.snova.hbaselib.framework.HClient;
import it.snova.hbaselib.schema.annotation.Column;
import it.snova.hbaselib.schema.annotation.Table;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.util.Bytes;


public class SchemaBuilder<T>
{
  HClient client;
  Class<T> tableClass;
  
  String name;
  String description;
  
  List<ColumnDescriptor> columns;

  public SchemaBuilder(HClient client, Class<T> tableClass)
  {
    this.client = client;
    this.tableClass = tableClass;

    Table tableAnnotation = (Table) tableClass.getAnnotation(Table.class);
    if (tableAnnotation == null) {
      throw new InvalidParameterException("The given table does not have the Table annotation");
    }
    
    name = tableAnnotation.name();
    description = tableAnnotation.description();
    
    initColumns();
  }
  
  public boolean tableExists() throws IOException
  {
    return client.getHBA().tableExists(name);
  }
  
  public void disableTable() throws IOException
  {
    client.getHBA().disableTable(name);
  }
  
  public void deleteTable() throws IOException
  {
    client.getHBA().deleteTable(name);
  }
  
  public void createTable() throws IOException
  {
    HTableDescriptor descriptor = createDescriptor();
    client.getHBA().createTable(descriptor);
  }

  private HTableDescriptor createDescriptor()
  {
    HTableDescriptor descriptor = new HTableDescriptor(name);

    for (ColumnDescriptor d: columns) {
      
      Column c = d.c;
      
      String compressionType = c.compressionType();
      if (compressionType.length() == 0) {
        compressionType = HColumnDescriptor.DEFAULT_COMPRESSION;
      }
      String bloomFilter = c.bloomFilter();
      if (bloomFilter.length() == 0) {
        bloomFilter = HColumnDescriptor.DEFAULT_BLOOMFILTER;
      }
      
      HColumnDescriptor col = new HColumnDescriptor(
          Bytes.toBytes(d.family),
          c.maxVersions(),
          compressionType,
          c.inMemory(),
          c.blockCacheEnabled(),
          c.blockSize(),
          c.timeToLive(),
          bloomFilter,
          c.scope());
      descriptor.addFamily(col);
    }
    return descriptor;
  }

  private void initColumns()
  {
    columns = new ArrayList<ColumnDescriptor>();
    
    @SuppressWarnings("rawtypes")
    Class clazz = tableClass;
    while (clazz != null) {
      for (Field f: clazz.getDeclaredFields()) {
        
        Column c = f.getAnnotation(Column.class);
        if (c == null) {
          continue;
        }
        
        String family = c.family();
        if (family.length() == 0) {
          family = f.getName();
        }
        
        String qualifier = c.column();
        
        columns.add(new ColumnDescriptor(f, c, family, qualifier));
      }
      clazz = clazz.getSuperclass();
    }
  }

  public TableBuilder<T> getTableBuilder()
  {
    return new TableBuilder<T>(this);
  }
  
  protected class ColumnDescriptor
  {
    Field f;
    Column c;
    String family;
    String qualifier;
    
    private ColumnDescriptor(Field f, Column c, String family, String qualifier)
    {
      this.f = f;
      this.c = c;
      this.family = family;
      this.qualifier = qualifier;
    }
  }

}
