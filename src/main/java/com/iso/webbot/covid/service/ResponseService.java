package com.iso.webbot.covid.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.iso.webbot.covid.dto.ListResultContainer;
import com.iso.webbot.covid.dto.ResultResponseDto;
import com.iso.webbot.covid.dto.SingleResultContainer;

@Service
public class ResponseService {
	public <T> SingleResultContainer<T> getSingleResult(T data) {
		return new SingleResultContainer<T>(true, 0, "성공하였습니다.", data);
	}
	
	public <T> ListResultContainer<T> getMultipleResult(List<T> data) {
		return new ListResultContainer<T>(true, 0, "성공하였습니다.", data);
	}
	
	public ResultResponseDto getSuccessResult() {
		ResultResponseDto responseDto = new ResultResponseDto(true, 0, "성공하였습니다.");
		return responseDto;
	}
	
	public ResultResponseDto getFailResult() {
		ResultResponseDto responseDto = new ResultResponseDto(false, -1, "실패하였습니다.");
		return responseDto;
	}
}
