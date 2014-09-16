package de.pta.fd.WebServiceSpendTest.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-08-14T09:32:19.714+0200")
@StaticMetamodel(StTumsatz.class)
public class StTumsatz_ {
	public static volatile SingularAttribute<StTumsatz, Long> id;
	public static volatile SingularAttribute<StTumsatz, BigDecimal> betrag;
	public static volatile SingularAttribute<StTumsatz, Timestamp> buchungsdatum;
	public static volatile SingularAttribute<StTumsatz, BigDecimal> fkAbteilung;
	public static volatile SingularAttribute<StTumsatz, BigDecimal> fkMatgruppe;
	public static volatile SingularAttribute<StTumsatz, BigDecimal> fkRefUmsatztyp;
	public static volatile SingularAttribute<StTumsatz, BigDecimal> fkSupplier;
}
