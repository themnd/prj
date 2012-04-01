package it.snova.appframework.membership.data;

import it.snova.dbschema.table.Group;
import it.snova.dbschema.table.User;

import javax.persistence.Query;

public class DBIterator<T>
{
  int start = -1;
  int limit = -1;
  ObjIterator<T> iterator;
  
  public int getStart()
  {
    return start;
  }
  
  public int getLimit()
  {
    return limit;
  }
  
  public DBIterator<T> setStart(int start)
  {
    this.start = start;
    return this;
  }
  
  public DBIterator<T> setLimit(int limit)
  {
    this.limit = limit;
    return this;
  }
  
  protected void setQueryParams(Query q)
  {
    if (getStart() >= 0) {
      q.setFirstResult(getStart());
    }
    if (getLimit() > 0) {
      q.setMaxResults(getLimit());
    }
  }

  public DBIterator<T> setIterator(ObjIterator<T> iterator)
  {
    this.iterator = iterator;
    return this;
  }
  
  public ObjIterator<T> getIterator()
  {
    return iterator;
  }
  
  public interface ObjIterator<T>
  {    
    public boolean process(T obj);
  }
  
  public interface UserIterator extends ObjIterator<User>
  {
  }
  
  public interface GroupIterator extends ObjIterator<Group>
  {
  }
}
