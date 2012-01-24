package it.snova.apptables.framework;

import it.snova.apptables.data.Metadata;
import it.snova.hbaselib.framework.DefaultValues;

import java.util.Date;

public class Context
{
  private long creation;
  private long modified;
  private String modifierId;
  
  public Context()
  {
    init(DefaultValues.ADMINID);
  }
  
  public Context(String modifierId)
  {
    init(modifierId);
  }

  private void init(String modifierId)
  {
    long t = new Date().getTime();
    creation = t;
    modified = t;
    this.modifierId = modifierId;
  }
  
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
  
  public <T extends Metadata> T withCreation(T table)
  {
    table.setCreation(creation);
    return table;
  }
  
  public <T extends Metadata> T withModifier(T table)
  {
    table.setModified(modified);
    table.setModifierId(modifierId);
    return table;
  }

  public <T extends Metadata> T with(T table)
  {
    return withModifier(withCreation(table));
  }

}
