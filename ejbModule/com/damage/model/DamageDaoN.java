package com.damage.model;

import javax.ejb.Remote;
import javax.persistence.EntityManager;

import com.damage.exception.InstanceNotFoundException;

@Remote
public interface DamageDaoN {

	Damage save(Damage damage,EntityManager em);

	void update(Damage damage,EntityManager em);

	void remove(Damage damage,EntityManager em) throws InstanceNotFoundException;

	Damage find(long idDamage, EntityManager em) throws InstanceNotFoundException;

	void flush(EntityManager em);

}

