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
import org.apache.jmeter.samplers.SampleResult;

import com.damage.damageService.ValidationService;
import com.damage.exception.InstanceNotFoundException;
import com.damage.exception.NotValidDamageException;
import com.damage.model.Damage;
import com.damage.model.DamageDaoN;
import com.damage.process.ApiDamageRefactorI;

public class ApiDamageRefactorLowTestLoad extends AbstractJavaSamplerClient {
	private static Context initialContext;
	private ApiDamageRefactorI apiDamageRefactor;
	private long damage1, damage2;
	private long executionTime;
	private ValidationService validationService;
	private DamageDaoN damageDao;
	private List<Damage> dList;
	private final long INCREMENT = 200;

	public ApiDamageRefactorLowTestLoad() {
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
			apiDamageRefactor = (ApiDamageRefactorI) initialContext
					.lookup("ejb:/Damage//ApiDamageRefactor!com.damage.process.ApiDamageRefactorI");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		try {
			damageDao = (DamageDaoN) initialContext
					.lookup("ejb:/Damage//DamageDaoNBean!com.damage.model.DamageDaoN");
		} catch (NamingException e2) {
			e2.printStackTrace();
		}
		try {
			dList = initDamages();
		} catch (InstanceNotFoundException e1) {
			e1.printStackTrace();
		}
		result.sampleStart();
		try {
			String newName = "name" + (System.currentTimeMillis()%100000000);
			executionTime = apiDamageRefactor.apiDamageValidationService(dList.get(0),dList.get(0), newName, INCREMENT);
		} catch (InterruptedException | NotValidDamageException
				| InstanceNotFoundException | SystemException e) {
			e.printStackTrace();
		}

		result.sampleEnd();
		result.setSuccessful(true);
		return result;

	}

	public List<Damage> initDamages() throws InstanceNotFoundException {

		List<Damage> dList = new ArrayList<Damage>();
		dList.add(damageDao.find(7));
//		dList.add(damageDao.find(damage2));
		return dList;
	}

	@Override
	public Arguments getDefaultParameters() {

		Arguments params = new Arguments();

		return params;
	}

}