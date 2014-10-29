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
			long increment) throws InstanceNotFoundException, SystemException;

	void apiDamageReadOperations(Damage damage)
			throws NotValidDamageException, InstanceNotFoundException;


}
