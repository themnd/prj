package it.snova.web.restapi.services.api.v1;

import it.snova.appframework.membership.data.UserManager;
import it.snova.dbschema.table.User;
import it.snova.web.restapi.sessions.ISessionManager;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.inject.Inject;

public class LoginService
{
  private static final Logger logger = Logger.getLogger(LoginService.class.getName());

  @Inject
  it.snova.appframework.context.Context context;

  @Inject
  ISessionManager sessionManager;

  @Path("/authenticate")
  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.TEXT_PLAIN)
  public Response authenticate(@Context HttpHeaders headers,
      @FormParam("username") final String username,
      @FormParam("password") final String password)
  {
    logger.info("authenticate user " + username);

    try {
      User u = loginUser(username, password);
      if (u == null) {
        return noCache(Response.status(403));
      }
      final String sessionId = sessionManager.createSession(u);
      return noCache(Response.ok(sessionId));
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage(), e);
      return noCache(Response.serverError());
    }
  }

  @Path("/loggedin")
  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.TEXT_PLAIN)
  public Response loggedin(@Context HttpHeaders headers, @FormParam("sid") final String sid)
  {
    logger.info("loggedin sid " + sid);

    try {
      if (sessionManager.getSession(sid) == null) {
        return noCache(Response.status(403));
      }
      return noCache(Response.ok(Boolean.TRUE.toString()));
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage(), e);
      return noCache(Response.serverError());
    }
  }

  private Response noCache(ResponseBuilder builder)
  {
    return builder.cacheControl(createCacheControl()).build();
  }

  private User loginUser(String name, String pwd) throws Exception
  {
    try {
      UserManager userManager = new UserManager(context);

      return userManager.loginUser(name, pwd);
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage(), e);
      throw e;
    }
  }

  private CacheControl createCacheControl()
  {
    CacheControl cacheControl = new CacheControl();
    cacheControl.setNoCache(false);
    cacheControl.setNoStore(false);
    cacheControl.setPrivate(false);
    return cacheControl;
  }

}
