package com.iso.webbot.covid.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.iso.webbot.covid.dto.CovidStatusResponseDto;
import com.iso.webbot.covid.dto.CovidStatusSaveDto;
import com.iso.webbot.covid.dto.CovidStatusUpdateDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CovidStatus extends TimeData{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 12)
	private String local;
	
	//확진자 증감 = domesticOccurrence + overseasInflow
	@Column(nullable = false)
	private Integer domesticOccurrence;
	@Column(nullable = false)
	private Integer overseasInflow;
	
	//확진 환자 = quarantine + quarantineRelease + dead
	@Column(nullable = false)
	private Integer quarantine;
	@Column(nullable = false)
	private Integer quarantineRelease;
	@Column(nullable = false)
	private Integer dead;
	@Column(nullable = true)
	private Double incidence;
	
	public void update(CovidStatusUpdateDto covidstatusUpdateDto) {
		this.local = covidstatusUpdateDto.getLocal();
		this.domesticOccurrence = covidstatusUpdateDto.getDomesticOccurrence();
		this.overseasInflow = covidstatusUpdateDto.getOverseasInflow();
		this.quarantine = covidstatusUpdateDto.getQuarantine();
		this.quarantineRelease = covidstatusUpdateDto.getQuarantineRelease();
		this.dead = covidstatusUpdateDto.getDead();
		this.incidence = covidstatusUpdateDto.getIncidence();
	}
}
