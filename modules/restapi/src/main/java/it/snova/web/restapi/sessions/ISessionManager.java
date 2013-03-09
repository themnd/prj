package it.snova.web.restapi.sessions;

public interface ISessionManager
{
  public String createSession(Object obj);
  public Object getSession(String sessionId);
}
