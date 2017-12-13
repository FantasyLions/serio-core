package com.serio.core.model;

/**
 * <p>接口返回的字段。</p>
 * @author zl.shi
 *
 */
public abstract class ResultBaseCommon {
	
	private String transactionCode;
	
	private String resultCode;
	
	private String errorMessage;
	

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * 接口返回字段的名字
	 * @author zl.shi
	 * @return
	 */
	public abstract String[] getFieldsSuccessNames();
	
	public abstract  String[] getFieldsErrorNames();
	
	@Override
	public String toString() {
		return "ResultBaseCommon [transactionCode=" + transactionCode + ", resultCode=" + resultCode
				+ ", errorMessage=" + errorMessage + "]";
	}
	
}
