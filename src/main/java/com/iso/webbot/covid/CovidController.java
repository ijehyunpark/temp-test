package com.iso.webbot.covid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iso.webbot.covid.dto.CovidStatusResponseDto;
import com.iso.webbot.covid.dto.CovidStatusSaveDto;
import com.iso.webbot.covid.dto.CovidStatusUpdateDto;
import com.iso.webbot.covid.dto.ResultResponseDto;
import com.iso.webbot.covid.dto.SingleResultContainer;
import com.iso.webbot.covid.service.CovidService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/covid-search")
public class CovidController {
	private final CovidService covidService;
	
	@GetMapping
	public SingleResultContainer<CovidStatusResponseDto> getData(@RequestParam("local") String local,
											@RequestParam("date") String date) {
		return covidService.getData(local, date);
	}
	
	@PostMapping
	public SingleResultContainer<Long> postData(@RequestBody CovidStatusSaveDto covidStatusSaveDto) {
		return covidService.postData(covidStatusSaveDto);
	}
	
	@PutMapping("/{id}")
	public ResultResponseDto changeData(@PathVariable("id") Long id,
							@RequestBody CovidStatusUpdateDto covidStatusUpdateDto) {
		return covidService.changeDate(id, covidStatusUpdateDto);
	}
	
	@DeleteMapping("/{id}")
	public ResultResponseDto deleteData(@PathVariable("id") Long id) {
		return covidService.deleteData(id);
	}
}
