package com.damage.process;

import java.util.List;

import javax.ejb.Remote;
import javax.transaction.SystemException;

import com.damage.exception.InstanceNotFoundException;
import com.damage.exception.NotValidDamageException;
import com.damage.model.Client;
import com.damage.model.Damage;

@Remote
public interface ApiDamageRefactorI {
	long apiDamageValidationService(List<Long> d,
			String newName, long increment) throws InterruptedException,
			NotValidDamageException, InstanceNotFoundException, SystemException;

	void apiDamageValidationUpdates(Damage damage, String newName,
			long increment, Client client) throws InstanceNotFoundException, SystemException, InterruptedException;

}