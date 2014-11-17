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
	public long apiDamageValidationService(Damage damage, Damage damage2,
			String newName, long increment) throws InstanceNotFoundException  {
		UserTransaction txn = context.getUserTransaction();
		long executionTime=0;
		try {
		validationService.verifyInitValue(damage); // READ
		validationService.validationNames(damage); // READ								
		validationService.verifDates(damage); //READ
		validationService.compareDamageLevel(damage, damage);
		Thread.sleep(SLEEP_TIME_READ);
//		long start = System.currentTimeMillis();
		
		txn.begin(); 
		validationService.setNewNames(damage, newName); // WRITE Operation
		validationService.updateDepositValue(damage, increment); // WRITE
		damageDao.flush();
		Thread.sleep(SLEEP_TIME);
		txn.commit(); 	
//		long stopTime = System.currentTimeMillis();
//		executionTime = (stopTime - start);
		} catch(InstanceNotFoundException |  RollbackException | InterruptedException | HeuristicMixedException | SystemException | NotValidDamageException 
				| NotSupportedException |  HeuristicRollbackException e) {
			if (txn != null) {
				try {
					txn.rollback();
				} catch (IllegalStateException | SecurityException
						| SystemException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}
		
//		return executionTime;
		return 0;
		
	}
		
	/*READ Method*/
	/*With this method we can proof read some object  while another thread is executing the main method to be proof*/
	@Override
	public void apiDamageReadOperations(long damageId) throws NotValidDamageException, InstanceNotFoundException, InterruptedException  {
		Damage damage = damageDao.find(damageId);
		validationService.verifyInitValue(damage);
			
	}
}
