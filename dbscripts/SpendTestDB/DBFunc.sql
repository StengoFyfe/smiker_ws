create or replace TYPE rUmsatzResultType AS OBJECT(ID	NUMBER(10,0),
FK_ABTEILUNG NUMBER(10,0),
FK_MATGRUPPE	NUMBER(10,0),
FK_SUPPLIER	NUMBER(10,0),
BUCHUNGSDATUM	TIMESTAMP(6),
BETRAG	NUMBER(19,4),
FK_REF_UMSATZTYP	NUMBER(10,0) ) ;

*******************************************************************************
create or replace TYPE tUmsatzResultType AS TABLE OF rUmsatzResultType;
*******************************************************************************

create or replace function GetUmsatzSpendResult(
  FK_ABTEILUNG   in number,
  FK_MATGRUPPE  in number,
  FK_SUPPLIER in number
) return tUmsatzResultType pipelined
is
    TYPE EmpCurTyp IS REF CURSOR;
    emp_cv EmpCurTyp;
    l_rec  rUmsatzResultType;
    temptable tUmsatzResultType;
    tempquery varchar2(1000);
  
begin
   tempquery := 
     'SELECT ID, FK_ABTEILUNG, FK_MATGRUPPE, FK_SUPPLIER, BUCHUNGSDATUM, BETRAG, FK_REF_UMSATZTYP FROM DIETRICF.ST_TUMSATZ WHERE FK_ABTEILUNG=' || TO_CHAR( FK_ABTEILUNG ) ||
     ' AND FK_MATGRUPPE=' || TO_CHAR( FK_MATGRUPPE )  ||
     ' AND FK_SUPPLIER=' || TO_CHAR( FK_SUPPLIER ) ;
     
    open emp_cv for tempquery;
    loop
      fetch emp_cv into l_rec;
      exit when (emp_cv%notfound);
      pipe row( rUmsatzResultType( l_rec.ID, l_rec.FK_ABTEILUNG, l_rec.FK_MATGRUPPE,
          l_rec.FK_SUPPLIER, l_rec.BUCHUNGSDATUM, l_rec.BETRAG, l_rec.FK_REF_UMSATZTYP ) );
    end loop;

  return;
  
end GetUmsatzSpendResult;

*******************************************************************************


GRANT EXECUTE ON DIETRICF.GetUmsatzSpendResult TO DIETRICF;


*******************************************************************************


select * from TABLE(GETUMSATZSPENDRESULT( 30, 10, 4 ) );


>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><
ORA-00932: Inkonsistente Datentypen: - erwartet, - erhalten
ORA-06512: in "DIETRICF.GETUMSATZSPENDRESULT", Zeile 21
00932. 00000 -  "inconsistent datatypes: expected %s got %s"
*Cause:    
*Action:





