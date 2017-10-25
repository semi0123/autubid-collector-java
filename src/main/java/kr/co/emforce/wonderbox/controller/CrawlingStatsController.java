package kr.co.emforce.wonderbox.controller;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.emforce.wonderbox.service.CrawlingStatsService;
import kr.co.emforce.wonderbox.util.CurrentTimeUtil;

@Controller
@CrossOrigin(origins="*")
public class CrawlingStatsController {
	
	private static final Logger log = Logger.getLogger(CrawlingStatsController.class);
	
	@Inject
	CrawlingStatsService crawlingStatsService;
	
	@RequestMapping(value="/crawling/stats/", method=RequestMethod.POST)
	@ResponseBody
	public void resultPost(
			@RequestBody Map<String, Object> requestBody,
			HttpServletRequest request
			){
		log.info(CurrentTimeUtil.getCurrentTime() + "IP " + request.getRemoteAddr() + " -> RESULT POST server_name = " + requestBody.get("server_name"));
		crawlingStatsService.insertBidCrawlingStats(requestBody);
	}
	
	@RequestMapping(value="/crawling/stats/rate/")
	@ResponseBody
	public Object statsRate(
			HttpServletRequest request
			){
		//log.info(CurrentTimeUtil.getCurrentTime() + "IP " + request.getRemoteAddr() + " -> GET STATS RATE server_name = " + requestBody.get("server_name"));
		return crawlingStatsService.selectBidCrawlingStatsRate();
	}
}
