package de.pta.fd.WebServiceSpendTest.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-08-14T09:32:19.707+0200")
@StaticMetamodel(StTmatgruppe.class)
public class StTmatgruppe_ {
	public static volatile SingularAttribute<StTmatgruppe, Long> id;
	public static volatile SingularAttribute<StTmatgruppe, String> matcode;
	public static volatile SingularAttribute<StTmatgruppe, String> name;
	public static volatile SingularAttribute<StTmatgruppe, StTmatgruppe> stTmatgruppe;
	public static volatile ListAttribute<StTmatgruppe, StTmatgruppe> stTmatgruppes;
}
