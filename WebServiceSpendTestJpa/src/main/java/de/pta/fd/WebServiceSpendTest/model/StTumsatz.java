package de.pta.fd.WebServiceSpendTest.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the ST_TUMSATZ database table.
 * 
 */
@Entity
@Table(name="ST_TUMSATZ")
@NamedQuery(name="StTumsatz.findAll", query="SELECT s FROM StTumsatz s")
public class StTumsatz implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ST_TUMSATZ_ID_GENERATOR", sequenceName="UMSATZ_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ST_TUMSATZ_ID_GENERATOR")
	private long id;

	private BigDecimal betrag;

	private Timestamp buchungsdatum;

	@Column(name="FK_ABTEILUNG")
	private BigDecimal fkAbteilung;

	@Column(name="FK_MATGRUPPE")
	private BigDecimal fkMatgruppe;

	@Column(name="FK_REF_UMSATZTYP")
	private BigDecimal fkRefUmsatztyp;

	@Column(name="FK_SUPPLIER")
	private BigDecimal fkSupplier;

	public StTumsatz() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getBetrag() {
		return this.betrag;
	}

	public void setBetrag(BigDecimal betrag) {
		this.betrag = betrag;
	}

	public Timestamp getBuchungsdatum() {
		return this.buchungsdatum;
	}

	public void setBuchungsdatum(Timestamp buchungsdatum) {
		this.buchungsdatum = buchungsdatum;
	}

	public BigDecimal getFkAbteilung() {
		return this.fkAbteilung;
	}

	public void setFkAbteilung(BigDecimal fkAbteilung) {
		this.fkAbteilung = fkAbteilung;
	}

	public BigDecimal getFkMatgruppe() {
		return this.fkMatgruppe;
	}

	public void setFkMatgruppe(BigDecimal fkMatgruppe) {
		this.fkMatgruppe = fkMatgruppe;
	}

	public BigDecimal getFkRefUmsatztyp() {
		return this.fkRefUmsatztyp;
	}

	public void setFkRefUmsatztyp(BigDecimal fkRefUmsatztyp) {
		this.fkRefUmsatztyp = fkRefUmsatztyp;
	}

	public BigDecimal getFkSupplier() {
		return this.fkSupplier;
	}

	public void setFkSupplier(BigDecimal fkSupplier) {
		this.fkSupplier = fkSupplier;
	}

}