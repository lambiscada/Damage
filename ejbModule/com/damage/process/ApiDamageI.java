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
	


	void apiDamageValidationService(Damage damage1, Damage damage2,
			String newName, long increment) throws InterruptedException,
			NotValidDamageException, InstanceNotFoundException;
	

	void apiDamageValidationService(long damage1, long damage2,
			String newName, long increment) throws InterruptedException,
			NotValidDamageException, InstanceNotFoundException;

	void apiDamageReadOperations(long damage) throws NotValidDamageException,
			InstanceNotFoundException, InterruptedException;


	void apiDamageReadOperations(List<Long> d)
			throws NotValidDamageException, InstanceNotFoundException,
			InterruptedException;

}
