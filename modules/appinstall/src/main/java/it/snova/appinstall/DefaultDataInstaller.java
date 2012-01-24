package it.snova.appinstall;

import it.snova.appframework.security.PasswordEncrypter;
import it.snova.appframework.security.PasswordEntity;
import it.snova.apptables.data.Metadata;
import it.snova.apptables.data.UsersTable;
import it.snova.apptables.framework.Context;
import it.snova.hbaselib.framework.DefaultValues;
import it.snova.hbaselib.framework.HClient;
import it.snova.hbaselib.schema.TableBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DefaultDataInstaller
{
  HClient client;
  List<Object> objects;
  
  public DefaultDataInstaller(HClient client)
  {
    this.client = client;
    objects = new ArrayList<Object>();
  }
  
  public DefaultDataInstaller addObject(Object o) throws IOException
  {
    objects.add(o);
    return this;
  }
  
  public DefaultDataInstaller createObjects() throws Exception
  {
    for (Object o: objects) {
      createObject(o);
    }
    return this;
  }
  
  private void createObject(Object o) throws Exception
  {
    TableBuilder tb = client.getSchemaBuilder(o.getClass()).getTableBuilder();
    if (o instanceof Metadata) {
      tb.add(new Context().with((Metadata)o));
    } else {
      tb.add(o);
    }
  }

  static public DefaultDataInstaller install(HClient client) throws Exception
  {
    PasswordEncrypter e = new PasswordEncrypter();
    PasswordEntity p = e.encrypt(new String("admin").toCharArray());

    UsersTable admin = new UsersTable();
    admin.setId(DefaultValues.ADMINID);
    admin.setDomainId("");
    admin.setName("admin");
    admin.setPwd(p.getPwd());
    admin.setSalt(p.getSalt());
    admin.setEmail("admin@admin.it");
    
    return new DefaultDataInstaller(client)
      .addObject(admin)
      .createObjects();
  }

}
