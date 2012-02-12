package it.snova.web.gui.config.utils;

import javax.servlet.FilterConfig;


public abstract class ConfigParser
{
  static public IConfigParser getParser(FilterConfig config)
  {
    return new FilterConfigParser(config);
  }

}
