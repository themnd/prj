package it.snova.dbschema.init;

import org.datanucleus.metadata.PersistenceUnitMetaData;

public class Initialization
{
  static public void initPersistenceClasses(PersistenceUnitMetaData meta)
  {
    meta.addClassName("it.snova.dbschema.table.Domain");
    meta.addClassName("it.snova.dbschema.table.User");
    meta.addClassName("it.snova.dbschema.table.Group");
  }
}
