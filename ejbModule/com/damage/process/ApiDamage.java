package com.damage.process;

import javax.annotation.Resource;
import javax.ejb.EJB;
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
	private final long SLEEP_TIME = 4000;
	@EJB
	private DamageDaoN damageDao;
	@EJB
	private ValidationService validationService;

	@Resource(lookup = "java:comp/TransactionSynchronizationRegistry")
	TransactionSynchronizationRegistry tsr;

	public ApiDamage() {

	}

	@Override
	public void apiDamageValidationService(Damage damage1, Damage damage2,
			String newName, long increment) throws InterruptedException,
			NotValidDamageException, InstanceNotFoundException {
		validationService.setNewNames(damage1, newName); // WRITE Operation	
		validationService.verifyInitValue(damage1); // READ
		damageDao.flush();
		
		newName = "name" + (System.currentTimeMillis()%100000000);
		validationService.setNewNames(damage1, newName); // WRITE Operation	
		validationService.verifyInitValue(damage1); // READ	
		damageDao.flush();
		
		newName = "name" + (System.currentTimeMillis()%100000000);
		validationService.setNewNames(damage1, newName); // WRITE Operation	
		validationService.verifyInitValue(damage1); // READ
		
		Thread.sleep(SLEEP_TIME);
		damageDao.flush();
	}
	
	

	@Override
	public void apiDamageValidationService(long damage, long damage2,
			String newName, long increment) throws InterruptedException,
			NotValidDamageException, InstanceNotFoundException {
		Damage damage1 = damageDao.find(damage);
	
		validationService.setNewNames(damage1, newName); // WRITE Operation	
		newName = "name" + (System.currentTimeMillis()%100000000);
		validationService.verifyInitValue(damage1); // READ
		damageDao.flush();
		
		validationService.setNewNames(damage1, newName); // WRITE Operation	
		newName = "name" + (System.currentTimeMillis()%100000000);
		validationService.verifyInitValue(damage1); // READ
		damageDao.flush();
		
		validationService.setNewNames(damage1, newName); // WRITE Operation	
		newName = "name" + (System.currentTimeMillis()%100000000);
		validationService.verifyInitValue(damage1); // READ
	

		Thread.sleep(SLEEP_TIME);
		damageDao.flush();
	}
	
	/*With this method we can proof read some object  while another thread is executing the main method to be proof*/
	@Override
	public void apiDamageReadOperations(long damage) throws NotValidDamageException, InstanceNotFoundException, InterruptedException  {
		Damage damage1 = damageDao.find(damage);
		validationService.verifyInitValue(damage1);
		validationService.verifyInitValue(damage1);
		validationService.verifyInitValue(damage1);
			
	}
	
	/*With this method we can proof read some object  while another thread is executing the main method to be proof*/
	@Override
	public void apiDamageReadOperations(Damage damage1) throws NotValidDamageException, InstanceNotFoundException, InterruptedException  {

		validationService.verifyInitValue(damage1);
		validationService.verifyInitValue(damage1);
		validationService.verifyInitValue(damage1);
			
	}

}
