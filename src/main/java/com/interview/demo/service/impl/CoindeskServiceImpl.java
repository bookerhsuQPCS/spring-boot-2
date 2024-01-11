package com.interview.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.demo.dto.QueryCoindeskRequest;
import com.interview.demo.dto.QueryCoindeskResponse;
import com.interview.demo.service.CoindeskService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class CoindeskServiceImpl implements CoindeskService {
	
	@Autowired
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();
    
    private final static String COINDESK_SERVIC_URL = "https://api.coindesk.com/v1/bpi/currentprice.json";

	@Override
	public QueryCoindeskResponse query(QueryCoindeskRequest request) {
		return this.callAPIByGet(COINDESK_SERVIC_URL);
	}
	
    private QueryCoindeskResponse callAPIByGet(String url) {

        ResponseEntity<QueryCoindeskResponse> responseEntity = restTemplate.getForEntity(url, QueryCoindeskResponse.class);

        try {
            log.info("[] 外部 API 呼叫請求 : {}", url);
            log.info("[] 外部 API 呼叫回應狀態碼 : {}", responseEntity.getStatusCode());
            log.info("[] 外部 API 呼叫回應結果 : {}", objectMapper.writeValueAsString(responseEntity));
        }catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return responseEntity.getBody();
    }
 
}
