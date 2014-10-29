package com.damage.process;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ApiDamageRefactor implements ApiDamageRefactorI {
	private final long SLEEP_TIME = 400;
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
//		validationService.compareDamageLevel(damage1, damage2); // READ
		validationService.verifyInitValue(damage1); // READ Operation
		validationService.verifyInitValue(damage1); // READ Operation
		validationService.verifyInitValue(damage1); // READ	
		validationService.verifyInitValue(damage1); // READ Operation
		validationService.verifyInitValue(damage1); // READ Operatio
//		System.out.println("Dentro al inicio:  "+tsr.getTransactionStatus());
		Thread.sleep(SLEEP_TIME);
//		
		long startTime = System.currentTimeMillis();
		apiDamageRefactor.apiDamageValidationUpdates(damage2, newName, increment);
		long stopTime = System.currentTimeMillis();
		long executionTime = (stopTime - startTime);
		return executionTime;
//		return 0;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void apiDamageValidationUpdates(Damage damage, String newName,
			long increment) throws InstanceNotFoundException, SystemException {
		/* High Concurrency transaction strategy */
		/* Wrap updates within a transaccion */
//		System.out.println("Dentro al inicio:  "+tsr.getTransactionStatus());
		validationService.setNewNames(damage, newName); // WRITE Operation
		//validationService.updateDepositValue(damage1.getIdDamage(), increment); // WRITE
		damageDao.flush();
	}
	
	/*With this method we can proof read some object  while another thread is executing  the refactor version of the main method*/
	@Override
	public void apiDamageReadOperations(Damage damage) throws NotValidDamageException, InstanceNotFoundException  {
		
		validationService.verifyInitValue(damage);
		validationService.verifyInitValue(damage);
		validationService.verifyInitValue(damage);
			
	}

}
