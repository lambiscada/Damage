package com.damage.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The persistent class for the DAMAGE database table.
 * 
 */
@Entity
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	private long idClient;

	private String nameClient;

	public Client() {
		super();
	}

	public Client(String nameClient) {
		this.nameClient = nameClient;

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getIdClient() {
		return this.idClient;
	}

	public void setIdClient(long idClient) {
		this.idClient = idClient;
	}
	

	public String getNameClient() {
		return this.nameClient;
	}

	public void setNameClient(String nameClient) {
		this.nameClient = nameClient;
	}

}