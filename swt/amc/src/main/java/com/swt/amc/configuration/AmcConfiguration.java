package com.swt.amc.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.swt.amc.api.LectureInformation;
import com.swt.amc.api.UserInformation;

@Configuration
@ConfigurationProperties("amc")
public class AmcConfiguration {

	private Map<String, LectureInformation> urlWhiteListMap = new HashMap<String, LectureInformation>();
	private Map<String, UserInformation> userNamePasswordMap = new HashMap<String, UserInformation>();

	public Map<String, LectureInformation> getUrlWhiteListMap() {
		return urlWhiteListMap;
	}

	public void setUrlWhiteListMap(Map<String, LectureInformation> urlWhiteListMap) {
		this.urlWhiteListMap = urlWhiteListMap;
	}

	public Map<String, UserInformation> getUserNamePasswordMap() {
		return userNamePasswordMap;
	}

	public void setUserNamePasswordMap(Map<String, UserInformation> userNamePasswordMap) {
		this.userNamePasswordMap = userNamePasswordMap;
	}

}
