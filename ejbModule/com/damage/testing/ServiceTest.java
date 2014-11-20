package com.damage.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.damage.damageService.ValidationService;
import com.damage.exception.InstanceNotFoundException;
import com.damage.exception.NotValidDamageException;
import com.damage.model.Damage;
import com.damage.model.DamageDaoN;

public class ServiceTest {
	private final long INCREMENT = 500;
	
	private static Context initialContext;
	private ValidationService validationService;
	private DamageDaoN damageDao;
	private List<Long> dList = new ArrayList<Long>();
	
	@BeforeClass
	public static void initContainer() throws Exception {
		Properties properties = new Properties();
		properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		initialContext = new InitialContext(properties);

	}

	@Before
	public void initContextAndBeans() throws NamingException,
			NotSupportedException, SystemException {
		validationService = (ValidationService) initialContext
				.lookup("ejb:/Damage//ValidationServiceBean!com.damage.damageService.ValidationService");
		damageDao = (DamageDaoN) initialContext
				.lookup("ejb:/Damage//DamageDaoNBean!com.damage.model.DamageDaoN");

	}

	@After
	public void cleanDb() throws InstanceNotFoundException {
		for (int i = 0; i < dList.size(); i++) {
			validationService.removeDamage(damageDao.find(dList.get(i)));
		}
	}

	@Test
	public void testVerifyInitValue() throws NotValidDamageException,
			NamingException, FileNotFoundException, IOException,
			ClassNotFoundException, InstanceNotFoundException {
		Damage damage = createDamage("Rodrigo", Calendar.getInstance(),
				"accidente coche", 1, "MainStreet", "Crash", 100, 500);
		long v1 = damage.getValueIni();
		long v2 = this.validationService.verifyInitValue(damage.getIdDamage());
		assertEquals(v1, v2);

	}

	@Test
	public void testValidationName() throws NotValidDamageException,
			InstanceNotFoundException {
		Damage damage = createDamage("Francisco", Calendar.getInstance(),
				"accidente fatal", 2, "Calle mayor", "Car Crash", 1000, 50000);
		boolean value = validationService.validationNames(damage.getIdDamage());
		assertEquals(value, true);

	}

	@Test(expected = NotValidDamageException.class)
	public void testValidationNameNotExist() throws NotValidDamageException,
			InstanceNotFoundException {
		Damage damage = createDamage("", Calendar.getInstance(),
				"accidente fatal", 2, "Calle mayor", "Car Crash", 1000, 50000);
		validationService.validationNames(damage.getIdDamage());

	}

//	@Test
//	public void testNewNames() throws InstanceNotFoundException {
//		Damage damage = createDamage("Sebastian", Calendar.getInstance(),
//				"accidente fatal", 2, "Calle mayor", "Car Crash", 1000, 50000);
//		String newName = "John";
//		validationService.setNewNames(damage.getIdDamage(), newName);
//		Damage damageNewName = damageDao.find(damage.getIdDamage());
//		assertNotEquals(damageNewName.getClientName(), "Sebastian");
//		assertEquals(damageNewName.getClientName(), "John");
//
//	}

	@Test(expected = NotValidDamageException.class)
	public void testVerificationPastDateTest() throws NotValidDamageException,
			ParseException, InstanceNotFoundException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = "12/09/2020";
		Date date = (Date) formatter.parse(dateString);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Damage damage = createDamage("Maria", cal, "accidente fatal", 2,
				"Calle mayor", "Car Crash", 1000, 50000);
		validationService.verifDates(damage.getIdDamage());

	}

	@Test
	public void testVerificationDateTest() throws NotValidDamageException,
			InstanceNotFoundException, ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = "12/09/1998";
		Date date = (Date) formatter.parse(dateString);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Damage damage = createDamage("Maria", cal, "accidente fatal", 2,
				"Calle mayor", "Car Crash", 1000, 50000);
		assertTrue(validationService.verifDates(damage.getIdDamage()));
	}

	@Test
	public void testCompareDamageLevel() throws InstanceNotFoundException {
		Damage damage1 = createDamage("Anton", Calendar.getInstance(),
				"accidente fatal", 3, "Calle mayor", "Car Crash", 1000, 50000);
		Damage damage2 = createDamage("Maria", Calendar.getInstance(),
				"accidente fatal", 1, "Calle mayor", "Car Crash", 7, 9);
		Damage damageM = validationService.compareDamageLevel(damage1.getIdDamage(), damage2.getIdDamage());
		assertEquals(damageM.getLevelDamage(), damage1.getLevelDamage());

	}

	@Test
	public void testUpdateDepositValue() throws InstanceNotFoundException {
		Damage damage1 = createDamage("Anton", Calendar.getInstance(),
				"accidente fatal", 3, "Calle mayor", "Car Crash", 1000, 50000);
		long pastDeposit = damage1.getDepositIni();
		validationService.updateDepositValue(damage1.getIdDamage(), INCREMENT);
		Damage damageNew = damageDao.find(damage1.getIdDamage());
		assertNotEquals(pastDeposit, damageNew.getDepositIni());
		assertEquals(damageNew.getDepositIni(), pastDeposit + INCREMENT);
	}

	@Test
	public void testRemoveRepeatedNamesTest() throws InstanceNotFoundException {
		Damage damage1 = createDamage("Anton", Calendar.getInstance(),
				"accidente fatal", 3, "Calle mayor", "Car Crash", 1000, 50000);
		Damage damage2 = createDamage("Anton", Calendar.getInstance(),
				"bla bla", 1, "Calle mayor", "Car Crash", 10300, 3000);
		dList.remove(damage1.getIdDamage());
		assertTrue(validationService.removeRepeatNames(damage1, damage2));

	}

	private Damage createDamage(String clientName, Calendar dateAct,
			String description, int level, String location, String nameDamage,
			long valueIni, long depositIni) throws InstanceNotFoundException {
		long d = validationService.createNewDamage(clientName, dateAct,
				description, level, location, nameDamage, valueIni, depositIni);
		dList.add(d);
		Damage dam = damageDao.find(d);
		
		return dam;

	}

}
