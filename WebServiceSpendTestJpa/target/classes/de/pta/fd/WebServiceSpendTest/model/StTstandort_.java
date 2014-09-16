package de.pta.fd.WebServiceSpendTest.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-08-14T09:32:19.710+0200")
@StaticMetamodel(StTstandort.class)
public class StTstandort_ {
	public static volatile SingularAttribute<StTstandort, Long> id;
	public static volatile SingularAttribute<StTstandort, String> name;
	public static volatile SingularAttribute<StTstandort, String> nummer;
	public static volatile ListAttribute<StTstandort, StTabteilung> stTabteilungs;
}
