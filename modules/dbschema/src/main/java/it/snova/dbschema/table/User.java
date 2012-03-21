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
  
  public User(String login, String name, String pwd)
  {
    this.login = login;
    this.name = name;
    this.pwd = pwd;
  }
  
  public Domain getDomain()
  {
    return domain;
  }

}
