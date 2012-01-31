package it.snova.hbaselib.schema;

import it.snova.hbaselib.framework.HClient;
import it.snova.hbaselib.schema.annotation.Column;
import it.snova.hbaselib.schema.annotation.Family;
import it.snova.hbaselib.schema.annotation.Table;
import it.snova.hbaselib.schema.descriptors.FamilyDescriptor;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
  List<FamilyDescriptor> families;

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

    for (FamilyDescriptor f: families) {      
      HColumnDescriptor col = new HColumnDescriptor(
          Bytes.toBytes(f.getName()),
          f.getMaxVersions(),
          f.getCompressionType(),
          f.isInMemory(),
          f.isBlockCacheEnabled(),
          f.getBlockSize(),
          f.getTimeToLive(),
          f.getBloomFilter(),
          f.getScope());
      descriptor.addFamily(col);
    }
    return descriptor;
  }

  private void initColumns()
  {
    columns = new ArrayList<ColumnDescriptor>();
    families = new ArrayList<FamilyDescriptor>();
    
    @SuppressWarnings("rawtypes")
    Class clazz = tableClass;
    while (clazz != null) {

      FamilyDescriptor family = null;
      
      Family familyAnnotation = (Family) clazz.getAnnotation(Family.class);
      if (familyAnnotation != null) {
        family = new FamilyDescriptor(familyAnnotation);
        families.add(family);
      }

      for (Field f: clazz.getDeclaredFields()) {
        
        Column c = f.getAnnotation(Column.class);
        if (c == null) {
          continue;
        }
        
        FamilyDescriptor columnFamily = null;
        
        String fieldFamily = c.family();
        if (fieldFamily.length() > 0) {
          columnFamily = new FamilyDescriptor(c);
          families.add(columnFamily);
        } else {
          columnFamily = family;
        }
        
        if (columnFamily == null) {
          throw new InvalidParameterException("Family has not been defined for table " + clazz.getName() + " or field " + f.getName());
        }
        
        String qualifier = c.column();
        if (qualifier.length() == 0) {
          qualifier = f.getName();
        }
        
        columns.add(new ColumnDescriptor(f, columnFamily, qualifier));
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
    FamilyDescriptor family;
    String qualifier;
    
    private ColumnDescriptor(Field f, FamilyDescriptor family, String qualifier)
    {
      if (family == null) {
        throw new InvalidParameterException("Family has not been defined");
      }
      
      if (StringUtils.isEmpty(qualifier)) {
        throw new InvalidParameterException("Qualifier has not been defined");
      }

      this.f = f;
      this.family = family;
      this.qualifier = qualifier;
    }
  }

}
