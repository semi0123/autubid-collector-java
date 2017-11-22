package kr.co.emforce.wonderbox.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.co.emforce.wonderbox.dao.collector.AutoBidDao;
import kr.co.emforce.wonderbox.service.CpaService;

@Service
public class CpaServiceImpl implements CpaService{

	@Inject
	AutoBidDao autoBidDao;
}
