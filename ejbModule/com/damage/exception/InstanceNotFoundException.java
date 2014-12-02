package com.damage.exception;


@SuppressWarnings("serial")
//@ApplicationException(rollback=true)
public class InstanceNotFoundException extends Exception {

	private Object key;
	private String className;

	public InstanceNotFoundException(String specificMessage, Object key,
			String className) {

		super(specificMessage + " (key = '" + key + "' - className = '"
				+ className + "')");
		this.key = key;
		this.className = className;

	}

	public Object getKey() {
		return key;
	}

	public String getClassName() {
		return className;
	}

}