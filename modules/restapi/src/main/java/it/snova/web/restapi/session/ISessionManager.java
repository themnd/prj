package it.snova.web.restapi.session;

public interface ISessionManager
{
  public String createSession(final Object obj);
  public Object getSession(final String sessionId);
  public boolean removeSession(final String sessionId);
}
