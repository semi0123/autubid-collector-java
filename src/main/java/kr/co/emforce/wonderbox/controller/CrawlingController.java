package kr.co.emforce.wonderbox.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.emforce.wonderbox.service.CrawlingService;
import kr.co.emforce.wonderbox.util.CurrentTimeUtil;

@Controller
@CrossOrigin(origins="*")
public class CrawlingController {
	
	private static final Logger log = Logger.getLogger(CrawlingController.class);
	
	@Inject
	CrawlingService crawlingService;
	
	@RequestMapping(value="/crawling/")
	@ResponseBody
	public Object forCrawlingModule(
			@RequestParam(value="process_num") Integer process_num,
			@RequestParam(value="bid_status", required=false, defaultValue="Active") String bid_status,
			@RequestParam(value="target", required=false) String target,
			@RequestParam(value="emergency_status", required=false) String emergency_status,
			@RequestParam(value="caller", required=false, defaultValue="module") String caller,
			HttpServletRequest request
		){
		log.info(CurrentTimeUtil.getCurrentTime() + "IP " + request.getRemoteAddr() + " -> GET process_num = " + process_num);
		
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("process_num", process_num);
		inputMap.put("bid_status", bid_status);
		inputMap.put("target", target);
		inputMap.put("caller", caller);
		inputMap.put("emergency_status", emergency_status);
		
		return crawlingService.selectForCrawlingModule(inputMap);
	}
	
	@RequestMapping(value="/crawling/", method=RequestMethod.POST)
	@ResponseBody
	public void post(
			@RequestBody Map<String, Object> requestBody,
			HttpServletRequest request
			){
		String kwd_nm = requestBody.get("kwd_nm").toString();
		String target = requestBody.get("target").toString();
		
		log.info(CurrentTimeUtil.getCurrentTime() + "IP " + request.getRemoteAddr() + " -> POST kwd_nm = "+ kwd_nm + " / target = " + target);
		
		
		try{
			crawlingService.runBidModule(requestBody);
			crawlingService.sendCrawlingPostJsonString(requestBody);
		}catch(Exception e){
			log.error("Crawling Post Error!!!");
			log.error(e.getMessage());
		}
	}
	
	@RequestMapping(value="/crash/", method=RequestMethod.POST)
	@ResponseBody
	public void crash(int process_num){
		crawlingService.crash(process_num);
		log.info(CurrentTimeUtil.getCurrentTime() + "Proces Num " + process_num + " crash");
	}
	
	@RequestMapping(value="/directRelocate/")
	@ResponseBody
	public Object directRelocate(HttpServletRequest request){
		Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
		try{
			log.info(CurrentTimeUtil.getCurrentTime() + "IP " + request.getRemoteAddr() + " -> Direct Relocate Process Num");
			crawlingService.directRelocateProcessNum();
			returnMap.put("status", Boolean.TRUE);
		}catch(Exception e){
			returnMap.put("status", Boolean.FALSE);
			returnMap.put("msg", e.getMessage());
		}
		return returnMap;
	}
	
	
}
