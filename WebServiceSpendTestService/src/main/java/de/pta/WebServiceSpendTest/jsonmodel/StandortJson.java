package de.pta.WebServiceSpendTest.jsonmodel;

import de.pta.fd.WebServiceSpendTest.model.StTstandort;

public class StandortJson extends StTstandort {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StandortJson( StTstandort so) {
		this.setId(so.getId());
		this.setName(so.getName());
		this.setNummer(so.getNummer());
	}

	@Override
	public String toString()  {
		return new StringBuffer("{ \"ID\" : \"").append(getId())
				.append("\", \"NAME\" : \"").append(getName())
				.append("\", \"NUMMER\" : \"").append(getNummer())
				.append("\"}")
				.toString();
	}
}
