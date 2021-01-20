package com.iso.webbot.covid.cbot;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class HttpConnectionTest {

	@Test
	void sendGetTest() throws Exception {
		HttpConnection hc = new HttpConnection();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("local", "서울");
		params.put("date", "20210112");
		hc.sendGet("http://localhost:8080/api/covid-search",params);
		assertNotNull(hc);
	}
	
	@Test
	void sendPostTest() throws Exception {
		HttpConnection hc = new HttpConnection();
		Map<String, Object> fields = new LinkedHashMap<>();
		fields.put("local", "서울");
		fields.put("domesticOccurrence", "44");
		fields.put("overseasInflow", "4");
		fields.put("quarantine", "444");
		fields.put("quarantineRelease", "44");
		fields.put("dead", "4");
		fields.put("incidence", "4.4");
		hc.sendPost("http://localhost:8080/api/covid-search", fields);
		assertNotNull(hc);
	}

}
