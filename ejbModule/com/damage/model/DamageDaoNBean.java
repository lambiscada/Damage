package com.damage.model;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import com.damage.exception.InstanceNotFoundException;

/**
 * Session Bean implementation class DamageDaoNBean
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class DamageDaoNBean implements DamageDaoN {


	@PersistenceContext(unitName = "DNDN",  type = PersistenceContextType.TRANSACTION)
	private EntityManager em;

	public DamageDaoNBean() {

	}

	@Override
	public Damage save(Damage damage) {				//SQL-insert
		em.persist(damage);
		return em.find(Damage.class, damage.getIdDamage());
	}

	@Override
	public void update(Damage damage) {	
		em.merge(damage);
	}

	@Override
	public void remove(Damage damage) throws InstanceNotFoundException {
		em.remove(em.contains(damage) ? damage : em.merge(damage));

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Damage find(long idDamage) throws InstanceNotFoundException {
		return em.find(Damage.class, idDamage);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void flush() {
//		System.out.println(em.getFlushMode());
		em.flush();

	}

}
