package it.snova.appinstall;

import it.snova.hbaselib.framework.HClient;

public class AppInstaller
{
  static public void install(HClient client) throws Exception
  {
    SchemaInstaller.install(client);
    SequenceInstaller.install(client);
    DefaultDataInstaller.install(client);
  }
}
