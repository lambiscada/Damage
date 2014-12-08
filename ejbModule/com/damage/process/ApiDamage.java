package com.damage.process;

import java.util.List;

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
	private SessionContext context;
	

	@Resource(lookup = "java:comp/TransactionSynchronizationRegistry")
	TransactionSynchronizationRegistry tsr;

	public ApiDamage() {

	}

	@Override
	public void apiDamageValidationService(Damage damage1, Damage damage2,
			String newName, long increment) throws NotValidDamageException, InterruptedException {
		try {
		validationService.setNewNames(damage1.getIdDamage(), newName); // WRITE Operation
		validationService.updateDepositValue(damage1.getIdDamage(), increment); // WRITE
		damageDao.flush();
		Thread.sleep(SLEEP_TIME);

		validationService.verifyInitValue(damage1); // READ
		validationService.validationNames(damage1); // READ	
		validationService.verifDates(damage1); //READ
		validationService.compareDamageLevel(damage1, damage1);
		Thread.sleep(SLEEP_TIME_READ);
		damageDao.flush();
		} catch (InstanceNotFoundException  up) {
			context.setRollbackOnly(); 
		}
	
	}


	/*
	 * With this method we can proof read some object while another thread is
	 * executing the main method to be proof
	 */
	@Override
	public void apiDamageReadOperations(long damage)
			throws NotValidDamageException, InstanceNotFoundException,
			InterruptedException {
		Damage d = damageDao.find(damage);
		validationService.verifyInitValue(d);

	}


}
