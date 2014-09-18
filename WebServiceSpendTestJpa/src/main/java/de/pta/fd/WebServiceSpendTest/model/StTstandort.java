package de.pta.fd.WebServiceSpendTest.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the st_tstandort database table.
 * 
 */
@Entity
@Table(name="st_tstandort")
@NamedQuery(name="StTstandort.findAll", query="SELECT s FROM StTstandort s")
public class StTstandort implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String name;

	private String nummer;

	//bi-directional many-to-one association to StTabteilung
	@OneToMany(mappedBy="stTstandort")
	private List<StTabteilung> stTabteilungs;

	public StTstandort() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
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