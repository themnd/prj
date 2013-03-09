package it.snova.web.restapi.servlet.listener;

import it.snova.web.restapi.services.ApiService;
import it.snova.web.restapi.services.api.v1.LoginService;
import it.snova.web.restapi.servlet.config.ApiModule;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class ContextListener extends GuiceServletContextListener
{
  private static final Logger logger = Logger.getLogger(ContextListener.class.getName());

  private ApiModule module;

  @Override
  public void contextInitialized(ServletContextEvent event)
  {
    logger.info("contextInitialized");

    module = new ApiModule();
    super.contextInitialized(event);
  }

  @Override
  protected Injector getInjector()
  {
    logger.log(Level.INFO, "Invoking ServletContextListener.");
    return Guice.createInjector(module, new ServletModule() {
      @Override
      protected void configureServlets()
      {
        bind(ApiService.class);
        bind(LoginService.class);
        serve("/*").with(GuiceContainer.class);
      }
    });
  }

}
