package com.swt.amc.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("amc")
public class AmcConfiguration {

	private Map<String, String> urlWhiteListMap = new HashMap<String, String>();

	public Map<String, String> getUrlWhiteListMap() {
		return urlWhiteListMap;
	}

	public void setUrlWhiteListMap(Map<String, String> urlWhiteListMap) {
		this.urlWhiteListMap = urlWhiteListMap;
	}

}
