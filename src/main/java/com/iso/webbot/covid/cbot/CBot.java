package com.iso.webbot.covid.cbot;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CBot {
	//@Scheduled(cron = "*/10 * * * * *")
	public void cBotSch() throws Exception {
		try {
			log.info("log scheduling");
			//#content > div > div.data_table.midd.mgt24 > table > tbody > tr:nth-child(2)
			Document doc = Jsoup.connect("http://ncov.mohw.go.kr/bdBoardList_Real.do?brdGubun=13").get();
			Elements contents = doc.select("#content")
									.select("div")
									.select("div.data_table.midd.mgt24")
									.select("table")
									.select("tbody")
									.select("tr");
			
			HttpConnection http = new HttpConnection();
			for(Element content : contents) {
				Map<String, Object> field = new LinkedHashMap<>();
				String local = content.select("th").text();
				if(local.compareTo("합계") == 0)
					continue;
				field.put("local", local);
				//국내 발생 : #content > div > div.data_table.midd.mgt24 > table > tbody > tr.sumline > td:nth-child(2)
				field.put("domesticOccurrence", content.select("td:nth-child(2)").text().replaceAll("\\,", ""));
				//해외 유입 : #content > div > div.data_table.midd.mgt24 > table > tbody > tr.sumline > td:nth-child(3)
				field.put("overseasInflow", content.select("td:nth-child(3)").text().replaceAll("\\,", ""));
				//#content > div > div.data_table.midd.mgt24 > table > tbody > tr.sumline > td:nth-child(6)
				field.put("quarantine", content.select("td:nth-child(6)").text().replaceAll("\\,", ""));
				//#content > div > div.data_table.midd.mgt24 > table > tbody > tr.sumline > td:nth-child(7)
				field.put("quarantineRelease", content.select("td:nth-child(7)").text().replaceAll("\\,", ""));
				//#content > div > div.data_table.midd.mgt24 > table > tbody > tr.sumline > td:nth-child(8)
				field.put("dead",content.select("td:nth-child(8)").text().replaceAll("\\,", ""));
				//#content > div > div.data_table.midd.mgt24 > table > tbody > tr.sumline > td:nth-child(9)
				String incidence = content.select("td:nth-child(9)").text();
				if(incidence.compareTo("-") != 0)
					field.put("incidence",incidence);
				http.sendPost("http://localhost:8080/api/covid-search", field);
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
