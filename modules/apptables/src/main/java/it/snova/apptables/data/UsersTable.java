package it.snova.apptables.data;

import it.snova.hbaselib.schema.annotation.Column;
import it.snova.hbaselib.schema.annotation.RowId;
import it.snova.hbaselib.schema.annotation.Sequence;
import it.snova.hbaselib.schema.annotation.Table;

@Table(name = "users")
public class UsersTable extends Metadata
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
  private String email;
  
  @Column
  private String pwd;
  
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

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getPwd()
  {
    return pwd;
  }

  public void setPwd(String pwd)
  {
    this.pwd = pwd;
  }

}
