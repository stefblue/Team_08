package com.swt.amc.api;





import javafx.util.Pair;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class LectureInformation {

	private String title;
	private String number;
	private String semester;
	private Integer ects;
	private String lecturer;
	private String content;
	private String link;
	private LinkedList<Pair<LocalDateTime, Duration>> dates = new LinkedList();

	public LectureInformation() {
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

	public String getLecturer() {
		return lecturer;
	}

	public void setLecturer(String lecturer) {
		this.lecturer = lecturer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public LinkedList<Pair<LocalDateTime, Duration>> getDates() { return dates; }

	public void addDate(Pair<LocalDateTime, Duration> date) { dates.add(date); }

}
