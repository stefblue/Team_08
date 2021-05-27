package com.swt.amc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swt.amc.api.LectureInformation;

public interface ILectureInformationRepository extends JpaRepository<LectureInformation, Long> {

	LectureInformation findByTag(String tag);

}
