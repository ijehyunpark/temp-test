package com.iso.webbot.covid.dto;

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
public class CovidStatusUpdateDto {
	private String local;
	
	private Integer domesticOccurrence;
	private Integer overseasInflow;
	
	private Integer quarantine;
	private Integer quarantineRelease;
	private Integer dead;
	
	private Double incidence;
}
