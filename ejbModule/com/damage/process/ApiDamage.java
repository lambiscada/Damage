package com.damage.process;

import java.util.Calendar;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.TransactionSynchronizationRegistry;

import com.damage.damageService.ValidationService;
import com.damage.exception.InstanceNotFoundException;
import com.damage.exception.NotValidDamageException;
import com.damage.model.Damage;
import com.damage.model.DamageDaoN;

/**
 * Session Bean implementation class ApiDamage
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ApiDamage implements ApiDamageI {
	private final long SLEEP_TIME_READ = 6000;
	private final long SLEEP_TIME = 2000;
	@EJB
	private DamageDaoN damageDao;
	@EJB
	private ValidationService validationService;

	@Resource
	SessionContext context;
	@Resource(lookup = "java:comp/TransactionSynchronizationRegistry")
	TransactionSynchronizationRegistry tsr;

	public ApiDamage() {

	}


	@Override
	public void apiDamageValidationService(long d, long damage2,
			String newName, long increment) throws InterruptedException,
			NotValidDamageException {
		try {
		Damage damage = damageDao.find(d);
		validationService.setNewNames(damage.getIdDamage(), newName); // WRITE Operation
		validationService.updateDepositValue(damage.getIdDamage(), increment); // WRITE
		damageDao.flush();
		Thread.sleep(SLEEP_TIME);

		validationService.verifyInitValue(damage.getIdDamage()); // READ
		validationService.validationNames(damage.getIdDamage()); // READ								
		validationService.verifDates(damage.getIdDamage()); //READ
		validationService.compareDamageLevel(damage.getIdDamage(), damage.getIdDamage());
		Thread.sleep(SLEEP_TIME_READ);
		
		damageDao.flush();
		} catch (InstanceNotFoundException e)
		{
			context.setRollbackOnly();
		}
	
		
	}
	

	/*
	 * With this method we can proof read some object while another thread is
	 * executing the main method to be proof
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void apiDamageReadOperations(Damage damage)
			throws NotValidDamageException, InstanceNotFoundException,
			InterruptedException {
		validationService.verifDates(damage);
	}

	/*
	 * With this method we can proof read some object while another thread is
	 * executing the main method to be proof
	 */

}
