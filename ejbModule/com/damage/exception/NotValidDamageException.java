package com.damage.exception;

import javax.ejb.ApplicationException;

@SuppressWarnings("serial")
@ApplicationException(rollback=true)
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