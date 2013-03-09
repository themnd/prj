package it.snova.web.restapi.sessions;

import java.util.UUID;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

public class SessionManager implements ISessionManager
{
  private final Ehcache cache;

  public SessionManager()
  {
    CacheConfiguration cacheConfiguration = new CacheConfiguration("sessions", 100)
    .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)
    .eternal(false)
    .timeToLiveSeconds(100)
    .timeToIdleSeconds(0);

    CacheManager mgr = CacheManager.getInstance();
    final net.sf.ehcache.Cache cache = new net.sf.ehcache.Cache(cacheConfiguration);
    mgr.addCache(cache);
    this.cache = cache;
  }

  @Override
  public String createSession(final Object obj)
  {
    final String sessionId = UUID.randomUUID().toString();
    cache.put(new Element(sessionId, obj));
    return sessionId;
  }

  @Override
  public Object getSession(final String sessionId)
  {
    Element element = cache.get(sessionId);
    if (element != null) {
      return element.getObjectValue();
    }
    return null;
  }

}
