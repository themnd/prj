package it.snova.apptables.data;

import it.snova.hbaselib.schema.annotation.Column;
import it.snova.hbaselib.schema.annotation.RowId;
import it.snova.hbaselib.schema.annotation.Sequence;
import it.snova.hbaselib.schema.annotation.Table;

@Table(name = "groups")
public class GroupsTable extends Metadata
{
  @Column
  @RowId
  @Sequence
  private String id;

  @Column
  private String domainId;
  
  @Column
  private String name;
  
  @Column
  private String ownerId;
  
  @Column
  private String members;

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getDomainId()
  {
    return domainId;
  }

  public void setDomainId(String domainId)
  {
    this.domainId = domainId;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public void setOwnerId(String ownerId)
  {
    this.ownerId = ownerId;
  }

  public String getMembers()
  {
    return members;
  }

  public void setMembers(String members)
  {
    this.members = members;
  }

}
