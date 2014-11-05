package com.dahuangit.util.sqlite;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * sqlite异常
 * 
 * @author 黄仁良
 * 
 */
public class SqliteException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private static final String EXCEPTION_CODE = "sqlite_operate_fail";

	private String message;
	private Throwable cause;
	private String exceptionCode;
	private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

	public SqliteException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
		this.cause = cause;
	}

	public SqliteException(String message) {
		super(message);
		this.message = message;
	}

	public SqliteException(String exceptionCode, String... args) {
		this.exceptionCode = exceptionCode;
		MessageFormat format = null;
		String exceptionMessage = resourceBundle.getString(exceptionCode);
		try {
			// ResourceBundle采用ISO-8859-1解码
			format = new MessageFormat(new String(exceptionMessage.getBytes("ISO-8859-1"), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.message = format.format(args);
	}

	public String getExceptionCode() {
		return exceptionCode;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + ": [message=" + message + ", cause=" + cause + "]";
	}

	@Override
	public String getLocalizedMessage() {
		return message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
