package com.damage.model;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.damage.exception.InstanceNotFoundException;

/**
 * Session Bean implementation class DamageDaoNBean
 */

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DamageDaoNBean implements DamageDaoN {
	
	@PersistenceContext(unitName = "DNDN")
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
	public Damage find(long idDamage) throws InstanceNotFoundException {
		return em.find(Damage.class, idDamage);
	}

	@Override
	public void flush() {
		em.flush();

	}

}
