package de.pta.WebServiceSpendTest.jsonmodel;

import de.pta.fd.WebServiceSpendTest.model.StTmatgruppe;


/**
 * MaterialgruppeJson
 * 
 * Klasse um ein StTmatgruppe POJO in einen Json-String umzuwandeln 
 */
public class MaterialgruppeJson extends StTmatgruppe  {
	
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MaterialgruppeJson( StTmatgruppe mg) {
			this.setId(mg.getId());
			this.setName(mg.getName());
			this.setMatcode(mg.getMatcode());
		}

		@Override
		public String toString()  {
			return new StringBuffer("{ \"ID\" : \"").append(getId())
					.append("\", \"NAME\" : \"").append(getName())
					.append("\", \"MAT_CODE\" : \"").append(getMatcode())
					.append("\"}")
					.toString();
		}

}
