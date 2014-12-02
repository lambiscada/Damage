package com.damage.process;

import java.util.ArrayList;
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
	List<Damage> dList = new ArrayList<Damage>();
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
	public long apiDamageValidationService(List<Long> idList, String newName,
			long increment) throws InterruptedException,
			NotValidDamageException, InstanceNotFoundException, SystemException {
		
		for (int i = 1; i < idList.size(); i++) {
//			Damage damage = damageDao.find(idList.get(i));
//			dList.add(damage);
		}
		for (int i = 1; i < dList.size(); i++) {
//			validationService.verifyInitValue(dList.get(i)); // READ
//			 validationService.validationNames(dList.get(i)); // READ
//			 validationService.validationNames(dList.get(i)); // READ
//			 validationService.verifyInitValue(dList.get(i)); // READ
		}
		for (int i = 1; i < dList.size(); i++) {
			apiDamageRefactor.apiDamageValidationUpdates(dList.get(i).getIdDamage(),newName, increment);
		}
		Thread.sleep(SLEEP_TIME);
//		damageDao.flush();

		return 0;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void apiDamageValidationUpdates(long damage, String newName,
			long increment) throws InstanceNotFoundException, SystemException,
			InterruptedException {
		/* High Concurrency transaction strategy */
		/* Wrap updates within a transaction */
//		validationService.setNewNames(damage, newName); // WRITE Operation
//		validationService.updateDepositValue(damage, increment); // WRITE
//		damageDao.flush();
	}

}