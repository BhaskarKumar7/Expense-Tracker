package com.um.exptracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServerResponseDto {

	private String message;
	private String statusCode;
}
