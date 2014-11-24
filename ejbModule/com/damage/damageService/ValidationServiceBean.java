package com.damage.damageService;

import java.util.Calendar;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.damage.exception.InstanceNotFoundException;
import com.damage.exception.NotValidDamageException;
import com.damage.model.Damage;
import com.damage.model.DamageDaoN;

/**
 * Session Bean implementation class ValidationServiceBean
 */

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ValidationServiceBean implements ValidationService {
	@EJB
	private DamageDaoN damageDao;

	public ValidationServiceBean() {
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public long verifyInitValue(Damage damage) throws NotValidDamageException,
			InstanceNotFoundException {
		long value = damage.getValueIni();
		if (value <= 0)
			throw new NotValidDamageException(damage.getIdDamage());

		return value;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public long verifyInitValue(long damageId) throws NotValidDamageException,
			InstanceNotFoundException {
		long value = damageDao.find(damageId).getValueIni();
		if (value <= 0)
			throw new NotValidDamageException(damageId);

		return value;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean validationNames(long damageId)
			throws NotValidDamageException, InstanceNotFoundException {
		if (damageDao.find(damageId).getClientName().isEmpty())
			throw new NotValidDamageException(damageId);
		return true;

	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean validationNames(Damage damage)
			throws NotValidDamageException, InstanceNotFoundException {
		if (damage.getClientName().isEmpty())
			throw new NotValidDamageException(damage.getIdDamage());
		return true;

	}

	@Override
	public void setNewNames(Damage damage, String newName) throws InstanceNotFoundException {
		damage.setClientName(newName);
		damageDao.update(damage);

	}
	@Override
	public void setNewNames(long damageId, String newName) throws InstanceNotFoundException {
		Damage d = damageDao.find(damageId);
		d.setClientName(newName);
		damageDao.update(d);

	}
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean verifDates(long damageId) throws NotValidDamageException, InstanceNotFoundException {			//SQL-SELECT 
		Calendar currentDate = Calendar.getInstance();
		Damage damage = damageDao.find(damageId);
		if (damage.getDateAct().before(currentDate))
			return true;
		else
			throw new NotValidDamageException(damage.getIdDamage());
	}
	@Override
//	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean verifDates(Damage damage) throws NotValidDamageException, InstanceNotFoundException {			//SQL-SELECT 
		Calendar currentDate = Calendar.getInstance();
		if (damage.getDateAct().before(currentDate))
			return true;
		else
			throw new NotValidDamageException(damage.getIdDamage());
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Damage compareDamageLevel(long damage1Id, long damage2Id) throws InstanceNotFoundException { 
		Damage d1 = damageDao.find(damage1Id);
		Damage d2 = damageDao.find(damage2Id);
		int level1 = d1.getLevelDamage();
		int level2 = d2.getLevelDamage();
		if (level1 >= level2)
			return d1;
		else
			return d2;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Damage compareDamageLevel(Damage damage1, Damage damage2) throws InstanceNotFoundException { 
		int level1 = damage1.getLevelDamage();
		int level2 = damage2.getLevelDamage();
		if (level1 >= level2)
			return damage1;
		else
			return damage2;
	}

	@Override
	public void updateDepositValue(long damageId, long increment)
			throws InstanceNotFoundException {
		Damage damage = damageDao.find(damageId);
		long newDeposit = damage.getDepositIni() + increment;
		damage.setDepositIni(newDeposit);
		damageDao.update(damage);
	}
	
	@Override
	public void updateDepositValue(Damage damage, long increment)
			throws InstanceNotFoundException {
		long newDeposit = damage.getDepositIni() + increment;
		damage.setDepositIni(newDeposit);
		damageDao.update(damage);
	}


	@Override
	public boolean removeRepeatNames(Damage d1, Damage d2)
			throws InstanceNotFoundException {
		if (d1.getClientName().equals(d2.getClientName())) {
			Damage damageDup = damageDao.find(d1.getIdDamage());
			damageDao.remove(damageDup);
			return true;
		}
		return false;
	}

	@Override
	public long createNewDamage(String clientName, Calendar dateAct,
			String description, int level, String location, String nameDamage,
			long valueIni, long depositIni) {

		Damage damage = new Damage(clientName, dateAct, description, level,
				location, nameDamage, valueIni, depositIni);
		long id = damageDao.save(damage).getIdDamage();	
		return id;
	}

	@Override
	public void removeDamage(Damage damage) throws InstanceNotFoundException {
		damageDao.remove(damage);
	}

}