package first.common.logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoggerInterceptor extends HandlerInterceptorAdapter{
	//protected Log log = LogFactory.getLog(LoggerInterceptor.class);
	protected Logger log = LoggerFactory.getLogger(LoggerInterceptor.class);

	//컨트롤러가 호출되기 전에 실행.
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("=========STRAT=========");
			log.debug("Request URI \t: " + request.getRequestURL());
		}
		return super.preHandle(request, response, handler);
	}
	
	//컨트롤러가 실행되고 난 후에 호출
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("=========END=========\n");
		}
		super.postHandle(request, response, handler, modelAndView);
	}
	
}
