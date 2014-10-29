package com.damage.testing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.SystemException;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterThread;
import org.apache.jmeter.samplers.SampleResult;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.damage.damageService.ValidationService;
import com.damage.exception.InstanceNotFoundException;
import com.damage.exception.NotValidDamageException;
import com.damage.model.Damage;
import com.damage.model.DamageDaoN;
import com.damage.process.ApiDamageI;
import com.damage.process.ApiDamageRefactorI;

public class ApiDamageTestLoad extends AbstractJavaSamplerClient {
	private static Context initialContext;
	private ApiDamageI apiDamage;
	private long damage1, damage2;
	private  ValidationService validationService;
	private final long INCREMENT = 200;
	private DamageDaoN damageDao;
	private List<Damage> dList;

	
	
	public ApiDamageTestLoad() {
		super();
	}

	@Override
	public void setupTest(JavaSamplerContext context) {

	}

	public SampleResult runTest(JavaSamplerContext context) {
		SampleResult result = new SampleResult();
		Properties properties = new Properties();
		properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		try {
			initialContext = new InitialContext(properties);
		} catch (NamingException e1) {

			e1.printStackTrace();
		}
		try {
			validationService = (ValidationService) initialContext
					.lookup("ejb:/Damage//ValidationServiceBean!com.damage.damageService.ValidationService");
		} catch (NamingException e) {

			e.printStackTrace();
		}
		try {
			apiDamage = (ApiDamageI) initialContext
					.lookup("ejb:/Damage//ApiDamage!com.damage.process.ApiDamageI");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		try {
			damageDao = (DamageDaoN) initialContext
					.lookup("ejb:/Damage//DamageDaoNBean!com.damage.model.DamageDaoN");
		} catch (NamingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			dList = initDamages();
		} catch (InstanceNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		result.sampleStart();
		try {
			// executionTime = apiDamageRefactor.apiDamageValidationService(
			// dList.get(0), dList.get(1), newName, INCREMENT);
			String newName = "name" + (System.currentTimeMillis()%100000000);
			apiDamage.apiDamageValidationService(dList.get(0),dList.get(0), newName, INCREMENT);
		} catch (InterruptedException | NotValidDamageException
				| InstanceNotFoundException e) {
			e.printStackTrace();
		}

		result.sampleEnd();
		result.setSuccessful(true);
		return result;

	}

	public List<Damage> initDamages() throws InstanceNotFoundException {
//		damage1 = validationService.createNewDamage("New Client",
//				Calendar.getInstance(), "description1", 1, "location",
//				"nameDamage", 100, 200);
//		damage2 = validationService.createNewDamage("New Client",
//				Calendar.getInstance(), "description1", 1, "location",
//				"nameDamage", 100, 200);
		List<Damage> dList = new ArrayList<Damage>();
//		dList.add(damage1);
//		dList.add(damage2);
		dList.add(damageDao.find(7));
//		dList.add(damageDao.find(damage2));
		return dList;
	}
}