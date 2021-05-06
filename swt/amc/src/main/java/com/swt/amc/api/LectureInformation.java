package com.swt.amc.api;

public class LectureInformation {

	private String title;
	private String number;
	private String semester;
	private Integer ects;
	private String Lecturer;
	private String Content;

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
		return Lecturer;
	}

	public void setLecturer(String lecturer) {
		Lecturer = lecturer;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

}
