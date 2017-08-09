package kr.co.emforce.wonderbox.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class CollectorTestInterceptor extends HandlerInterceptorAdapter  {
	
	private static final Logger log = LoggerFactory.getLogger(CollectorTestInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean bool = true;
		
		if( bool ){
			log.info("Interceptor True");
			return true; //통과
		}else{
			log.info("Interceptor False");
			return false;
		}
	}
	

}
