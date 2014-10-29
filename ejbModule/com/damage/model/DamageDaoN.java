package com.damage.model;

import javax.ejb.Remote;

import com.damage.exception.InstanceNotFoundException;

@Remote
public interface DamageDaoN {

	Damage save(Damage damage);

	void update(Damage damage);

	void remove(Damage damage) throws InstanceNotFoundException;

	Damage find(long idDamage) throws InstanceNotFoundException;

	void flush();

}

