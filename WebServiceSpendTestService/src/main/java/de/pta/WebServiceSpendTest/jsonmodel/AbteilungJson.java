package de.pta.WebServiceSpendTest.jsonmodel;

import de.pta.fd.WebServiceSpendTest.model.StTabteilung;


public class AbteilungJson extends StTabteilung {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long fkStandort = 0L;
	private Long fkParent = 0L;
	public Long getFkStandort() {
		return fkStandort;
	}

	public void setFkStandort(Long fkStandort) {
		this.fkStandort = fkStandort;
	}

	public Long getFkParent() {
		return fkParent;
	}

	public void setFkParent(Long fkParent) {
		this.fkParent = fkParent;
	}

	private int treepos = 0;

	public AbteilungJson( StTabteilung ab, Long fkStandort, Long fkParent, int treepos) {
		this.setId(ab.getId());
		this.setFkStandort(fkStandort);
		this.setFkParent(fkParent);
		this.setName(ab.getName());
		this.setAbtNummer(ab.getAbtNummer());
		this.setTreepos(treepos);
	}

	public int getTreepos() {
		return treepos;
	}

	public void setTreepos(int treepos) {
		this.treepos = treepos;
	}

	@Override
	public String toString()  {
		return new StringBuffer("{ \"ID\" : \"").append(getId())
				.append("\", \"FK_STANDORT\" : \"").append(getFkStandort())
				.append("\", \"FK_PARENT\" : \"").append(getFkParent())
				.append("\", \"NAME\" : \"").append(getName())
				.append("\", \"ABT_NUMMER\" : \"").append(getAbtNummer())
				.append("\", \"TREEPOS\" : \"").append(getTreepos())
				.append("\"}")
				.toString();
	}
}
