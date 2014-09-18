package de.pta.fd.WebServiceSpendTest.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ST_TSTANDORT database table.
 * 
 */
@Entity
@Table(name="ST_TSTANDORT")
@NamedQuery(name="StTstandort.findAll", query="SELECT s FROM StTstandort s")
public class StTstandort implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ST_TSTANDORT_ID_GENERATOR", sequenceName="STANDORT_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ST_TSTANDORT_ID_GENERATOR")
	private long id;

	
	// Name des Standortes
	private String name;

	private String nummer;

	//bi-directional many-to-one association to StTabteilung
	@OneToMany(mappedBy="stTstandort", cascade={CascadeType.ALL})
	private List<StTabteilung> stTabteilungs;

	public StTstandort() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNummer() {
		return this.nummer;
	}

	public void setNummer(String nummer) {
		this.nummer = nummer;
	}

	public List<StTabteilung> getStTabteilungs() {
		return this.stTabteilungs;
	}

	public void setStTabteilungs(List<StTabteilung> stTabteilungs) {
		this.stTabteilungs = stTabteilungs;
	}

	public StTabteilung addStTabteilung(StTabteilung stTabteilung) {
		getStTabteilungs().add(stTabteilung);
		stTabteilung.setStTstandort(this);

		return stTabteilung;
	}

	public StTabteilung removeStTabteilung(StTabteilung stTabteilung) {
		getStTabteilungs().remove(stTabteilung);
		stTabteilung.setStTstandort(null);

		return stTabteilung;
	}

}