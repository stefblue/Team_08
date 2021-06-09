package com.swt.augmentmycampus.api.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LectureInformation {

	private String tag;
	private String title;
	private String number;
	private String semester;
	private Integer ects;
	private List<String> lecturer;
	private String content;
	private String link;
	private Object dates;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public Integer getEcts() {
		return ects;
	}

	public void setEcts(Integer ects) {
		this.ects = ects;
	}

	public List<String> getLecturer() {
		return lecturer;
	}

	public void setLecturer(List<String> lecturer) {
		this.lecturer = lecturer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Object getDates() {
		return dates;
	}

	public void setDates(Object dates) {
		this.dates = dates;
	}
}
