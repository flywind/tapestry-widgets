package org.flywind.widgets.test.common.result;

import org.flywind.widgets.test.common.utils.JSONSerializer;

/**
 * JSON模型.
 * 用户后台向前台返回的JSON对象
 */
@SuppressWarnings("serial")
public class Json implements java.io.Serializable {

	/**
	 * 操作结果 1.成功 0.失败
	 */
	private int success = 0;
	
	private String errorCode = null;
	
	private String msg = "操作失败";
	
	private Object obj = null;

	public int isSuccess() {
		return success;
	}
	
	public int getSuccess() {
		return success;
	}
	
	public void setSuccess(int success) {
		this.success = success;
	}

	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = JSONSerializer.serialize(obj);
	}

	@Override
	public String toString(){
		return obj.toString();
	}
	
}
