package de.pta.WebServiceSpendTest.jsonmodel;

import de.pta.fd.WebServiceSpendTest.model.StTsupplier;



public class SupplierJson extends StTsupplier  {
	
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public SupplierJson( StTsupplier su) {
			this.setId(su.getId());
			this.setFirma(su.getFirma());
			this.setKredId(su.getKredId());
		}

		@Override
		public String toString()  {
			return new StringBuffer("{ \"ID\" : \"").append(getId())
					.append("\", \"FIRMA\" : \"").append(getFirma())
					.append("\", \"KRED_ID\" : \"").append(getKredId())
					.append("\"}")
					.toString();
		}

}
