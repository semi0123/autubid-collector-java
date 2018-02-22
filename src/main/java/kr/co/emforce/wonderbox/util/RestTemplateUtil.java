package kr.co.emforce.wonderbox.util;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * @Usage 메소드 오버로딩으로 최종적으로 exchange(String url, HttpMethod method, Map<String, Object> params, String token) 호출
 * 
 * String url : 사용할 URL
 * Ex) anApiDNS + "/bid_favorites/
 * 
 * String method : HttpMethod 클래스의 Enum 사용
 * Ex) HttpMethod.[GET, POST, PUT, DELETE, ...]
 * 
 * String paramsStr : 파라미터 스트링 전달 방식으로, 자동으로 Map형식으로 변경
 * Ex1) param1=one
 * Ex2) param1=one&param2=two
 * 
 * Map<String, Object> params : Map에 넣어 전달하여, Method 파라미터가 GET이면 자동으로 key=value&key=value 형식으로 URL뒤에 붙이고, 타 방식이면 requestBody에 전송
 * 
 * String token : api 로그인 토큰 값(키 값 제외한 token 밸류)
 *
 * @author jyw
 */
public class RestTemplateUtil {
	
	private static final Logger log = Logger.getLogger(RestTemplateUtil.class);
	
	
	public static Map<String, Object> exchange(String url, HttpMethod method, String paramsStr) throws JsonProcessingException{
		return exchange(url, method, paramsStr, null);
	}
	
	public static Map<String, Object> exchange(String url, HttpMethod method, String paramsStr, String token) throws JsonProcessingException{
		Map<String, Object> params = new HashMap<String, Object>();
		
		if( paramsStr != null ){
			if( paramsStr.endsWith("&") ){
				paramsStr = paramsStr.substring(0, paramsStr.length()-1);
			}
			
			String[] paramsArr = paramsStr.split("&");
			String[] temp = null;
			
			if( paramsArr.length == 0 ){
				temp = paramsStr.split("=");
				params.put(temp[0], temp[1]);
			}else{
				for(String data : paramsArr){
					temp = data.split("=");
					params.put(temp[0], temp[1]);
				}
			}
		}
		
		return exchange(url, method, params, token);
	}
	
	public static Map<String, Object> exchange(String url, HttpMethod method, Map<String, Object> params) throws JsonProcessingException{
		return exchange(url, method, params, null);
	}
	
	public static Map<String, Object> exchange(String url, HttpMethod method, Map<String, Object> params, String token) throws JsonProcessingException{
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		String body = "{}";
		
		if( token != null ){
			headers.add("Cookie", "token=" + token);
		}
		
		if( method == HttpMethod.GET ){
			StringBuffer urlStrBuffer = new StringBuffer(url + "?");
			Iterator<String> paramNames = params.keySet().iterator();
			String paramName = null;
			
			while( paramNames.hasNext() ){
				paramName = paramNames.next();
				urlStrBuffer.append(paramName + "=" + params.get(paramName) + "&");
			}
			url = urlStrBuffer.toString();
		}else{
			body = new ObjectMapper().writeValueAsString(params);
		}
		
		log.info("■■■■■■■■■■■■■ Other Service API Call");
		log.info(method + " " + url);
		log.info("Headers : " + headers);
		log.info("RequestBody : " + body);
		
		return restTemplate.exchange(url, method, new HttpEntity(body, headers), Map.class).getBody();
		
	}
}
