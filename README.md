# Javabank - A Java Banking Application

Javabank is a comprehensive Java banking application that offers a robust set of features for managing customers' financial accounts. With support for multiple customers, checking and savings accounts, and secure transaction handling, Javabank simplifies financial management for both individuals and developers seeking to integrate banking functionality into their applications.  
Javabank adopts the MVC (Model-View-Controller) architecture with a service layer for abstracting the domain model to the controllers, ensuring a clean and organized separation of concerns in the application's design.

## **Key Features:**

**Customer-Centric:** Javabank allows for an unlimited number of customers, each with the flexibility to have multiple checking and savings accounts.  
**Account Management:** Ensure that account balances never go negative, as transactions are designed to fail if there are insufficient funds.  
**Savings Account:** Savings accounts come with a minimum balance requirement, and no debits or transfers can be made from these accounts if they fall below this limit.  
**Transaction Handling:** Customers can easily deposit, withdraw, or transfer funds between their checking and savings accounts, promoting financial flexibility.  
**Balance Sum:** Customers can view their total balance by aggregating the balances of all their accounts.  
**Recipient Management:** Customers have the ability to maintain a list of recipients, streamlining the process of transferring money.  
**Bank Overview:** Banks can oversee all customer accounts and view the combined balance across all customers.  

## **Persistence Notes:**

Establishes a one-to-many bi-directional relationship between Customer and Account.  


## **Technologies:**

- Maven for build and dependency management;
- Spring Framework for web and transaction support;
- Spring Boot for rapid development and efficient deployment;
- Hibernate for object-relational mapping;
- MySQL databases for data storage.
