package it.snova.web.gui.config.utils;

import it.snova.appframework.context.Context;

public class Configuration
{
  static private Configuration instance = null;
  
  static public Configuration getInstance()
  {
    if (instance == null) {
      instance = new Configuration();
    }
    return instance;
  }
  
  Context context;
  
  public void setContext(Context context)
  {
    this.context = context;
  }
  
  public Context getContext()
  {
    return context;
  }
  
}
