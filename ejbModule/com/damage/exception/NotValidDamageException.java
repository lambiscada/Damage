package com.damage.exception;


@SuppressWarnings("serial")
public class NotValidDamageException extends Exception {

	private long damageId;

	public NotValidDamageException(long damageId) {
		super("Not valid=> damageId = " + damageId);
		this.damageId = damageId;
	}

	public long getDamageId() {
		return damageId;
	}
}