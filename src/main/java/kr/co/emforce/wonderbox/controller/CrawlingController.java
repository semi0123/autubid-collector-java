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
import kr.co.emforce.wonderbox.util.TimePositionMaker;

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
		log.info("IP " + request.getRemoteAddr() + " -> GET process_num = " + process_num);
		
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("process_num", process_num);
		inputMap.put("bid_status", bid_status);
		inputMap.put("target", target);
		inputMap.put("caller", caller);
		inputMap.put("timePosition", TimePositionMaker.makeTimePosition());
		inputMap.put("emergency_status", emergency_status);
		
		return crawlingService.selectForCrawlingModule(inputMap);
	}
	
	/**
	 * 
	 * @param requestBody
	  {
		"checked_at" : "2018-01-30 10:02:10",
		"emergency_status" : "False",
		"kwd_nm" : "자동차보험",
		"result_rank" : [ {
				"ad_dsc" : "동부화재 새이름 DB손해보험! 교통법규준수 및 최근3년 무사고시 최대13.6%할인",
				"rank" : 1,
				"site" : "www.directdb.co.kr",
				"title" : "DB손해보험 다이렉트 차보험"
			}, {
				"ad_dsc" : "연2천km이하 주행시 특약할인으로 내보험료 42% Down! 자동차보험",
				"rank" : 2,
				"site" : "www.hanwhadirect.com",
				"title" : "한화다이렉트 공식 자동차보험"
			}, {
				"ad_dsc" : "인터넷으로 더 꼼꼼하게 확인하고 가입하는 보험. 3천km이하 주행시 33%특약할인",
				"rank" : 3,
				"site" : "www.meritzdirect.com",
				"title" : "메리츠 다이렉트 자동차보험"
			},
			...
			
		],
		"server_name" : "yw_test",
		"target" : "pc"
	  }
	 * @param request
	 */
	@RequestMapping(value="/crawling/", method=RequestMethod.POST)
	@ResponseBody
	public void post(
			@RequestBody Map<String, Object> requestBody,
			HttpServletRequest request
			){
		String kwd_nm = requestBody.get("kwd_nm").toString();
		String target = requestBody.get("target").toString();
		
		log.info("IP " + request.getRemoteAddr() + " -> POST kwd_nm = "+ kwd_nm + " / target = " + target);
		
		
		try{
			crawlingService.runBidModule(requestBody);
			crawlingService.sendCrawlingPostJsonString(requestBody);
		}catch(Exception e){
			e.printStackTrace();
			log.error("Crawling Post Error!!!");
			log.error(e.getMessage());
		}
	}
	
	@RequestMapping(value="/crash/", method=RequestMethod.POST)
	@ResponseBody
	public void crash(@RequestBody Map<String, String> reqBody){
		crawlingService.crash(reqBody.get("name"));
		log.info("Server >>> " + reqBody.get("name") + " crash");
	}
	
	@RequestMapping(value="/rerun/")
	@ResponseBody
	public Object reRun(
			@RequestParam(value="process_num", required=true) String processNum,
			HttpServletRequest request
		){
		log.info("IP " + request.getRemoteAddr() + " -> Rerun " + processNum + " Machine(s)");
		return "Rerun machine num : " + crawlingService.updateReRun(processNum);
	}
	
	@RequestMapping(value="/directRelocate/")
	@ResponseBody
	public Object directRelocate(HttpServletRequest request){
		Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
		try{
			log.info("IP " + request.getRemoteAddr() + " -> Direct Relocate Process Num");
			crawlingService.directRelocateProcessNum();
			returnMap.put("status", Boolean.TRUE);
		}catch(Exception e){
			returnMap.put("status", Boolean.FALSE);
			returnMap.put("msg", e.getMessage());
		}
		return returnMap;
	}

}
