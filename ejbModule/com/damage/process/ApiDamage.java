package com.damage.process;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
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

	@Resource(lookup = "java:comp/TransactionSynchronizationRegistry")
	TransactionSynchronizationRegistry tsr;

    
	public ApiDamage() {

	}

	
	@Override
	public void apiDamageValidationService(List<Long> idList,
			String newName, long increment, EntityManager em) throws InterruptedException,
			NotValidDamageException, InstanceNotFoundException {
		
		
	    /*
	      * Note that javax.sql.XADataSource is used instead of a specific
	      * driver implementation such as com.ibm.db2.jcc.DB2XADataSource.
	      */

		for (int i = 1;i<idList.size();i++) {
			Damage damage = damageDao.find(idList.get(i),em);
//			validationService.setNewNames(damage.getIdDamage(), newName,em); // WRITE
//			validationService.updateDepositValue(damage.getIdDamage(), increment,em); // WRITE
			damageDao.flush(em);	
		}
		Thread.sleep(SLEEP_TIME);
		
		for (int i = 1;i<idList.size();i++) {
//		validationService.verifyInitValue(idList.get(i)); // READ
//		validationService.validationNames(idList.get(i)); // READ								
//		validationService.validationNames(idList.get(i)); // READ
//		validationService.verifyInitValue(idList.get(i)); // READ
		}
		damageDao.flush(em);
		
	}


	/*
	 * With this method we can proof read some object while another thread is
	 * executing the main method to be proof
	 */
	@Override
	public void apiDamageReadOperations(List<Long> idList)
			throws NotValidDamageException, InstanceNotFoundException,
			InterruptedException {
		for (int i = 1; i<idList.size(); i++){
//			validationService.verifyInitValue(idList.get(i));
		}
	}

}