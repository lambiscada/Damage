package com.damage.process;

import javax.ejb.Remote;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.damage.exception.InstanceNotFoundException;
import com.damage.exception.NotValidDamageException;
import com.damage.model.Damage;

@Remote
public interface ApiDamageI {


	public void apiDamageValidationService(Damage damage1, Damage damage2,
			String newName, long increment) throws InterruptedException, NotValidDamageException;


	void apiDamageReadOperations(long damage) throws NotValidDamageException,
			InstanceNotFoundException, InterruptedException;

}
