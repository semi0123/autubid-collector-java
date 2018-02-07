package kr.co.emforce.wonderbox.util;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestTemplateUtil {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> exchange(String url, HttpMethod method, Map<String, Object> data) throws JsonProcessingException{
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		String body = "{}";
		
		if( method == HttpMethod.GET ){
			StringBuffer urlStrBuffer = new StringBuffer(url + "?");
			Iterator<String> paramNames = data.keySet().iterator();
			String paramName = null;
			
			while( paramNames.hasNext() ){
				paramName = paramNames.next();
				urlStrBuffer.append(paramName + "=" + data.get(paramName) + "&");
			}
			
			url = urlStrBuffer.toString();
		}else{
			body = new ObjectMapper().writeValueAsString(data);
		}
		
		return restTemplate.exchange(url, method, new HttpEntity(body, headers), Map.class).getBody();
		
	}
}
