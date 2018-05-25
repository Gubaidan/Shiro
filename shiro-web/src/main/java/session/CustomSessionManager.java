package session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import javax.servlet.ServletRequest;
import java.io.Serializable;

/**
 * 如果用默认的DefaultWebSessionManager ，会多次从Redis中读取数据 造成资源浪费 ，所有重写retrieveSession（）方法
 * retrieveSession该方法为读取Session方法
 * 参数SessionKey 中包含request对象，所有在第一次请求时从Redis中读取，之后都从request读取
 */
public class CustomSessionManager extends DefaultWebSessionManager {
    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        Serializable sessionId = getSessionId(sessionKey);
        ServletRequest request = null;
        if (sessionKey instanceof WebSessionKey){
            request = ((WebSessionKey)sessionKey).getServletRequest();
        }
        if (request != null && sessionId != null){
            Session session =  (Session) request.getAttribute(sessionId.toString());
            if (session != null){
                return session;
            }
        }
        Session session = super.retrieveSession(sessionKey);
        if(request != null && sessionId != null){
            request.setAttribute(sessionId.toString(), session);
        }
        return session;
    }
}
