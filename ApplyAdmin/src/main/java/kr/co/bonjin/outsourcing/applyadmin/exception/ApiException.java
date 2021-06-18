package kr.co.bonjin.outsourcing.applyadmin.exception;

public class ApiException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private String code;
	private String message;
	
	public ApiException(ApiError apiError, String message) {
		this.code = apiError.getCode();
		this.message = apiError.getMessage() + message;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getMessage() {
		return this.message;
	}
}
