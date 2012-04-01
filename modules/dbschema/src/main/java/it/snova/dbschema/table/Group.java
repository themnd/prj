package it.snova.dbschema.table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="groups")
public class Group
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  long id;
  
  @ManyToOne
  @JoinColumn(name="domain")
  Domain domain;

  String name;

  public long getId()
  {
    return id;
  }

  public Domain getDomain()
  {
    return domain;
  }

  public void setDomain(Domain domain)
  {
    this.domain = domain;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

}
