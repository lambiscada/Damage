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

	
	long apiDamageValidationService(long damage, long damage2, String newName,
			long increment) throws InterruptedException,
			NotValidDamageException, InstanceNotFoundException, SystemException, NotSupportedException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException;


	void apiDamageReadOperations(long damage)
			throws NotValidDamageException, InstanceNotFoundException, InterruptedException;


}
