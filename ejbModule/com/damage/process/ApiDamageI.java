package com.damage.process;

import java.util.List;

import javax.ejb.Remote;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.damage.exception.InstanceNotFoundException;
import com.damage.exception.NotValidDamageException;
import com.damage.model.Damage;

@Remote
public interface ApiDamageI {


	public void apiDamageValidationService(Damage long1, Damage long2,
			String newName, long increment) throws InterruptedException, NotValidDamageException;


	void apiDamageReadOperations(long damage) throws NotValidDamageException,
			InstanceNotFoundException, InterruptedException;


}
