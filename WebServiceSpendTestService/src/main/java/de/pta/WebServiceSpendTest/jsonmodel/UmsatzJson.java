package de.pta.WebServiceSpendTest.jsonmodel;


import de.pta.fd.WebServiceSpendTest.model.additionalBeans.UmsatzResult;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * UmsatzJson
 * 
 * Klasse um ein StTumsatz POJO in einen Json-String umzuwandeln
 * Achtung: Hier muss auf die korrekte Darstellung der Datumsformate geachtet werden. 
 */
public class UmsatzJson extends UmsatzResult  {
		private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public UmsatzJson( UmsatzResult um) {
			this.setId(um.getId());
			this.setBuchungsdatum(um.getBuchungsdatum());
			this.setBetrag(um.getBetrag());
			this.setRefUmsatztyp(um.getRefUmsatztyp());
		}

		@Override
		public String toString()  {
			return new StringBuffer("{ \"ID\" : \"").append(getId())
					.append("\", \"BUCHUNGSDATUM\" : \"").append(df.format(getBuchungsdatum()))
					.append("\", \"BETRAG\" : \"").append(getBetrag())
					.append("\", \"UMSATZ_TYP\" : \"").append(getRefUmsatztyp())
					.append("\"}")
					.toString();
		}

}


