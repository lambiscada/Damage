package com.damage.process;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.transaction.UserTransaction;

import com.damage.damageService.ValidationService;
import com.damage.exception.InstanceNotFoundException;
import com.damage.exception.NotValidDamageException;
import com.damage.model.Damage;
import com.damage.model.DamageDaoN;

/**
 * Session Bean implementation class ApiDamageRefactor
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
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
	 
	@Resource 
	private EJBContext context;
	
	public ApiDamageRefactor() {

	}
	

	@Override
	public long apiDamageValidationService(long damage, long damage2,
			String newName, long increment) throws InterruptedException,
			NotValidDamageException, InstanceNotFoundException, NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		UserTransaction txn = context.getUserTransaction();

		validationService.verifyInitValue(damage); // READ
		validationService.validationNames(damage); // READ								
		validationService.verifDates(damage); //READ
		validationService.compareDamageLevel(damage, damage);
		Thread.sleep(SLEEP_TIME_READ);
		
		txn.begin(); 
		validationService.setNewNames(damage, newName); // WRITE Operation
		validationService.updateDepositValue(damage, increment); // WRITE
		damageDao.flush();
		Thread.sleep(SLEEP_TIME);
		txn.commit(); 	
		damageDao.flush();
		return 0;
		
	}
	
	
	
	/*READ Method*/
	/*With this method we can proof read some object  while another thread is executing the main method to be proof*/
	@Override
	public void apiDamageReadOperations(long damage) throws NotValidDamageException, InstanceNotFoundException, InterruptedException  {

		Damage damage1 = damageDao.find(damage);
		validationService.verifyInitValue(damage1);
			
	}
}
