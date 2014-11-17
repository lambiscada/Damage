package com.damage.testing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.TransactionSynchronizationRegistry;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.damage.damageService.ValidationService;
import com.damage.exception.InstanceNotFoundException;
import com.damage.exception.NotValidDamageException;
import com.damage.model.Damage;
import com.damage.model.DamageDaoN;
import com.damage.process.ApiDamageI;

public class ApiDamageTest {
	private static Context initialContext;
	private ApiDamageI apiDamage;
	private long damage1, damage2;
	private ValidationService validationService;
	private DamageDaoN damageDao;

	private final long INCREMENT = 200;

	public ApiDamageTest() {

	}

	@BeforeClass
	public static void initContext() throws Exception {
		Properties properties = new Properties();
		properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		initialContext = new InitialContext(properties);

	}

	@Before
	public void setup() throws NamingException {

		apiDamage = (ApiDamageI) initialContext
				.lookup("ejb:/Damage//ApiDamage!com.damage.process.ApiDamageI");
		validationService = (ValidationService) initialContext
				.lookup("ejb:/Damage//ValidationServiceBean!com.damage.damageService.ValidationService");
		damageDao = (DamageDaoN) initialContext
				.lookup("ejb:/Damage//DamageDaoNBean!com.damage.model.DamageDaoN");
	}

	@Test
	public void testApiDamage() throws NamingException, InterruptedException,
			NotValidDamageException, InstanceNotFoundException {
		List<Damage> dList = initDamages();
		String newName = "name" + (System.currentTimeMillis() % 100000000);
//		long startTime = System.currentTimeMillis();

		apiDamage.apiDamageValidationService(dList.get(0), dList.get(0),
				newName, INCREMENT);
//		long stopTime = System.currentTimeMillis();
//		long executionTime = (stopTime - startTime);
//		 System.out.println("ApiDamageValidationService execution time:  "
//		 + executionTime + "ms");
		

	}

	 public List<Damage> initDamages() throws InstanceNotFoundException {
	 List<Damage> dList = new ArrayList<Damage>();
	 dList.add(damageDao.find(7));
	 return dList;
	 }
	
}
