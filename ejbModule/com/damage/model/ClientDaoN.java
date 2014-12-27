package com.damage.model;

import javax.ejb.Remote;

import com.damage.exception.InstanceNotFoundException;

@Remote
public interface ClientDaoN {

	Client save(Client client);

	void update(Client client);

	void remove(Client client) throws InstanceNotFoundException;

	Client find(long idClient) throws InstanceNotFoundException;

	void flush();

}

