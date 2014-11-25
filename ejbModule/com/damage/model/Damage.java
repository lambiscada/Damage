package com.damage.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * The persistent class for the DAMAGE database table.
 * 
 */
@Entity
public class Damage implements Serializable {
	private static final long serialVersionUID = 1L;

	private long idDamage;

	private String clientName;

	@Temporal(TemporalType.DATE)
	private Calendar dateAct;

	private String description;

	private int levelDamage;

	private String locationDamage;

	private String nameDamage;

	private long valueIni;

	private long depositIni;


	public Damage() {
		super();
	}

	public Damage(String clientName, Calendar dateAct, String description,
			int levelDamage, String location, String nameDamage, long valueIni,
			long depositIni) {
		this.clientName = clientName;
		this.dateAct = dateAct;
		this.description = description;
		this.levelDamage = levelDamage;
		this.locationDamage = location;
		this.nameDamage = nameDamage;
		this.valueIni = valueIni;
		this.depositIni = depositIni;

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getIdDamage() {
		return this.idDamage;
	}

	public void setIdDamage(long idDamage) {
		this.idDamage = idDamage;
	}

	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDateAct() {
		return this.dateAct;
	}

	public void setDateAct(Calendar dateAct) {
		this.dateAct = dateAct;
	}

	public long getDepositIni() {
		return this.depositIni;
	}

	public void setDepositIni(long depositIni) {
		this.depositIni = depositIni;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getLevelDamage() {
		return this.levelDamage;
	}

	public void setLevelDamage(int levelDamage) {
		this.levelDamage = levelDamage;
	}

	public String getLocationDamage() {
		return this.locationDamage;
	}

	public void setLocationDamage(String locationDamage) {
		this.locationDamage = locationDamage;
	}

	public String getNameDamage() {
		return this.nameDamage;
	}

	public void setNameDamage(String nameDamage) {
		this.nameDamage = nameDamage;
	}

	public long getValueIni() {
		return this.valueIni;
	}

	public void setValueIni(long valueIni) {
		this.valueIni = valueIni;
	}


}