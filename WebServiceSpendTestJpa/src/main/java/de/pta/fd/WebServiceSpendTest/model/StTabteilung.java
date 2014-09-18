package de.pta.fd.WebServiceSpendTest.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ST_TABTEILUNG database table.
 * 
 */
@Entity
@Table(name="ST_TABTEILUNG")
@NamedQuery(name="StTabteilung.findAll", query="SELECT s FROM StTabteilung s")
public class StTabteilung implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ST_TABTEILUNG_ID_GENERATOR", sequenceName="ABTEILUNG_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ST_TABTEILUNG_ID_GENERATOR")
	private long id;

	@Column(name="ABT_NUMMER")
	private String abtNummer;

	private String name;

	//bi-directional many-to-one association to StTabteilung
	@ManyToOne
	@JoinColumn(name="FK_PARENT")
	private StTabteilung stTabteilung;

	//bi-directional many-to-one association to StTabteilung
	@OneToMany(mappedBy="stTabteilung")
	private List<StTabteilung> stTabteilungs;

	//bi-directional many-to-one association to StTstandort
	@ManyToOne
	@JoinColumn(name="FK_STANDORT")
	private StTstandort stTstandort;

	public StTabteilung() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAbtNummer() {
		return this.abtNummer;
	}

	public void setAbtNummer(String abtNummer) {
		this.abtNummer = abtNummer;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StTabteilung getStTabteilung() {
		return this.stTabteilung;
	}

	public void setStTabteilung(StTabteilung stTabteilung) {
		this.stTabteilung = stTabteilung;
	}

	public List<StTabteilung> getStTabteilungs() {
		return this.stTabteilungs;
	}

	public void setStTabteilungs(List<StTabteilung> stTabteilungs) {
		this.stTabteilungs = stTabteilungs;
	}

	public StTabteilung addStTabteilung(StTabteilung stTabteilung) {
		getStTabteilungs().add(stTabteilung);
		stTabteilung.setStTabteilung(this);

		return stTabteilung;
	}

	public StTabteilung removeStTabteilung(StTabteilung stTabteilung) {
		getStTabteilungs().remove(stTabteilung);
		stTabteilung.setStTabteilung(null);

		return stTabteilung;
	}

	public StTstandort getStTstandort() {
		return this.stTstandort;
	}

	public void setStTstandort(StTstandort stTstandort) {
		this.stTstandort = stTstandort;
	}

}