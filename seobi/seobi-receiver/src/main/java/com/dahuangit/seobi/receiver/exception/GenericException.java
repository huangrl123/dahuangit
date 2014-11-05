package com.dahuangit.seobi.receiver.exception;

/**
 * 作为所有业务异常类的基类 对于预知可能会发生的异常， 我们采用抛出unchecked类型的异常方式，
 * <p>
 * 在最外层进行捕获集中处理。 这样做的原因是，
 * <p>
 * 我们认为所有的异常都是将导致一个业务逻辑无法正常完成的意外， 如果可以完成，
 * <p>
 * 那将只是一个条件分支，不具备定义异常的前提条件。
 * <p>
 * 可识别的异常均对应了message code，每一个message code都对应了预定义的message。
 * 
 * @author 黄仁良
 * 
 *         创建时间2014年8月21日上午10:06:34
 */
public class GenericException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String message;
	private Throwable cause;

	public GenericException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
		this.cause = cause;
	}

	public GenericException(String message) {
		super(message);
		this.message = message;
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
