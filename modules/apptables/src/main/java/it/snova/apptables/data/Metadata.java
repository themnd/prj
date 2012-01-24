package it.snova.apptables.data;

import it.snova.hbaselib.schema.annotation.Column;

public abstract class Metadata
{
  @Column(family="meta", column="created")
  private long creation;
  
  @Column(family="meta", column="modified")
  private long modified;

  @Column(family="meta", column="modifier")
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
