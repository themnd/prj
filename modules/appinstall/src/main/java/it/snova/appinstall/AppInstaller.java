package it.snova.appinstall;

import it.snova.appframework.context.Context;

public class AppInstaller
{
  static public void install(Context context) throws Exception
  {
//    SchemaInstaller.install(context);
//    SequenceInstaller.install(context);
    DefaultDataInstaller.install(context);
  }
}
