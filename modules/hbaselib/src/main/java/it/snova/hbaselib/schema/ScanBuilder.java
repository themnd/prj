package it.snova.hbaselib.schema;

import it.snova.hbaselib.schema.SchemaBuilder.ColumnDescriptor;

import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class ScanBuilder<T>
{
  TableBuilder<T> tb;
  Scan scan;
  
  protected ScanBuilder(TableBuilder<T> tb)
  {
    this.tb = tb;
    scan = new Scan();
  }
  
  public ScanBuilder<T> setCaching(int caching)
  {
    scan.setCaching(caching);
    return this;
  }
  
  public ScanBuilder<T> setMaxVersions(int maxVersions)
  {
    scan.setMaxVersions(maxVersions);
    return this;
  }
  
  public ScanBuilder<T> addColumn(byte[] family, byte[] qualifier)
  {
    scan.addColumn(family, qualifier);
    return this;
  }
  
  public ScanBuilder<T> addFamily(byte[] family)
  {
    scan.addFamily(family);
    return this;
  }
  
  public ScanProcessor<T> process(ScanProcessor<T> processor) throws Exception
  {
    HTable table = tb.initTable();
    try {
      ResultScanner scanner = table.getScanner(scan);
      while (true) {
        Result r = scanner.next();
        if (r == null) {
          break;
        }

        T object = processor.createObject();
        ClassParser<T> parser = new ClassParser<T>((Class<T>) object.getClass());
        
        for (ColumnDescriptor d: tb.schema.columns) {
          byte[] v = r.getValue(Bytes.toBytes(d.family.getName()), Bytes.toBytes(d.qualifier));
          parser.setObjectValue(d.f, object, new String(v));
        }

        if (processor.processResult(object)) {
          break;
        }
      }
    } finally {
      table.close();
    }
    return processor;
  }
  
}
