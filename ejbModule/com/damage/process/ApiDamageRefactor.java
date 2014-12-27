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
import com.damage.model.Client;
import com.damage.model.ClientDaoN;
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
	@EJB
	private ClientDaoN clientDao;

	@Resource(lookup = "java:comp/TransactionSynchronizationRegistry")
	TransactionSynchronizationRegistry tsr;

	public ApiDamageRefactor() {

	}

	@Override
	public long apiDamageValidationService(List<Long> idList, String newName,
			long increment) throws InterruptedException,
			NotValidDamageException, InstanceNotFoundException, SystemException {

		/*
		 * Note that javax.sql.XADataSource is used instead of a specific driver
		 * implementation such as com.ibm.db2.jcc.DB2XADataSource.
		 */

		Damage damage = damageDao.find(idList.get(0));
		Client client = clientDao.find(idList.get(0));

		validationService.verifyInitValue(damage); // READ
		validationService.validationNames(damage); // READ
		validationService.validationNames(damage); // READ
		validationService.verifyInitValue(damage); // READ
		Thread.sleep(SLEEP_TIME_READ);
		long startTime = System.currentTimeMillis();
		apiDamageRefactor.apiDamageValidationUpdates(damage, newName, increment, client);
		long stopTime = System.currentTimeMillis();
		long executionTime = (stopTime - startTime);
		return executionTime;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void apiDamageValidationUpdates(Damage damage, String newName,
			long increment, Client client) throws InstanceNotFoundException, SystemException,
			InterruptedException {
		/* High Concurrency transaction strategy */
		/* Wrap updates within a transaction */
		validationService.setNewNames(damage, newName); // WRITE
		validationService.updateDepositValue(damage.getIdDamage(), increment);
		client.setNameClient("maria"+ (System.currentTimeMillis() % 100000000));
		clientDao.update(client);
//		Client client1 = clientDao.find(7);
//		String clientname = client1.getNameClient();
//		System.out.println(clientname);
		clientDao.flush();
		damageDao.flush();
		Thread.sleep(SLEEP_TIME);
	}

}