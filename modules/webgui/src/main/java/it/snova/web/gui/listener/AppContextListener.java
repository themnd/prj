package it.snova.web.gui.listener;

import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener
{
  protected static final Logger logger = Logger.getLogger(AppContextListener.class.getName());
  
  @Override
  public void contextInitialized(ServletContextEvent event)
  {
//    Injector injector = Guice.createInjector(new RSSModule());
//    Injector child = injector.createChildInjector(new DummyModule());
//    WireApp.init(child);
    logger.info("contextInitialized");
  }

  @Override
  public void contextDestroyed(ServletContextEvent event)
  {
    logger.info("contextDestroyed");
  }

}