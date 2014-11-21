package com.damage.process;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
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

	@Resource(lookup = "java:comp/TransactionSynchronizationRegistry")
	TransactionSynchronizationRegistry tsr;

	public ApiDamageRefactor() {

	}

	@Override
	public long apiDamageValidationService(List<Long> d, String newName,
			long increment) throws InterruptedException,
			NotValidDamageException, InstanceNotFoundException, SystemException {

		for (int i = 1; i < 50; i++) {
			validationService.verifyInitValue(d.get(0)); // READ
			 validationService.validationNames(d.get(0)); // READ
			 validationService.validationNames(d.get(0)); // READ
			 validationService.verifyInitValue(d.get(0)); // READ
//			Thread.sleep(SLEEP_TIME_READ);
		}
		for (int i = 1; i < 50; i++) {
			Damage damage = damageDao.find(d.get(0));
			apiDamageRefactor.apiDamageValidationUpdates(damage.getIdDamage(),newName, increment);
		}
		Thread.sleep(SLEEP_TIME);
		damageDao.flush();

		return 0;
	}

	@Override
	public long apiDamageValidationService(Damage damage1, Damage damage2,
			String newName, long increment) throws InterruptedException,
			NotValidDamageException, InstanceNotFoundException, SystemException {
		/* READ operations outside of the transaction scope */
		validationService.verifyInitValue(damage1); // READ
		validationService.validationNames(damage1); // READ
		validationService.validationNames(damage1); // READ
		validationService.verifyInitValue(damage1); // READ
		Thread.sleep(SLEEP_TIME_READ);

		damageDao.flush();
		long startTime = System.currentTimeMillis();
		apiDamageRefactor.apiDamageValidationUpdates(damage1.getIdDamage(),
				newName, increment);
		long stopTime = System.currentTimeMillis();
		long executionTime = (stopTime - startTime);
		return executionTime;
		// return 0;
	}

	@Override
	public long apiDamageValidationService(long damage, long damage2,
			String newName, long increment) throws InterruptedException,
			NotValidDamageException, InstanceNotFoundException, SystemException {
		/* READ operations outside of the transaction scope */

		validationService.verifyInitValue(damage); // READ
		validationService.validationNames(damage); // READ
		validationService.validationNames(damage); // READ
		validationService.verifyInitValue(damage); // READ
		Thread.sleep(SLEEP_TIME_READ);

		damageDao.flush();
		long startTime = System.currentTimeMillis();
		apiDamageRefactor
				.apiDamageValidationUpdates(damage, newName, increment);
		long stopTime = System.currentTimeMillis();
		long executionTime = (stopTime - startTime);
		return executionTime;
		// return 0;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void apiDamageValidationUpdates(long damage, String newName,
			long increment) throws InstanceNotFoundException, SystemException,
			InterruptedException {
		/* High Concurrency transaction strategy */
		/* Wrap updates within a transaction */
		validationService.setNewNames(damage, newName); // WRITE Operation
		validationService.updateDepositValue(damage, increment); // WRITE
		damageDao.flush();
//		Thread.sleep(SLEEP_TIME);
	}

	/* READ Method */

	/*
	 * With this method we can proof read some object while another thread is
	 * executing the main method to be proof
	 */
	@Override
	public void apiDamageReadOperations(long damage)
			throws NotValidDamageException, InstanceNotFoundException,
			InterruptedException {

		Damage damage1 = damageDao.find(damage);
		validationService.verifyInitValue(damage1);

	}

	/*
	 * With this method we can proof read some object while another thread is
	 * executing the main method to be proof
	 */
	@Override
	public void apiDamageReadOperations(Damage damage1)
			throws NotValidDamageException, InstanceNotFoundException,
			InterruptedException {

		validationService.verifyInitValue(damage1);

	}
}