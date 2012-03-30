package it.snova.web.gui.listener;

import it.snova.appframework.context.ConnectorOptions;
import it.snova.appframework.context.Context;
import it.snova.web.gui.config.utils.Configuration;

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
    
    Configuration config = Configuration.getInstance();
    ConnectorOptions options = new ConnectorOptions()
      .dbname("app")
      .username("root")
      .password("root");
    
    logger.info("options:" + options.url());

    config.setContext(new Context().setOptions(options).init());
    
    logger.info("contextInitialized");
  }

  @Override
  public void contextDestroyed(ServletContextEvent event)
  {
    logger.info("contextDestroyed");
  }

}