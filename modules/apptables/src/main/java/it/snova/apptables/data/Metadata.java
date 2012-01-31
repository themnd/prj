package it.snova.apptables.data;

import it.snova.hbaselib.schema.annotation.Column;
import it.snova.hbaselib.schema.annotation.Family;

@Family(family = "meta")
public abstract class Metadata
{
  @Column(column="created")
  private long creation;
  
  @Column(column="modified")
  private long modified;

  @Column(column="modifier")
  private String modifierId;

  public long getCreation()
  {
    return creation;
  }

  public void setCreation(long creation)
  {
    this.creation = creation;
  }

  public long getModified()
  {
    return modified;
  }

  public void setModified(long modified)
  {
    this.modified = modified;
  }

  public String getModifierId()
  {
    return modifierId;
  }

  public void setModifierId(String modifierId)
  {
    this.modifierId = modifierId;
  }

}
