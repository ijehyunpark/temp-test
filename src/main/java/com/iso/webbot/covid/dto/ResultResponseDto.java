package com.iso.webbot.covid.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ResultResponseDto {
	private boolean success;
	private int code;
	private String msg;
}
