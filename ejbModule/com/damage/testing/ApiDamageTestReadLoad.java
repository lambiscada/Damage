package com.damage.testing;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import com.damage.damageService.ValidationService;
import com.damage.exception.InstanceNotFoundException;
import com.damage.exception.NotValidDamageException;
import com.damage.model.DamageDaoN;
import com.damage.process.ApiDamageI;

public class ApiDamageTestReadLoad extends AbstractJavaSamplerClient {
	private static Context initialContext;
	private ApiDamageI apiDamage;
	private long damage1, damage2;
	private ValidationService validationService;
	private final long INCREMENT = 200;
	private DamageDaoN damageDao;
	private List<Long> dList;
	private long num;
	
	public ApiDamageTestReadLoad() {
		super();
	}

	@Override
	public void setupTest(JavaSamplerContext context) {

	}
	@Override
    public Arguments getDefaultParameters() {
        Arguments defaultParameters = new Arguments();
        defaultParameters.addArgument("NUM", "${__threadNum}");
        return defaultParameters;
    }
	
	public SampleResult runTest(JavaSamplerContext context) {
		SampleResult result = new SampleResult();
		Properties properties = new Properties();
		properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		num  = context.getIntParameter("NUM");
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
			e2.printStackTrace();
		}
		try {
			dList = initDamages();
		} catch (InstanceNotFoundException e1) {
			e1.printStackTrace();
		}
		result.sampleStart();
		try {
			apiDamage.apiDamageReadOperations(dList.get(0));
		} catch (NotValidDamageException | InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		result.sampleEnd();
		result.setSuccessful(true);
		return result;

	}

	public List<Long> initDamages() throws InstanceNotFoundException {
		List<Long> dList = new ArrayList<Long>();
		dList.add(num);
//		dList.add((long) 7);
		return dList;
	}

}