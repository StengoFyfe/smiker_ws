package de.pta.fd.WebServiceSpendTest.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the st_tumsatz database table.
 * 
 */
@Entity
@Table(name="st_tumsatz")
@NamedQuery(name="StTumsatz.findAll", query="SELECT s FROM StTumsatz s")
public class StTumsatz implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal betrag;

	@Temporal(TemporalType.DATE)
	private Date buchungsdatum;

	@Column(name="FK_ABTEILUNG")
	private int fkAbteilung;

	@Column(name="FK_MATGRUPPE")
	private int fkMatgruppe;

	@Column(name="FK_REF_UMSATZTYP")
	private int fkRefUmsatztyp;

	@Column(name="FK_SUPPLIER")
	private int fkSupplier;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	public StTumsatz() {
	}

	public BigDecimal getBetrag() {
		return this.betrag;
	}

	public void setBetrag(BigDecimal betrag) {
		this.betrag = betrag;
	}

	public Date getBuchungsdatum() {
		return this.buchungsdatum;
	}

	public void setBuchungsdatum(Date buchungsdatum) {
		this.buchungsdatum = buchungsdatum;
	}

	public int getFkAbteilung() {
		return this.fkAbteilung;
	}

	public void setFkAbteilung(int fkAbteilung) {
		this.fkAbteilung = fkAbteilung;
	}

	public int getFkMatgruppe() {
		return this.fkMatgruppe;
	}

	public void setFkMatgruppe(int fkMatgruppe) {
		this.fkMatgruppe = fkMatgruppe;
	}

	public int getFkRefUmsatztyp() {
		return this.fkRefUmsatztyp;
	}

	public void setFkRefUmsatztyp(int fkRefUmsatztyp) {
		this.fkRefUmsatztyp = fkRefUmsatztyp;
	}

	public int getFkSupplier() {
		return this.fkSupplier;
	}

	public void setFkSupplier(int fkSupplier) {
		this.fkSupplier = fkSupplier;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

}