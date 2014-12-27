package com.damage.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import com.damage.exception.InstanceNotFoundException;

/**
 * Session Bean implementation class DamageDaoNBean
 */

@Stateless
public class ClientDaoNBean implements ClientDaoN {


	@PersistenceContext(unitName = "DNDN2")
	private EntityManager em;
	
	public ClientDaoNBean() {

	}

	@Override
	public Client save(Client client) {				//SQL-insert
		em.persist(client);
		return em.find(Client.class, client.getIdClient());
	}

	@Override
	public void update(Client client) {	
		em.merge(client);
	}

	@Override
	public void remove(Client client) throws InstanceNotFoundException {
		em.remove(em.contains(client) ? client : em.merge(client));

	}

	@Override
	public Client find(long idClient) throws InstanceNotFoundException {
		return em.find(Client.class, idClient);
	}

	@Override
	public void flush() {
//		System.out.println(em.getFlushMode());
		em.flush();

	}

}
