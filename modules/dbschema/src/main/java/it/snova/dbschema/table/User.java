package it.snova.dbschema.table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  long id;
  
  @ManyToOne
  @JoinColumn(name="domain")
  Domain domain;
  
  String login;
  String name;
  String pwd;
  String email;
  
  public User(String login, String name)
  {
    this.login = login;
    this.name = name;
  }
  
  public long getId()
  {
    return id;
  }
  
  public String getLogin()
  {
    return login;
  }
  
  public String getName()
  {
    return name;
  }
  
  public String getPwd()
  {
    return pwd;
  }

  public void setPwd(String pwd)
  {
    this.pwd = pwd;
  }
  
  public Domain getDomain()
  {
    return domain;
  }
  
  public void setDomain(Domain domain)
  {
    this.domain = domain;
  }
  
  public String getEmail()
  {
    return email;
  }
  
  public void setEmail(String email)
  {
    this.email = email;
  }
}
