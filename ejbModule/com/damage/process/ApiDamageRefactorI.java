package com.damage.process;

import javax.ejb.Remote;
import javax.transaction.SystemException;

import com.damage.exception.InstanceNotFoundException;
import com.damage.exception.NotValidDamageException;
import com.damage.model.Damage;

@Remote
public interface ApiDamageRefactorI {

	long apiDamageValidationService(Damage damage1, Damage damage2,
			String newName, long increment) throws InterruptedException,
			NotValidDamageException, InstanceNotFoundException, SystemException;

	void apiDamageValidationUpdates(Damage damage1, String newName,
			long increment) throws InstanceNotFoundException, SystemException, InterruptedException;

	void apiDamageReadOperations(long damage)
			throws NotValidDamageException, InstanceNotFoundException;

	void apiDamageReadOperations(Damage damage1)
			throws NotValidDamageException, InstanceNotFoundException;


}
