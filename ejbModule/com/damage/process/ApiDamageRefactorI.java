package com.damage.process;

import javax.ejb.Remote;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import com.damage.exception.InstanceNotFoundException;
import com.damage.exception.NotValidDamageException;
import com.damage.model.Damage;

@Remote
public interface ApiDamageRefactorI {

	
	long apiDamageValidationService(Damage damage, Damage damage2, String newName,
			long increment) throws InstanceNotFoundException, NotValidDamageException;


	void apiDamageReadOperations(long damage)
			throws NotValidDamageException, InstanceNotFoundException, InterruptedException;

}
