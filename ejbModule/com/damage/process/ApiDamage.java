package com.damage.process;

import java.util.List;

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
import com.damage.model.Client;
import com.damage.model.ClientDaoN;
import com.damage.model.Damage;
import com.damage.model.DamageDaoN;

/**
 * Session Bean implementation class ApiDamage
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ApiDamage implements ApiDamageI {
	private final long SLEEP_TIME_READ = 6000;
	private final long SLEEP_TIME = 2000;
	@EJB
	private DamageDaoN damageDao;
	@EJB
	private ClientDaoN clientDao;
	@EJB
	private ValidationService validationService;

	@Resource(lookup = "java:comp/TransactionSynchronizationRegistry")
	TransactionSynchronizationRegistry tsr;

	public ApiDamage() {

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void apiDamageValidationService(List<Long> idList, String newName,
			long increment) throws InterruptedException,
			NotValidDamageException, InstanceNotFoundException {

		/*
		 * Note that javax.sql.XADataSource is used instead of a specific driver
		 * implementation such as com.ibm.db2.jcc.DB2XADataSource.
		 */

		Damage damage = damageDao.find(idList.get(0));
		Client client = clientDao.find(idList.get(0));

		validationService.setNewNames(damage.getIdDamage(), newName); // WRITE
		validationService.updateDepositValue(damage.getIdDamage(), increment);
		client.setNameClient("maria"+ (System.currentTimeMillis() % 100000000));
		clientDao.update(client);
		clientDao.flush();
		damageDao.flush();
		Thread.sleep(SLEEP_TIME);

		validationService.verifyInitValue(idList.get(0)); // READ
		validationService.validationNames(idList.get(0)); // READ
		validationService.validationNames(idList.get(0)); // READ
		validationService.verifyInitValue(idList.get(0)); // READ
		Thread.sleep(SLEEP_TIME_READ);
		clientDao.flush();
		damageDao.flush();
		
		/* Exception simulation to be sure that the transaction is atomic */
//		Client client1 = clientDao.find(200);
//		String clientname = client1.getNameClient();
//		System.out.println(clientname);

	}

	/*
	 * With this method we can proof read some object while another thread is
	 * executing the main method to be proof
	 */
	@Override
	public void apiDamageReadOperations(List<Long> idList)
			throws NotValidDamageException, InstanceNotFoundException,
			InterruptedException {
			validationService.verifyInitValue(idList.get(0));
			Client client = clientDao.find(idList.get(0));
			
	}

}