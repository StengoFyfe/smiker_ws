package de.pta.fd.WebServiceSpendTest.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the ST_TSUPPLIER database table.
 * 
 */
@Entity
@Table(name="ST_TSUPPLIER")
@NamedQuery(name="StTsupplier.findAll", query="SELECT s FROM StTsupplier s")
public class StTsupplier implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	private String firma;

	@Column(name="KRED_ID")
	private String kredId;

	public StTsupplier() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirma() {
		return this.firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public String getKredId() {
		return this.kredId;
	}

	public void setKredId(String kredId) {
		this.kredId = kredId;
	}

}