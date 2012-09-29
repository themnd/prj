package it.snova.dbschema.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="domains")
public class Domain implements Serializable
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  long id;

  private Domain domain;
//  private List<Domain> subdomains;

  String name;

  List<User> users = new ArrayList<User>();

  public Domain() {
  }

  public Domain(String name)
  {
    this.name = name;
  }

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  public long getId()
  {
    return id;
  }

  public void setId(long id)
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
  
  @OneToMany(
      mappedBy="domain",
      targetEntity=User.class,
      fetch=FetchType.LAZY,
      cascade=CascadeType.ALL)
  public List<User> getUsers()
  {
    return users;
  }
  
  public void setUsers(List<User> users)
  {
    this.users = users;
  }

  @OneToOne
  @JoinColumn(name = "domain")
  public Domain getDomain()
  {
    return domain;
  }

  public void setDomain(Domain domain)
  {
    this.domain = domain;
  }

//  @ManyToOne
//  @JoinColumn(name="domain")
//  public List<Domain> getSubDomains()
//  {
//    return subdomains;
//  }
//
//  public void setSubDomains(List<Domain> subdomains)
//  {
//    this.subdomains = subdomains;
//  }
}
