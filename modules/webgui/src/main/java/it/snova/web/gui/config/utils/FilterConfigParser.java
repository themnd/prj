package it.snova.web.gui.config.utils;

import javax.servlet.FilterConfig;

public class FilterConfigParser implements IConfigParser
{
  FilterConfig config;
  
  public FilterConfigParser(FilterConfig config)
  {
    this.config = config;
  }

  @Override
  public String getInitParameter(String name)
  {
    return config.getInitParameter(name);
  }

}
