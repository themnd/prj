package it.snova.apptables.data;

import it.snova.hbaselib.schema.annotation.Column;
import it.snova.hbaselib.schema.annotation.RowId;
import it.snova.hbaselib.schema.annotation.Sequence;
import it.snova.hbaselib.schema.annotation.Table;

@Table(name = "domain")
public class DomainTable extends Metadata
{
  @Column
  @RowId
  @Sequence
  private String id;
  
  @Column
  private String name;
  
  @Column
  private String domain;

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getDomain()
  {
    return domain;
  }

  public void setDomain(String domain)
  {
    this.domain = domain;
  }

}
