# Bank Transfer Restful API
This is a simple Restful API services project provisioned to funds transfer transactions between accounts.

Used the spring boot framework, lombok for getter/ setters, hibernate ORM with H2 in memory database to manage data, Springfox for Swagger2. 
Externalized the account data to a pipe (|) delimited file and application loads it at the time of bootstrap to the in-memory H2 database. For your ease, the accounts.txt has been placed under /resources folder. 

* [BankTransfer Restful API Swagger UI](http://localhost:8080/swagger-ui.html/)


### Bank Transfer API endpoints are: 
http://localhost:8080/bankTransfer-api/getAllAccounts

http://localhost:8080/bankTransfer-api/allTransactions

http://localhost:8080/bankTransfer-api/performTransfer
