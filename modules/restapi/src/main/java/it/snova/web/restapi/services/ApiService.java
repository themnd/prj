package it.snova.web.restapi.services;

import it.snova.web.restapi.services.api.v1.LoginService;

import java.util.logging.Logger;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import com.sun.jersey.api.core.ResourceContext;

@Path("/api")
public class ApiService
{
  private static final Logger logger = Logger.getLogger(ApiService.class.getName());

  @Context
  private ResourceContext resourceContext;

  @Path("/v1/login")
  public LoginService getLoginApi()
  {
    logger.info("getLoginApi");

    return resourceContext.getResource(LoginService.class);
  }
}
