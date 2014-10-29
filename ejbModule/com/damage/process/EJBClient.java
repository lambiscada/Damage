package com.damage.process;

import java.util.Calendar;

import javax.naming.Context;
import javax.naming.NamingException;

import com.damage.model.Damage;
import com.damage.model.DamageDaoN;
import com.damage.model.DamageDaoNBean;

public class EJBClient {

	public static void main(String[] args) throws NamingException {
		Context context = null;
		DamageDaoN bean = null;
		// 1. Obtaining Context
		context = JNDILookupUtil.getInitialContext();
		// 2. Generate JNDI Lookup name
		String lookupName = getLookupName();
		// 3. Lookup and cast
		bean = (DamageDaoN) context.lookup(lookupName);

		Damage damage = createDamage("Rodrigo", Calendar.getInstance(),
				"accidente coche", 1, "MainStreet", "Crash", 100, 500);
		bean.save(damage);
		bean.flush();

		System.out.println(damage.getIdDamage());
		context.close();

	}

	private static String getLookupName() {
		
		String appName = "";
		String moduleName = "Damage";
		String distinctName = "";
		String beanName = DamageDaoNBean.class.getSimpleName();
		final String interfaceName = DamageDaoN.class.getName();
		
		String name = "ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + interfaceName;
		return name;
	}

	private static Damage createDamage(String clientName, Calendar dateAct,
			String description, int level, String location, String nameDamage,
			long valueIni, long depositIni) {

		Damage damage = new Damage(clientName, dateAct, description, level,
				location, nameDamage, valueIni, depositIni);
		return damage;
	}

}
