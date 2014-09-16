/**
 * 
 */
package de.pta.fd.WebServiceSpendTest.model.additionalBeans;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author dietricf
 *
 */
public class UmsatzResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8691652999850470139L;
	/**
	 * 
	 */

	private Long Id = null;
	private java.util.Date buchungsdatum = null;
	private BigDecimal betrag = null;
	private String RefUmsatztyp = null;
	
	public UmsatzResult() {
		
	}
	
	public Long getId() {
		return Id;
	}


	public void setId(Long id) {
		Id = id;
	}


	public java.util.Date getBuchungsdatum() {
		return buchungsdatum;
	}


	public void setBuchungsdatum(java.util.Date buchungsdatum) {
		this.buchungsdatum = buchungsdatum;
	}


	public BigDecimal getBetrag() {
		return betrag;
	}


	public void setBetrag(BigDecimal betrag) {
		this.betrag = betrag;
	}


	public String getRefUmsatztyp() {
		return RefUmsatztyp;
	}


	public void setRefUmsatztyp(String refUmsatztyp) {
		RefUmsatztyp = refUmsatztyp;
	}


}
