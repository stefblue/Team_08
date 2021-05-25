package com.swt.amc.api;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "lecture_information")
public class LectureInformation {

	private Long id;
	private String tag;
	private String title;
	private String number;
	private String semester;
	private Integer ects;
	private List<String> lecturer = new ArrayList<String>();
	private String content;
	private String link;

	public LectureInformation() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	@Column(nullable = false, unique = true)
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Column(nullable = false, unique = true)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(nullable = false, unique = true)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(nullable = false)
	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	@Column(nullable = false)
	public Integer getEcts() {
		return ects;
	}

	public void setEcts(Integer ects) {
		this.ects = ects;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@Column(nullable = false)
	public List<String> getLecturer() {
		return lecturer;
	}

	public void setLecturer(List<String> lecturer) {
		this.lecturer = lecturer;
	}

	@Type(type = "text")
	@Column(nullable = false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonIgnore
	@Column(nullable = false, unique = true)
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
