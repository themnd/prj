package it.snova.dbschema.table;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="domains")
public class Domain
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  long id;
  
  String name;
  
  List<User> users = new ArrayList<User>();
  
  public Domain(String name)
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
  
}
