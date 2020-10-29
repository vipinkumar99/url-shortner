package com.url.shorter.api.pojo;

public class BaseResponse<T> {
	private String message;
	private boolean error;
	private T data;

	public BaseResponse(String message, boolean error, T data) {
		super();
		this.message = message;
		this.error = error;
		this.data = data;
	}

	public BaseResponse(String message, boolean error) {
		super();
		this.message = message;
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
