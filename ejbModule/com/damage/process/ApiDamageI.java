package com.damage.process;

import java.util.List;

import javax.ejb.Remote;

import com.damage.exception.InstanceNotFoundException;
import com.damage.exception.NotValidDamageException;
import com.damage.model.Damage;

@Remote
public interface ApiDamageI {
	

	void apiDamageValidationService(List<Long> d,
			String newName, long increment) throws InterruptedException,
			NotValidDamageException, InstanceNotFoundException;


	void apiDamageReadOperations(List<Long> d)
			throws NotValidDamageException, InstanceNotFoundException,
			InterruptedException;

}
