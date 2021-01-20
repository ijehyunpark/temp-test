package com.iso.webbot.covid.dto;

import com.iso.webbot.covid.domain.CovidStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CovidStatusResponseDto {
	private String local;
	private Integer totalChangeConfirmedPatients;
	private Integer domesticOccurrence;
	private Integer overseasInflow;
	
	private Integer confirmedPatients;
	private Integer quarantine;
	private Integer quarantineRelease;
	private Integer dead;
	
	private Double incidence;
	
	public CovidStatusResponseDto(CovidStatus covidStatus) {
		this.local = covidStatus.getLocal();
		this.domesticOccurrence = covidStatus.getDomesticOccurrence();
		this.overseasInflow = covidStatus.getOverseasInflow();
		this.totalChangeConfirmedPatients = this.domesticOccurrence + this.overseasInflow;
		
		this.quarantine = covidStatus.getQuarantine();
		this.quarantineRelease = covidStatus.getQuarantineRelease();
		this.dead = covidStatus.getDead();
		this.confirmedPatients = this.quarantine + this.quarantineRelease + this.dead;
		
		this.incidence = covidStatus.getIncidence();
	}
} 
