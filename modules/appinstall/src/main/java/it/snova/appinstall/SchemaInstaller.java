package it.snova.appinstall;

import it.snova.apptables.data.DomainTable;
import it.snova.apptables.data.GroupsTable;
import it.snova.apptables.data.UsersTable;
import it.snova.hbaselib.framework.HClient;
import it.snova.hbaselib.schema.SchemaBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SchemaInstaller
{
  HClient client;
  List<Class> classes;
  
  public SchemaInstaller(HClient client)
  {
    this.client = client;
    classes = new ArrayList<Class>();
  }
  
  public SchemaInstaller addTable(Class c)
  {
    classes.add(c);
    return this;
  }
  
  public SchemaInstaller recreateTables() throws IOException
  {
    for (Class c: classes) {
      recreateTable(client.getSchemaBuilder(c));
    }
    return this;
  }
  
  private void recreateTable(SchemaBuilder table) throws IOException
  {
    if (table.tableExists()) {
      table.disableTable();
      table.deleteTable();
    }
    
    table.createTable();    
  }
  
  static public SchemaInstaller install(HClient client) throws IOException
  {
    return new SchemaInstaller(client)
      .addTable(DomainTable.class)
      .addTable(UsersTable.class)
      .addTable(GroupsTable.class)
      .recreateTables();
  }

}
