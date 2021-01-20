package com.iso.webbot.covid.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.iso.webbot.covid.domain.CovidStatus;
import com.iso.webbot.covid.domain.CovidStatusRepository;
import com.iso.webbot.covid.dto.CovidStatusResponseDto;
import com.iso.webbot.covid.dto.CovidStatusSaveDto;
import com.iso.webbot.covid.dto.CovidStatusUpdateDto;
import com.iso.webbot.covid.dto.ResultResponseDto;
import com.iso.webbot.covid.dto.SingleResultContainer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CovidService {
	private static Integer[] DATE = {31,29,31,30,31,30,31,31,30,31,30,31};
	private final CovidStatusRepository covidStatusRepository;
	private final ResponseService responseService;
	
	public SingleResultContainer<CovidStatusResponseDto> getData(String local, String date) {
		if(date.length() != 8)
			throw new IllegalArgumentException();
		int year = Integer.valueOf(date.substring(0, 4));
		int month = Integer.valueOf(date.substring(4, 6));
		int dayOfMonth = Integer.valueOf(date.substring(6));
		log.info(year + ":" + month + " : " + dayOfMonth);
		if(dayOfMonth < 0 || dayOfMonth > DATE[month - 1] || month == 2 && dayOfMonth == 29 && (year % 4 != 0 || (year % 100 == 0 && year % 400 != 0)))
			throw new IllegalArgumentException();
		LocalDateTime fromDate = LocalDateTime.of(year, month, dayOfMonth, 0, 0, 0, 0);
		LocalDateTime toDate = LocalDateTime.of(year, month, dayOfMonth, 23, 59, 59, 999999999);
		log.error(fromDate.toString());
		log.info(toDate.toString());
		CovidStatus entity = covidStatusRepository.findByLocalAndCreatedDateBetween(local, fromDate, toDate).orElseThrow(IllegalArgumentException::new);
		return responseService.getSingleResult(new CovidStatusResponseDto(entity));
	}
	
	public SingleResultContainer<Long> postData(CovidStatusSaveDto covidStatusSaveDto) {
		CovidStatus entity = covidStatusSaveDto.toEntity();
		return responseService.getSingleResult(covidStatusRepository.save(entity).getId());
	}
	
	public ResultResponseDto changeDate(Long id, CovidStatusUpdateDto covidStatusUpdateDto) {
		CovidStatus entity = covidStatusRepository.findById(id).orElseThrow(IllegalArgumentException::new);
		entity.update(covidStatusUpdateDto);
		return responseService.getSuccessResult();
	}
	
	public ResultResponseDto deleteData(Long id) {
		CovidStatus entity = covidStatusRepository.findById(id).orElseThrow(IllegalArgumentException::new);
		covidStatusRepository.delete(entity);
		return responseService.getSuccessResult();
	}
}
