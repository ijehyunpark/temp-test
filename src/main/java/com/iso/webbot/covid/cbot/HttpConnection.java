package com.iso.webbot.covid.cbot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;

import org.springframework.http.HttpMethod;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpConnection {
	public String sendGet(String targetUrl, Map<String, Object> parameter) throws Exception {
		String param = "";
		StringBuffer sb = new StringBuffer();
		for(String key : parameter.keySet()) {
			if(sb.length() > 0)
				sb.append("&");
			sb.append(key);
			sb.append("=");
			sb.append(URLEncoder.encode((String) parameter.get(key), "UTF-8"));
		}
		if(sb.length() > 0)
			param = sb.toString();
		targetUrl += "?" + param;
		log.info(targetUrl);
		URL url = new URL(targetUrl);
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod(HttpMethod.GET.toString());
		
		int responseCode = conn.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		log.info("Http 응답코드 : " + responseCode);
		log.info("Http body : " + response.toString());
		
		return response.toString();
	}
	
	public void sendPost(String targetUrl, Map<String, Object> fields) throws Exception {
		URL url = new URL(targetUrl);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json; utf-8");
		conn.setRequestProperty("Accept", "application/json");
		conn.setDoOutput(true);
		
		StringBuilder postData = new StringBuilder();
		postData.append("{");
		for(Map.Entry<String, Object> param : fields.entrySet()) {
			if(postData.length() > 1)
				postData.append(", ");
			postData.append("\"");
			postData.append(param.getKey());
			postData.append("\"");
			postData.append(": ");
			postData.append("\"");
			postData.append(String.valueOf(param.getValue()));
			postData.append("\"");
		}
		postData.append("}");
		log.info(postData.toString());
		byte[] postDataBytes = postData.toString().getBytes("UTF-8");
		conn.getOutputStream().write(postDataBytes);
		
		//conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		
		String inputLine;
		while((inputLine = in.readLine()) != null) {
			log.info(inputLine);
		}
		in.close();
	}
}
