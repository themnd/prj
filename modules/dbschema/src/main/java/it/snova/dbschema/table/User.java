package it.snova.dbschema.table;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;

  @ManyToOne
  @JoinColumn(name = "domain")
  Domain domain;

  String login;
  String name;
  String pwd;
  String email;

  @ManyToMany(mappedBy = "members", cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
  private Collection<Group> groups;

  public User(String login, String name)
  {
    this.login = login;
    this.name = name;
    groups = new ArrayList<Group>();
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

  public Collection<Group> getGroups()
  {
    return groups;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (id ^ (id >>> 32));
    return result;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    User other = (User) obj;
    if (id != other.id) {
      return false;
    }
    return true;
  }

}
