package de.pta.fd.WebServiceSpendTest.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-08-14T09:32:19.705+0200")
@StaticMetamodel(StTabteilung.class)
public class StTabteilung_ {
	public static volatile SingularAttribute<StTabteilung, Long> id;
	public static volatile SingularAttribute<StTabteilung, String> abtNummer;
	public static volatile SingularAttribute<StTabteilung, String> name;
	public static volatile SingularAttribute<StTabteilung, StTabteilung> stTabteilung;
	public static volatile ListAttribute<StTabteilung, StTabteilung> stTabteilungs;
	public static volatile SingularAttribute<StTabteilung, StTstandort> stTstandort;
}
