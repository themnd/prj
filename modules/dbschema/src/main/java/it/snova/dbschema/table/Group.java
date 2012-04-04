package it.snova.dbschema.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
  
  @ManyToMany(mappedBy="groups", cascade=CascadeType.DETACH, fetch=FetchType.EAGER)
  @JoinTable(name="usergroups", 
    joinColumns=@JoinColumn(name="groupid"),
    inverseJoinColumns=@JoinColumn(name="userid"))
  private List<User> members;
  
  public Group()
  {
    members = new ArrayList<User>();
  }

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
  
  public Collection<User> getMembers()
  {
    return members;
  }
  
  public void addMember(User u)
  {
    if (!members.contains(u)) {
      members.add(u);
    }
  }
  
  public void removeMember(User u)
  {
    if (members.contains(u)) {
      members.remove(u);
    }
  }
  
  public boolean hasMember(User u)
  {
    return members.contains(u);
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
    Group other = (Group) obj;
    if (id != other.id) {
      return false;
    }
    return true;
  }

}
