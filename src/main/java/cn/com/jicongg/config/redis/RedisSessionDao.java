package cn.com.jicongg.config.redis;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.stereotype.Component;

/**
 * session共享.
 * @author cong.ji
 *
 * Date: 2017-10-13
 */
@Component
public class RedisSessionDao extends AbstractSessionDAO {

  /* (non-Javadoc)
   * @see org.apache.shiro.session.mgt.eis.SessionDAO#update(org.apache.shiro.session.Session)
   */
  @Override
  public void update(Session session) throws UnknownSessionException {
    // TODO Auto-generated method stub
    
  }

  /* (non-Javadoc)
   * @see org.apache.shiro.session.mgt.eis.SessionDAO#delete(org.apache.shiro.session.Session)
   */
  @Override
  public void delete(Session session) {
    // TODO Auto-generated method stub
    
  }

  /* (non-Javadoc)
   * @see org.apache.shiro.session.mgt.eis.SessionDAO#getActiveSessions()
   */
  @Override
  public Collection<Session> getActiveSessions() {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.apache.shiro.session.mgt.eis.AbstractSessionDAO#doCreate(org.apache.shiro.session.Session)
   */
  @Override
  protected Serializable doCreate(Session session) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.apache.shiro.session.mgt.eis.AbstractSessionDAO#doReadSession(java.io.Serializable)
   */
  @Override
  protected Session doReadSession(Serializable sessionId) {
    // TODO Auto-generated method stub
    return null;
  }  

}
