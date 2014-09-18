package de.pta.fd.WebServiceSpendTest.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ST_RUMSATZTYP database table.
 * 
 */
@Entity
@Table(name="ST_RUMSATZTYP")
@NamedQuery(name="StRumsatztyp.findAll", query="SELECT s FROM StRumsatztyp s")
public class StRumsatztyp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ST_RUMSATZTYP_ID_GENERATOR", sequenceName="UMSATZTYP_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ST_RUMSATZTYP_ID_GENERATOR")
	private long id;

	@Column(name="UMSATZ_TYP")
	private String umsatzTyp;

	public StRumsatztyp() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUmsatzTyp() {
		return this.umsatzTyp;
	}

	public void setUmsatzTyp(String umsatzTyp) {
		this.umsatzTyp = umsatzTyp;
	}

}