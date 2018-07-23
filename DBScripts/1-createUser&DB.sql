 -- WINDOWS PSQL CONFIGURATION SCRIPT
 
 
 --CREATE ROLE
 CREATE USER postgres with PASSWORD 'postgres' ;
 
 -- CREATE DATABASE
 CREATE database crddb ;
 
 --GRANT PRIVILEGE
 grant all privileges on database crdb to postgres ;
 