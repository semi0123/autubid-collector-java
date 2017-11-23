package kr.co.emforce.wonderbox.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.emforce.wonderbox.service.CpaService;
import kr.co.emforce.wonderbox.util.CurrentTimeUtil;

@Controller
@CrossOrigin(origins="*")
public class CpaController {
	
	private static final Logger log = Logger.getLogger(CrawlingController.class);
	
	@Inject
	CpaService cpaService;
	
	@RequestMapping(value="/cpa/", method=RequestMethod.POST)
	public Object cpa(HttpServletRequest request){
		log.info(CurrentTimeUtil.getCurrentTime() + "IP " + request.getRemoteAddr() + " -> POST /cpa/");
		
		cpaService.runBidModule();
		
		return null;
	}
}
