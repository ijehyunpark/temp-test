package com.iso.webbot.covid.domain;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CovidStatusRepository extends JpaRepository<CovidStatus, Long>{
	Optional<CovidStatus> findByLocalAndCreatedDateBetween(String local, LocalDateTime fromDate, LocalDateTime toDate);
	
}
