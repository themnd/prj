package it.snova.hbaselib.schema.descriptors;

import it.snova.hbaselib.schema.annotation.Column;
import it.snova.hbaselib.schema.annotation.Family;

import org.apache.hadoop.hbase.HColumnDescriptor;

public class FamilyDescriptor
{
  private String name;
  private int maxVersions;
  private String compressionType;
  private boolean inMemory;
  private boolean blockCacheEnabled;
  private int blockSize;
  private int timeToLive;
  private String bloomFilter;
  private int scope;

  public FamilyDescriptor(Family family)
  {
    name = family.family();
    maxVersions = family.maxVersions();
    compressionType = family.compressionType();
    inMemory = family.inMemory();
    blockCacheEnabled = family.blockCacheEnabled();
    blockSize = family.blockSize();
    timeToLive = family.timeToLive();
    bloomFilter = family.bloomFilter();
    scope = family.scope();
    
    if (compressionType.length() == 0) {
      compressionType = HColumnDescriptor.DEFAULT_COMPRESSION;
    }
    if (bloomFilter.length() == 0) {
      bloomFilter = HColumnDescriptor.DEFAULT_BLOOMFILTER;
    }
  }

  public FamilyDescriptor(Column column)
  {
    name = column.family();
    maxVersions = column.maxVersions();
    compressionType = column.compressionType();
    inMemory = column.inMemory();
    blockCacheEnabled = column.blockCacheEnabled();
    blockSize = column.blockSize();
    timeToLive = column.timeToLive();
    bloomFilter = column.bloomFilter();
    scope = column.scope();
    
    if (compressionType.length() == 0) {
      compressionType = HColumnDescriptor.DEFAULT_COMPRESSION;
    }
    if (bloomFilter.length() == 0) {
      bloomFilter = HColumnDescriptor.DEFAULT_BLOOMFILTER;
    }
  }
  
  public FamilyDescriptor(FamilyDescriptor family)
  {
    name = family.getName();
    maxVersions = family.getMaxVersions();
    compressionType = family.getCompressionType();
    inMemory = family.isInMemory();
    blockCacheEnabled = family.isBlockCacheEnabled();
    blockSize = family.getBlockSize();
    timeToLive = family.getTimeToLive();
    bloomFilter = family.getBloomFilter();
    scope = family.getScope();
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public int getMaxVersions()
  {
    return maxVersions;
  }

  public void setMaxVersions(int maxVersions)
  {
    this.maxVersions = maxVersions;
  }

  public String getCompressionType()
  {
    return compressionType;
  }

  public void setCompressionType(String compressionType)
  {
    this.compressionType = compressionType;
  }

  public boolean isInMemory()
  {
    return inMemory;
  }

  public void setInMemory(boolean inMemory)
  {
    this.inMemory = inMemory;
  }

  public boolean isBlockCacheEnabled()
  {
    return blockCacheEnabled;
  }

  public void setBlockCacheEnabled(boolean blockCacheEnabled)
  {
    this.blockCacheEnabled = blockCacheEnabled;
  }

  public int getBlockSize()
  {
    return blockSize;
  }

  public void setBlockSize(int blockSize)
  {
    this.blockSize = blockSize;
  }

  public int getTimeToLive()
  {
    return timeToLive;
  }

  public void setTimeToLive(int timeToLive)
  {
    this.timeToLive = timeToLive;
  }

  public String getBloomFilter()
  {
    return bloomFilter;
  }

  public void setBloomFilter(String bloomFilter)
  {
    this.bloomFilter = bloomFilter;
  }

  public int getScope()
  {
    return scope;
  }

  public void setScope(int scope)
  {
    this.scope = scope;
  }

}
