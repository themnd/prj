package it.snova.hbaselib.schema;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.InvalidParameterException;

public class ClassParser<T extends Object>
{
  Class<T> c;
  
  public ClassParser(Class<T> c)
  {
    this.c = c;
  }
  
  public void setObjectValue(Field f, Object o, Object v) throws Exception
  {
    if (Modifier.isPublic(f.getModifiers())) {
      f.set(o, v);
    } else {
      String setName = f.getName();
      setName = "set" + setName.substring(0, 1).toUpperCase() + setName.substring(1);
      {
        try {
          Method m = c.getMethod(setName, String.class);
          m.invoke(o, v);
          return;
        } catch (java.lang.NoSuchMethodException e) {
        }
      }
      {
        try {
          long vl = Long.parseLong((String) v);
          try {
            Method m = c.getMethod(setName, Long.TYPE);
            m.invoke(o, vl);
            return;
          } catch (java.lang.NoSuchMethodException e) {
          }
        } catch (NumberFormatException e1) {
        }
      }
      {
        try {
          int vi = Integer.parseInt((String) v);
          try {
            Method m = c.getMethod(setName, Integer.TYPE);
            m.invoke(o, vi);
            return;
          } catch (java.lang.NoSuchMethodException e) {
          }
        } catch (NumberFormatException e1) {
        }
      }
      
      throw new InvalidParameterException("cannot find a suitable method for " + setName);
    }
  }
  
  public Object getObjectValue(Field f, Object o) throws Exception
  {
    if (Modifier.isPublic(f.getModifiers())) {
      return (String) f.get(o);
    } else {
      String getName = f.getName();
      getName = "get" + getName.substring(0, 1).toUpperCase() + getName.substring(1);
      Method m = c.getMethod(getName);
      if (m != null) {
        return m.invoke(o, null);
      }
      return null;
    }
  }


}
