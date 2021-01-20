package com.iso.webbot.covid.dto;

import com.iso.webbot.covid.domain.CovidStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CovidStatusSaveDto {
	private String local;
	
	private Integer domesticOccurrence;
	private Integer overseasInflow;
	
	private Integer quarantine;
	private Integer quarantineRelease;
	private Integer dead;
	
	private Double incidence;
	
	public CovidStatus toEntity() {
		return CovidStatus.builder()
							.local(local)
							.domesticOccurrence(domesticOccurrence)
							.overseasInflow(overseasInflow)
							.quarantine(quarantine)
							.quarantineRelease(quarantineRelease)
							.dead(dead)
							.incidence(incidence)
							.build();
	}
}
