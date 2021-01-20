package com.iso.webbot.covid.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResultContainer<T> extends ResultResponseDto {
	private T data;
	
	public SingleResultContainer(boolean success, int code, String msg) {
		super(success, code, msg);
	}
	public SingleResultContainer(boolean success, int code, String msg, T data) {
		super(success, code, msg);
		this.data = data;
	}
}
