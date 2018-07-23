 create table HOLDING (
 	SEC VARCHAR(10),
    AMT DECIMAL(18,2)
 );
 
 create table MODEL (
 	SEC VARCHAR(10),
    PERCEN DECIMAL(8,3)
 );
 
 create table ORD (
     SEC VARCHAR(10),
     TRANS VARCHAR(1),
     AMT DECIMAL(18,2)
     CHECK (AMT > 0)
  );