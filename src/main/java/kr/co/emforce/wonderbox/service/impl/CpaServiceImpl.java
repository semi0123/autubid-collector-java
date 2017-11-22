package kr.co.emforce.wonderbox.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.co.emforce.wonderbox.dao.collector.AutoBidDao;
import kr.co.emforce.wonderbox.service.CpaService;

@Service
public class CpaServiceImpl implements CpaService{

	@Inject
	AutoBidDao autoBidDao;

	@Override
	public void runBidModule() {
		Map<String, Object> inputMap = new HashMap<String, Object>();
		
		
		
	}
}
