package com.damage.testing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
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
		List<Damage> dList = initDamages();
		long execution = 0;
		String newName = "name" + (System.currentTimeMillis()%100000000);
		long startTime = System.currentTimeMillis();
		try {
			execution = apiDamageRefactor.apiDamageValidationService(dList.get(0), dList.get(0),
					newName, INCREMENT);
		} catch (SecurityException | IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		long stopTime = System.currentTimeMillis();
//		long executionTime = (stopTime - startTime);
//		 System.out.println("ApiDamageValidationService execution time:  "
//		 + executionTime + "ms");
//		
//		System.out.println("ejecucion:  "+execution);

	}

	

	 public List<Damage> initDamages() throws InstanceNotFoundException {
	 List<Damage> dList = new ArrayList<Damage>();
	 dList.add(damageDao.find(7));
	 return dList;
	 }
	
}

