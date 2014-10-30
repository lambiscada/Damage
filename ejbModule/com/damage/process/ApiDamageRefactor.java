package com.damage.process;

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
	private final long SLEEP_TIME = 4000;
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
	public long apiDamageValidationService(Damage damage1, Damage damage2,
			String newName, long increment) throws InterruptedException,
			NotValidDamageException, InstanceNotFoundException, SystemException {
		/* READ operations outside of the transaction scope */
		validationService.verifyInitValue(damage1); // READ Operation
		validationService.verifyInitValue(damage1); // READ Operation
		validationService.verifyInitValue(damage1); // READ	
	
//		System.out.println("Dentro al inicio:  "+tsr.getTransactionStatus());
		Thread.sleep(SLEEP_TIME);
//		
//		long startTime = System.currentTimeMillis();
		apiDamageRefactor.apiDamageValidationUpdates(damage1, newName, increment);
//		long stopTime = System.currentTimeMillis();
//		long executionTime = (stopTime - startTime);
//		return executionTime;
		return 0;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void apiDamageValidationUpdates(Damage damage, String newName,
			long increment) throws InstanceNotFoundException, SystemException, InterruptedException {
		/* High Concurrency transaction strategy */
		/* Wrap updates within a transaccion */
		
//	System.out.println("Dentro al inicio:  "+tsr.getTransactionStatus());
		validationService.setNewNames(damage, newName); // WRITE Operation
		damageDao.flush();
		
		newName = "name" + (System.currentTimeMillis()%100000000);	
		validationService.setNewNames(damage, newName); // WRITE Operation
		damageDao.flush();
		
		newName = "name" + (System.currentTimeMillis()%100000000);
		validationService.setNewNames(damage, newName); // WRITE Operation
		Thread.sleep(4000);
		damageDao.flush();
	}
	
	/*With this method we can proof read some object  while another thread is executing the main method to be proof*/
	@Override
	public void apiDamageReadOperations(long damage) throws NotValidDamageException, InstanceNotFoundException  {

		Damage damage1 = damageDao.find(damage);
		validationService.verifyInitValue(damage1);
		validationService.verifyInitValue(damage1);
		validationService.verifyInitValue(damage1);
			
	}
	
	/*With this method we can proof read some object  while another thread is executing the main method to be proof*/
	@Override
	public void apiDamageReadOperations(Damage damage1) throws NotValidDamageException, InstanceNotFoundException  {

		validationService.verifyInitValue(damage1);
		validationService.verifyInitValue(damage1);
		validationService.verifyInitValue(damage1);
			
	}
}
