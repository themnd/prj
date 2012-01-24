package it.snova.hbaselib.schema.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.hadoop.hbase.HColumnDescriptor;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
  
  public String family() default "";
  public String column() default "value";
  
  public int maxVersions() default HColumnDescriptor.DEFAULT_VERSIONS;
  public String compressionType() default "";
  public boolean inMemory() default HColumnDescriptor.DEFAULT_IN_MEMORY;
  public boolean blockCacheEnabled() default HColumnDescriptor.DEFAULT_BLOCKCACHE;
  public int blockSize() default HColumnDescriptor.DEFAULT_BLOCKSIZE;
  public int timeToLive() default HColumnDescriptor.DEFAULT_TTL;
  public String bloomFilter() default "";
  public int scope() default 0;
}
