# Spending Tracker Backend
The goal of this small SpringBoot app is to provide a simple REST Api for handling everyday expenses.

The app contains an H2 in memory DB with a couple of example transactions for the better testability. Regarding the functionality:  
- CRUD operations are available
- total amounts spent in last month grouped by categories

Start with:
```bash
.\mvnw spring-boot:start
```