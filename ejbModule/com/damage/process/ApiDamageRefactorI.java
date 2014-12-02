package com.damage.process;

import javax.ejb.Remote;
import javax.transaction.SystemException;
import com.damage.exception.InstanceNotFoundException;
import com.damage.exception.NotValidDamageException;
import com.damage.model.Damage;

@Remote
public interface ApiDamageRefactorI {

	long apiDamageValidationService(long damage, long damage2, String newName,
			long increment) throws InterruptedException,
			NotValidDamageException, InstanceNotFoundException, SystemException;

	void apiDamageValidationUpdates(long damage, String newName, long increment)
			throws InstanceNotFoundException, SystemException,
			InterruptedException;

}