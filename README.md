# CRDCodingTest
CRD JAVA Coding TEST

This a core JAVA program for updating the Holding Table by buying & selling the stock and insert every transaction record in ORD table 
on the basis of a entries in the Model Table. PostgreSQL database has been used for storing records of Holding, Model and ORD table

DBScripts folder contains the db, user and table creation scripts along with schema and data insertion scripts.

The program starts with Main class. 
First, it initializes the resources based on entries in application.properties file. Then it creates an instance of dbOperations and portfolioOperations.

After that it creates database connection and retreive the total amount in Holding table. Then it fetches the details of all the stocks in the Holding and Model table along with holding Percentage of each stock by performing left and right outer join on these tables. It then store the each record in a list of HashMap.

For each record in the list, Main fetches the values of amt, stock, model percent & holding percent from Map, it checks the difference in Model percent and Holding percent to decide whether to buy the stock or sell it.

if the difference is positive, then it has to sell some percent of stock based on the difference in the amount and if the whole amount is sold then it needs to remove the entry of the stock from Holding Table.

if the difference us negative, then it has to purchase some percent of stock and if the holding amount is zero that means it is a new stock (present in Model Table) so it has to be newly added in the Holding Table based on the model percent.

Since the buying and selling of the stocks performs database operations on more than one table, we need to ensure that Atomicity is maintained so for that connection.setAutoCommit() is set to FALSE and connection.commit() is called at the end of buyStock() and sellStock() methods and connection.rollback() is called in the catch block if some failure occurs in between
