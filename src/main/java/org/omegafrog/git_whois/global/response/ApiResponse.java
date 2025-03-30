package org.omegafrog.git_whois.global.response;

public class ApiResponse<T> {
	private int code;
	private String message;
	private T data;

	public static <T> ApiResponse<T> success(String message) {
		return new ApiResponse<>(200, message, null);
	}

	public static <T> ApiResponse<T> success(String message, T data) {

		return new ApiResponse<>(200, message, data);
	}

	public static <T> ApiResponse<T> success(int code, String message, T data) {
		if (code < 200 || code > 299) {
			throw new IllegalArgumentException("Invalid response code: " + code);
		}
		return new ApiResponse<>(200, message, data);
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public T getData() {
		return data;
	}

	public ApiResponse(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
}
