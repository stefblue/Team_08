package com.swt.amc.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("amc")
public class AmcConfiguration {

	private List<String> urlWhiteList = new ArrayList<String>();

	public List<String> getUrlWhiteList() {
		return urlWhiteList;
	}

	public void setUrlWhiteList(List<String> urlWhiteList) {
		this.urlWhiteList = urlWhiteList;
	}

}
