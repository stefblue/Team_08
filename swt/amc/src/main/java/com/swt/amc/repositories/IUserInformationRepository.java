package com.swt.amc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swt.amc.api.UserInformation;

public interface IUserInformationRepository extends JpaRepository<UserInformation, Long> {

	UserInformation findByUserName(String userName);

}
