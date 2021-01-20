package com.iso.webbot.covid.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListResultContainer<T> extends ResultResponseDto{
	private List<T> data;
	public ListResultContainer(boolean success, int code, String msg) {
		super(success, code, msg);
	}
	public ListResultContainer(boolean success, int code, String msg, List<T> data) {
		super(success, code, msg);
		this.data = data;
	}
}
