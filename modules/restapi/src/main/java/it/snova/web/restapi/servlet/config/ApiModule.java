package it.snova.web.restapi.servlet.config;

import it.snova.appframework.context.ConnectorOptions;
import it.snova.appframework.context.Context;
import it.snova.web.restapi.session.ISessionManager;
import it.snova.web.restapi.session.SessionManager;

import java.util.logging.Logger;

import com.google.inject.AbstractModule;

public class ApiModule extends AbstractModule
{
  private static final Logger logger = Logger.getLogger(ApiModule.class.getName());

  @Override
  protected void configure()
  {
    //    bindConstant().annotatedWith(Names.named("dbname")).to("app");
    //    bindConstant().annotatedWith(Names.named("dbuser")).to("root");
    //    bindConstant().annotatedWith(Names.named("dbpwd")).to("root");

    ConnectorOptions options = new ConnectorOptions()
    .dbname("app")
    .username("root")
    .password("root");

    logger.info("options:" + options.url());

    bind(Context.class).toInstance(new Context().setOptions(options).init());
    bind(ISessionManager.class).toInstance(new SessionManager());

    //    bind(Repository.class).to(FileSystemRepository.class);
    //
    //    // How do we load image directory path in a good way?
    //    if (imageRepositoryPath == null) {
    //      updateImageRepositoryPath();
    //    }
    //    bindConstant().annotatedWith(Names.named("ImageDirectory")).to(imageRepositoryPath);
    //
    //    bind(NamingScheme.class).to(UnbalancedTreeNamingScheme.class);
    //
    //    bind(FileCounter.class).to(FileSystemFileCounter.class);
    //
    //    bind(MimeDetector.class).to(DataMimeDetector.class);
  }

}
