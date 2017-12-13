package com.serio.core.model;

import java.util.List;
import com.serio.core.utils.ReflectionUtils;

/**
 * 接口调用后返回的结果。
 * <p>如果接口有返回结果，会解析到resultCom对象，可用{@link #isSuccess}、{@link #hasException} 两个字段判断是否返回正常</p>
 * @author zl.shi
 *
 */
public class Result<T> {
	
	/**
	 * Givex返回的原始数据，这里面的字段可以自行去文档了解
	 */
	private List<String> originalResult;
	
	/**
	 * Givex 返回的resultCode
	 */
	private String resultCode;
	
	/**
	 * <p>导致错误的原因，一般会放Givex返回的错误原因，也会有本地代码报错信息。如果是Givex返回的错误信息{@link #resultCom}对象中的errorMessage也会有保存信息</p>
	 */
	private String errorMessage;
	
	/**
	 * 判断Givex返回的状态是否为正常，一般Givex result code 返回为0时为true
	 */
	private boolean isSuccess;
	
	/**
	 * 判断调用接口的过程和返回结果解析的过程中是否有异常，如果有会把异常放到{@link #exception}字段中
	 */
	private boolean hasException;
	
	/**
	 * 调用接口的过程和返回结果解析的过程中如果catch到异常都会放到这个里面
	 */
	private Exception exception;
	
	/**
	 * Givex返回结果解析后的对象
	 */
	private T resultCom;
	
	/**
	 * 设置参数属性
	 * @param resultList
	 * @param methodNames
	 * @param t
	 * @param instanceClass
	 */
	public void setResultComonAttr( List<String> resultList, String[] methodNames, T t, Class<?> instanceClass ) {
        try {
        	ReflectionUtils.setObjectAttrs(resultList, methodNames, resultCom, instanceClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	

	public T getResultCom() {
		return resultCom;
	}

	public void setResultCom(T resultCom) {
		this.resultCom = resultCom;
	}

	public List<String> getOriginalResult() {
		return originalResult;
	}

	public void setOriginalResult(List<String> originalResult) {
		this.originalResult = originalResult;
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

	public boolean getIsSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public boolean getHasException() {
		return hasException;
	}

	public void setHasException(boolean hasException) {
		this.hasException = hasException;
	}


	public Exception getException() {
		return exception;
	}


	public void setException(Exception exception) {
		this.exception = exception;
	}


	@Override
	public String toString() {
		return "Result [originalResult=" + originalResult + ", resultCode=" + resultCode + ", errorMessage="
				+ errorMessage + ", isSuccess=" + isSuccess + ", hasException=" + hasException + ", exception="
				+ exception + ", resultCom=" + resultCom + "]";
	}

}
