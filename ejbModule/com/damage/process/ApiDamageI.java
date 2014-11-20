package com.damage.process;

import javax.ejb.Remote;

import com.damage.exception.InstanceNotFoundException;
import com.damage.exception.NotValidDamageException;
import com.damage.model.Damage;

@Remote
public interface ApiDamageI {

	void apiDamageReadOperations(Damage damage) throws NotValidDamageException,
			InstanceNotFoundException, InterruptedException;

	void apiDamageValidationService(long damage, long damage2, String newName,
			long increment) throws InterruptedException,
			NotValidDamageException, InstanceNotFoundException;


}
