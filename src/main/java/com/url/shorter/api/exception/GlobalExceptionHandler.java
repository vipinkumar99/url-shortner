package com.url.shorter.api.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.url.shorter.api.pojo.BaseResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UrlException.class)
	public BaseResponse<?> handleUrlException(UrlException exception) {
		return new BaseResponse<>(exception.getMessage(), true);
	}

	@ExceptionHandler(Exception.class)
	public BaseResponse<?> handleOtherException(Exception exception) {
		return new BaseResponse<>(exception.getMessage(), true);
	}
}
