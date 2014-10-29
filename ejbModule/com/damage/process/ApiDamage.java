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
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ApiDamage implements ApiDamageI {
	private final long SLEEP_TIME = 60000;
	@EJB
	private DamageDaoN damageDao;
	@EJB
	private ValidationService validationService;

	@Resource(lookup = "java:comp/TransactionSynchronizationRegistry")
	TransactionSynchronizationRegistry tsr;

	public ApiDamage() {

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void apiDamageValidationService(Damage damage1, Damage damage2,
			String newName, long increment) throws InterruptedException,
			NotValidDamageException, InstanceNotFoundException {
//		validationService.verifDates(damage1); // READ Operation
		validationService.setNewNames(damage1, newName); // WRITE Operation	
		newName = "name" + (System.currentTimeMillis()%100000000);
		validationService.verifyInitValue(damage1); // READ
		
		validationService.setNewNames(damage1, newName); // WRITE Operation	
		newName = "name" + (System.currentTimeMillis()%100000000);
		validationService.verifyInitValue(damage1); // READ	
		
		validationService.setNewNames(damage1, newName); // WRITE Operation	
		newName = "name" + (System.currentTimeMillis()%100000000);
		validationService.verifyInitValue(damage1); // READ
		
//		System.out.println("metodo2 soy un READ");
//		validationService.compareDamageLevel(damage1, damage2); // READ

//		System.out.println("metodo3 soy un WRITE");
		//validationService.updateDepositValue(damage1, increment); // WRITE
//		System.out.println("metodo4 soy un READ ");
		

//		System.out.println("metodo5 soy un READ");

		Thread.sleep(SLEEP_TIME);
//		System.out.println("metodo6 soy el temindo FLUSH");
		damageDao.flush();
	}
	
	/*With this method we can proof read some object  while another thread is executing the main method to be proof*/
	@Override
	public void apiDamageReadOperations(long damage) throws NotValidDamageException, InstanceNotFoundException, InterruptedException  {
		String newName = "name" + (System.currentTimeMillis()%100000000);
		//validationService.setNewNames(damage, newName); // WRITE Operation
		Damage damage1 = damageDao.find(damage);
		validationService.verifyInitValue(damage);
		validationService.verifyInitValue(damage);
		validationService.verifyInitValue(damage);
			
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
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
		damageDao.flush();
		
		
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

}
