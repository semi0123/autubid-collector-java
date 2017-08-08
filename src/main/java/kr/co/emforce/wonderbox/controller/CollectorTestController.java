package kr.co.emforce.wonderbox.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.emforce.wonderbox.model.CollectorTest;
import kr.co.emforce.wonderbox.service.CollectorTestService;
import kr.co.emforce.wonderbox.util.JsonToClassConverter;

@Controller
@CrossOrigin(origins="*")
@RequestMapping("/test")
public class CollectorTestController {
	
	@Inject
	CollectorTestService ctService;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	@ResponseBody
	public Object get(
			@RequestParam(value="name", required=false) String name){
		Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
		Map<String, Object> inputMap = new HashMap<String, Object>();
		
		inputMap.put("name", name);
		returnMap.put("collectors", ctService.select(inputMap));
		returnMap.put("status", Boolean.TRUE);
		
		return returnMap;
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST, headers = {"content-type=application/json"})
	@ResponseBody
	public Object post(
			@RequestBody Map<String, Object> requestBody){
		Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
		List<Map<String, Object>> ctList = (ArrayList<Map<String, Object>>) requestBody.get("collectors");
		try{
			JsonToClassConverter.convert(ctList, CollectorTest.class);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			returnMap.put("status", Boolean.TRUE);
//			returnMap.put("count_by_inserted", ctService.insert(ctList));
		}catch(Exception e){
			e.printStackTrace();
			returnMap.put("status", Boolean.FALSE);
			returnMap.put("msg", e.getMessage());
		}
		return returnMap;
	}
	
	
}
