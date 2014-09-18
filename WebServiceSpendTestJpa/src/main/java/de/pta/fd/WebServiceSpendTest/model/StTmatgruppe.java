package de.pta.fd.WebServiceSpendTest.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ST_TMATGRUPPE database table.
 * 
 */
@Entity
@Table(name="ST_TMATGRUPPE")
@NamedQuery(name="StTmatgruppe.findAll", query="SELECT s FROM StTmatgruppe s")
public class StTmatgruppe implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ST_TMATGRUPPE_ID_GENERATOR", sequenceName="MATGRUPPE_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ST_TMATGRUPPE_ID_GENERATOR")
	private long id;

	private String matcode;

	private String name;

	//bi-directional many-to-one association to StTmatgruppe
	@ManyToOne
	@JoinColumn(name="FK_PARENT")
	private StTmatgruppe stTmatgruppe;

	//bi-directional many-to-one association to StTmatgruppe
	@OneToMany(mappedBy="stTmatgruppe")
	private List<StTmatgruppe> stTmatgruppes;

	public StTmatgruppe() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMatcode() {
		return this.matcode;
	}

	public void setMatcode(String matcode) {
		this.matcode = matcode;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StTmatgruppe getStTmatgruppe() {
		return this.stTmatgruppe;
	}

	public void setStTmatgruppe(StTmatgruppe stTmatgruppe) {
		this.stTmatgruppe = stTmatgruppe;
	}

	public List<StTmatgruppe> getStTmatgruppes() {
		return this.stTmatgruppes;
	}

	public void setStTmatgruppes(List<StTmatgruppe> stTmatgruppes) {
		this.stTmatgruppes = stTmatgruppes;
	}

	public StTmatgruppe addStTmatgruppe(StTmatgruppe stTmatgruppe) {
		getStTmatgruppes().add(stTmatgruppe);
		stTmatgruppe.setStTmatgruppe(this);

		return stTmatgruppe;
	}

	public StTmatgruppe removeStTmatgruppe(StTmatgruppe stTmatgruppe) {
		getStTmatgruppes().remove(stTmatgruppe);
		stTmatgruppe.setStTmatgruppe(null);

		return stTmatgruppe;
	}

}