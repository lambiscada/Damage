package com.damage.damageService;

import java.util.Calendar;

import javax.ejb.Remote;

import com.damage.exception.InstanceNotFoundException;
import com.damage.exception.NotValidDamageException;
import com.damage.model.Damage;

@Remote
public interface ValidationService {

	long verifyInitValue(Damage damage) throws NotValidDamageException,
			InstanceNotFoundException;

	long verifyInitValue(long damageId) throws NotValidDamageException,
			InstanceNotFoundException;

	boolean validationNames(long damageId) throws NotValidDamageException,
			InstanceNotFoundException;

	boolean validationNames(Damage damage) throws NotValidDamageException,
			InstanceNotFoundException;

	void setNewNames(Damage damage, String newName)
			throws InstanceNotFoundException;

	void setNewNames(long damageId, String newName)
			throws InstanceNotFoundException;

	boolean verifDates(long damageId) throws NotValidDamageException,
			InstanceNotFoundException;

	Damage compareDamageLevel(long damage1Id, long damage2Id)
			throws InstanceNotFoundException;

	void updateDepositValue(long damageId, long increment)
			throws InstanceNotFoundException;

	void updateDepositValue(Damage damage, long increment)
			throws InstanceNotFoundException;

	boolean removeRepeatNames(Damage d1, Damage d2)
			throws InstanceNotFoundException;

	long createNewDamage(String clientName, Calendar dateAct,
			String description, int level, String location, String nameDamage,
			long valueIni, long depositIni);

	void removeDamage(Damage damage) throws InstanceNotFoundException;

	boolean verifDates(Damage damage) throws NotValidDamageException,
			InstanceNotFoundException;

	Damage compareDamageLevel(Damage damage1, Damage damage2)
			throws InstanceNotFoundException;

}