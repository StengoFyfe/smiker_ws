-- set SERVERoutput on
DECLARE
/*
  TYPE DatesTyp IS RECORD (
      Buchungsdatum date := null );
      */
      
   -- Datum Werte
   TYPE DatesTable IS TABLE OF date
      INDEX BY BINARY_INTEGER;
  
  datetab DatesTable;
  
   -- Betrag Werte
   TYPE BetragTableType IS TABLE OF NUMBER(19,4)
      INDEX BY BINARY_INTEGER;
      
  betragtab BetragTableType;
  
  -- Faktoren für Beträge
  TYPE FaktorTableType IS TABLE OF FLOAT
      INDEX BY BINARY_INTEGER;
  faktortab FaktorTableType;      
      
  -- Target Array
  TYPE TargetType IS TABLE OF ST_TUMSATZ%ROWTYPE;
  targettab TargetType;
  
  rTarget ST_TUMSATZ%ROWTYPE;

  


    counter int := 1;
    lAbteilungID  NUMBER(10,0) := 0;
    lMaterialgruppeID NUMBER(10,0) := 0;
    lSupplierID NUMBER(10,0) := 0;
    
    lTempBaseID NUMBER(10,0) := 0;
    TempBetrag NUMBER (19,4) := 0;
    
    outputline    varchar2(255) := '';


    CURSOR ABTCur
    IS SELECT ID FROM ST_TABTEILUNG ORDER BY ID ASC;
    
    CURSOR MGCur
    IS SELECT ID FROM ST_TMATGRUPPE;
    
    CURSOR SUCur
    IS SELECT ID FROM ST_TSUPPLIER;

     
