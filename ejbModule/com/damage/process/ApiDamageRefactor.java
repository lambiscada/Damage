package com.damage.process;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.SystemException;
import javax.transaction.TransactionSynchronizationRegistry;

import com.damage.damageService.ValidationService;
import com.damage.exception.InstanceNotFoundException;
import com.damage.exception.NotValidDamageException;
import com.damage.model.Damage;
import com.damage.model.DamageDaoN;

/**
 * Session Bean implementation class ApiDamageRefactor
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ApiDamageRefactor implements ApiDamageRefactorI {
	private final long SLEEP_TIME_READ = 6000;
	private final long SLEEP_TIME = 2000;
	@EJB
	private DamageDaoN damageDao;
	@EJB
	private ValidationService validationService;
	@EJB
	private ApiDamageRefactorI apiDamageRefactor;
	
	@Resource   
	private SessionContext context;
	@Resource(lookup = "java:comp/TransactionSynchronizationRegistry")
	TransactionSynchronizationRegistry tsr;

	public ApiDamageRefactor() {

	}

	@Override
	public long apiDamageValidationService(Damage damage1, Damage damage2,
			String newName, long increment) throws InterruptedException,
			NotValidDamageException, InstanceNotFoundException, SystemException {
		/* READ operations outside of the transaction scope */
		validationService.verifyInitValue(damage1); // READ
		validationService.validationNames(damage1); // READ								
		validationService.verifDates(damage1); //READ
		validationService.compareDamageLevel(damage1, damage1);
		Thread.sleep(SLEEP_TIME_READ);

		damageDao.flush();
		long startTime = System.currentTimeMillis();
		apiDamageRefactor.apiDamageValidationUpdates(damage1.getIdDamage(), newName, increment);
		long stopTime = System.currentTimeMillis();
		long executionTime = (stopTime - startTime);
		return executionTime;
//		return 0;
	}
	

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void apiDamageValidationUpdates(long damage, String newName,
			long increment) throws InstanceNotFoundException, SystemException, InterruptedException {
		/* High Concurrency transaction strategy */
		/* Wrap updates within a transaction */
		try {
		validationService.setNewNames(damage, newName); // WRITE Operation
		validationService.updateDepositValue(damage, increment); // WRITE
		damageDao.flush();
		Thread.sleep(SLEEP_TIME);
		} 	catch (InstanceNotFoundException  e)
			{
				context.setRollbackOnly();
			}
	}
}
		

