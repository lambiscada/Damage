package com.damage.testing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.SystemException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.damage.damageService.ValidationService;
import com.damage.exception.InstanceNotFoundException;
import com.damage.exception.NotValidDamageException;
import com.damage.model.Damage;
import com.damage.model.DamageDaoN;
import com.damage.process.ApiDamageRefactorI;


public class ApiDamageRefactorLowTest {
	private static Context initialContext;
	private  ApiDamageRefactorI apiDamageRefactor;
	private long damage1, damage2;
	private  ValidationService validationService;
	private  DamageDaoN damageDao;
	private final int ROWS = 50;

	private final long INCREMENT = 200;

	
	public ApiDamageRefactorLowTest() {
		
	}

	@BeforeClass
	public static void initContext() throws Exception {
		Properties properties = new Properties();
		properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		initialContext = new InitialContext(properties);
		
	}

	@Before
	public void setup() throws NamingException {
			
		
		apiDamageRefactor = (ApiDamageRefactorI) initialContext
				.lookup("ejb:/Damage//ApiDamageRefactor!com.damage.process.ApiDamageRefactorI");
		validationService = (ValidationService) initialContext
				.lookup("ejb:/Damage//ValidationServiceBean!com.damage.damageService.ValidationService");
		damageDao = (DamageDaoN) initialContext
				.lookup("ejb:/Damage//DamageDaoNBean!com.damage.model.DamageDaoN");
	}
	@Test
	public void testApiDamage() throws NamingException, InterruptedException,
			NotValidDamageException, InstanceNotFoundException, SystemException {
		List<Long> dList = initDamages();
		String newName = "name" + (System.currentTimeMillis()%100000000);
		long startTime = System.currentTimeMillis();
		long ex = apiDamageRefactor.apiDamageValidationService(dList, newName, INCREMENT);
		long stopTime = System.currentTimeMillis();
		long executionTime = (stopTime - startTime);
//		 System.out.println("ApiDamageValidationService execution time:  "
//		 + executionTime + "ms");
//		
//		System.out.println("ejecucion:  "+ex);

	}

	public List<Long> initDamages() throws InstanceNotFoundException {
		List<Long> dList = new ArrayList<Long>();
		for (int i = 1; i <= ROWS; i++)
			dList.add((long) i);

		return dList;
	}
	
}