BEGIN
      dbms_output.put_line('Start');
      

    -- init the temp tables
    datetab(1) := TO_DATE('2003/01/09', 'yyyy/mm/dd');
    datetab(2) := TO_DATE('2004/03/12', 'yyyy/mm/dd');
    datetab(3) := TO_DATE('2004/04/15', 'yyyy/mm/dd');
    datetab(4) := TO_DATE('2005/06/18', 'yyyy/mm/dd');
    datetab(5) := TO_DATE('2005/07/21', 'yyyy/mm/dd');
    datetab(6) := TO_DATE('2006/08/24', 'yyyy/mm/dd');
    datetab(7) := TO_DATE('2006/09/27', 'yyyy/mm/dd');
    datetab(8) := TO_DATE('2007/10/30', 'yyyy/mm/dd');
    datetab(9) := TO_DATE('2007/11/02', 'yyyy/mm/dd');
    datetab(10) := TO_DATE('2008/12/05', 'yyyy/mm/dd');
    datetab(11) := TO_DATE('2008/01/08', 'yyyy/mm/dd');
    datetab(12) := TO_DATE('2008/02/11', 'yyyy/mm/dd');
    datetab(13) := TO_DATE('2009/04/14', 'yyyy/mm/dd');
    datetab(14) := TO_DATE('2009/05/17', 'yyyy/mm/dd');
    datetab(15) := TO_DATE('2009/07/20', 'yyyy/mm/dd');
    datetab(16) := TO_DATE('2009/08/23', 'yyyy/mm/dd');
    datetab(17) := TO_DATE('2010/08/26', 'yyyy/mm/dd');
    datetab(18) := TO_DATE('2010/09/29', 'yyyy/mm/dd');
    datetab(19) := TO_DATE('2010/10/01', 'yyyy/mm/dd');
    datetab(20) := TO_DATE('2011/11/04', 'yyyy/mm/dd');
    datetab(21) := TO_DATE('2011/12/07', 'yyyy/mm/dd');
    datetab(22) := TO_DATE('2012/01/10', 'yyyy/mm/dd');
    datetab(23) := TO_DATE('2012/03/13', 'yyyy/mm/dd');
    datetab(24) := TO_DATE('2012/05/16', 'yyyy/mm/dd');
    datetab(25) := TO_DATE('2013/06/19', 'yyyy/mm/dd');
    datetab(26) := TO_DATE('2013/08/22', 'yyyy/mm/dd');
    datetab(27) := TO_DATE('2013/10/25', 'yyyy/mm/dd');
    datetab(28) := TO_DATE('2013/11/28', 'yyyy/mm/dd');
    datetab(29) := TO_DATE('2013/12/31', 'yyyy/mm/dd');
    datetab(30) := TO_DATE('2013/02/03', 'yyyy/mm/dd');

    betragtab(1) := 570;
    betragtab(2) := 590;
    betragtab(3) := 600;
    betragtab(4) := 550;
    betragtab(5) := 610;
    betragtab(6) := 650;
    betragtab(7) := 580;
    betragtab(8) := 730;
    betragtab(9) := 520;
    betragtab(10) := 670;
    betragtab(11) := 740;
    betragtab(12) := 510;
    betragtab(13) := 630;
    betragtab(14) := 500;
    betragtab(15) := 640;
    betragtab(16) := 530;
    betragtab(17) := 540;
    betragtab(18) := 620;
    betragtab(19) := 750;
    betragtab(20) := 690;
    betragtab(21) := 700;
    betragtab(22) := 710;
    betragtab(23) := 560;
    betragtab(24) := 660;
    betragtab(25) := 720;
    betragtab(26) := 680;
    
    faktortab(1) := 1.01 ;
    faktortab(2) := 1.05 ;
    faktortab(3) := 1.13 ;
    faktortab(4) := 1.35 ;
    faktortab(5) := 1.42 ;
    faktortab(6) := 1.59 ;
    faktortab(7) := 1.87 ;
    
    targettab := targettype();
    
    DELETE FROM ST_TUMSATZ;

      FOR rABT in ABTCur LOOP
        lAbteilungID := rABT.ID;
        
        FOR rMG in MGCur LOOP

          lMaterialGruppeID := rMG.ID;
          
          FOR rSU in SUCur LOOP
            lSupplierID := rSU.ID;
            
            FOR i in 1..500 LOOP
              lTempBaseID := (lAbteilungID * lMaterialGruppeID * i ) / lSupplierID;
              
              
              
              TempBetrag := Betragtab(lTempBaseID MOD Betragtab.LAST +1 ) * faktortab( lTempBaseID MOD faktortab.LAST + 1);
              --dbms_output.put_line( TO_CHAR( TempBetrag ) );
              
              
              rTarget.ID := counter;
              rTarget.FK_ABTEILUNG := lAbteilungID;
              rTarget.FK_MATGRUPPE := lMaterialGruppeID;
              rTarget.FK_SUPPLIER := lSupplierID;
              rTarget.BUCHUNGSDATUM := datetab( lTempBaseID MOD datetab.LAST + 1);
              rTarget.BETRAG := TempBetrag;
              rTarget.FK_REF_UMSATZTYP := (counter MOD 2 + 1);
                     
              targettab.extend;      
              targettab(counter) := rTarget;
              
              counter := counter +1;
              
            END LOOP;
          END LOOP;
        END LOOP;
      END LOOP;
      
      for g in 1..targettab.LAST loop
        counter := 1;        
        INSERT INTO ST_TUMSATZ VALUES targettab(g);
      END LOOP;
        
   
    dbms_output.put_line( counter );
    
   datetab.delete;
   targettab.delete;
   COMMIT;
END; 

/*
CREATE GLOBAL TEMPORARY TABLE Dates
        (startdate DATE
) ON COMMIT DELETE ROWS;

CREATE GLOBAL TEMPORARY TABLE Betraege
        (betrag number(19,4)
) ON COMMIT DELETE ROWS;

CREATE GLOBAL TEMPORARY TABLE Faktoren
      (faktor number(1,3)
) ON COMMIT DELETE ROWS;

*/

