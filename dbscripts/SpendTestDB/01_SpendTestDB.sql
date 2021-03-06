
-- -----------------------------------------
-- Tabelle Standort
-- -----------------------------------------

CREATE TABLE ST_TSTANDORT (
  ID           NUMBER(10)    NOT NULL,
  NAME         VARCHAR2(100)  NOT NULL,
  NUMMER       VARCHAR2(20) NULL
  );

ALTER TABLE ST_TSTANDORT ADD (
  CONSTRAINT STANDORT_PK PRIMARY KEY (ID));
  
CREATE SEQUENCE STANDORT_SEQ;

CREATE OR REPLACE TRIGGER STANDORT_BIR 
BEFORE INSERT ON ST_TSTANDORT 
FOR EACH ROW
WHEN (new.id IS NULL)
BEGIN
  SELECT STANDORT_SEQ.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/





-- -----------------------------------------
-- Tabelle Materialgruppe
-- -----------------------------------------
CREATE TABLE ST_TMATGRUPPE (
  ID           NUMBER(10)    NOT NULL,
  FK_PARENT    NUMBER(10)    NULL,
  MATCODE      VARCHAR2(20)  NULL,
  NAME         VARCHAR2(100) NOT NULL,
  
   CONSTRAINT MATGRUPPE_PK PRIMARY KEY  (ID),
   CONSTRAINT MATGRUPPE_FK_PARENT FOREIGN KEY (FK_PARENT) REFERENCES ST_TMATGRUPPE (ID) ON DELETE CASCADE
   
);






CREATE SEQUENCE MATGRUPPE_SEQ;

CREATE OR REPLACE TRIGGER MATGRUPPE_BIR 
BEFORE INSERT ON ST_TMATGRUPPE 
FOR EACH ROW
WHEN (new.id IS NULL)
BEGIN
  SELECT MATGRUPPE_SEQ.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/

-- -----------------------------------------
-- Tabelle Supplier
-- -----------------------------------------
CREATE TABLE ST_TSUPPLIER (
  ID           NUMBER(10)    NOT NULL,
  FIRMA        VARCHAR(100)  NULL,
  KRED_ID      VARCHAR2(20)  NULL
 );

ALTER TABLE ST_TSUPPLIER ADD (
  CONSTRAINT SUPPLIER_PK PRIMARY KEY (ID));

CREATE SEQUENCE SUPPLIER_SEQ;



CREATE OR REPLACE TRIGGER SUPPLIER_BIR 
BEFORE INSERT ON ST_TSUPPLIER 
FOR EACH ROW
WHEN (new.id IS NULL)
BEGIN
  SELECT SUPPLIER_SEQ.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/


-- -----------------------------------------
-- Tabelle Abteilung
-- -----------------------------------------
CREATE TABLE ST_TABTEILUNG (
  ID           NUMBER(10)    NOT NULL,
  FK_STANDORT  NUMBER(10)    NOT NULL,
  FK_PARENT    NUMBER(10)    NULL,
  ABT_NUMMER   VARCHAR2(20)  NULL,
  NAME         VARCHAR2(100) NOT NULL,
  
   CONSTRAINT ABTEILUNG_PK PRIMARY KEY  (ID),
   CONSTRAINT ABTEILUNG_FK_PARENT FOREIGN KEY (FK_PARENT) REFERENCES ST_TABTEILUNG (ID) ON DELETE CASCADE

 );

ALTER TABLE ST_TABTEILUNG ADD (
   CONSTRAINT ABTEILUNG_FK_STANDORT FOREIGN KEY (FK_STANDORT) REFERENCES ST_TSTANDORT (ID) ON DELETE CASCADE
);


CREATE SEQUENCE ABTEILUNG_SEQ;



CREATE OR REPLACE TRIGGER ABTEILUNG_BIR 
BEFORE INSERT ON ST_TABTEILUNG 
FOR EACH ROW
WHEN (new.id IS NULL)
BEGIN
  SELECT ABTEILUNG_SEQ.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/



-- -----------------------------------------
-- Tabelle Umsatz
-- -----------------------------------------
CREATE TABLE ST_TUMSATZ (
  ID                  NUMBER(10)    NOT NULL,
  FK_ABTEILUNG        NUMBER(10)    NOT NULL,
  FK_MATGRUPPE        NUMBER(10)    NOT NULL,
  FK_SUPPLIER         NUMBER(10)    NOT NULL,
  BUCHUNGSDATUM       TIMESTAMP     NOT NULL,
  BETRAG              NUMBER(19,4)  NOT NULL,
  FK_REF_UMSATZTYP    NUMBER(10)    NOT NULL,
  
   CONSTRAINT UMSATZ_PK PRIMARY KEY  (ID),
   CONSTRAINT UMSATZ_FK_ABTEILUNG FOREIGN KEY (FK_ABTEILUNG) REFERENCES ST_TABTEILUNG (ID) ON DELETE CASCADE,
   CONSTRAINT UMSATZ_FK_MATGRUPPE FOREIGN KEY (FK_MATGRUPPE) REFERENCES ST_TMATGRUPPE (ID) ON DELETE CASCADE,
   CONSTRAINT UMSATZ_FK_SUPPLIER  FOREIGN KEY (FK_SUPPLIER)  REFERENCES ST_TSUPPLIER  (ID) ON DELETE CASCADE
 );


CREATE SEQUENCE UMSATZ_SEQ;



CREATE OR REPLACE TRIGGER UMSATZ_BIR 
BEFORE INSERT ON ST_TUMSATZ 
FOR EACH ROW
WHEN (new.id IS NULL)
BEGIN
  SELECT UMSATZ_SEQ.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/


-- -----------------------------------------
-- Tabelle Umsatztyp
-- -----------------------------------------
CREATE TABLE ST_RUMSATZTYP (
  ID           NUMBER(10)    NOT NULL,
  UMSATZ_TYP   VARCHAR2(20)  NOT NULL
 );

ALTER TABLE ST_RUMSATZTYP ADD (
  CONSTRAINT UMSATZTYP_PK PRIMARY KEY (ID));

CREATE SEQUENCE UMSATZTYP_SEQ;



CREATE OR REPLACE TRIGGER UMSATZTYP_BIR 
BEFORE INSERT ON ST_RUMSATZTYP
FOR EACH ROW
WHEN (new.id IS NULL)
BEGIN
  SELECT UMSATZTYP_SEQ.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/

/*
drop table ST_TSTANDORT
drop table ST_TMATGRUPPE
drop table ST_TSUPPLIER
drop table ST_TABTEILUNG
drop table ST_TUMSATZ
drop table ST_RUMSATZTYP
*/

