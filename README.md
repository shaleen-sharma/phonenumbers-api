# phonenumbers-api
The api performs below operations 
- getAllPhoneNumber(pageNo, pageSize) - Retrieves all the phone numbers in the db with pagination
- getPhoneNumbersByCustomer(userId) - Retrieves all the phone numbers that belong to a customer
- activatePhoneNumber(userId, number) - ACtivates a number that belong to a customer

 #### Usage:

1. git clone the project into your workspace.
2. cd into the directory and perform in the following order of modules:
3. Gradle wrapper is used for convenience so you dont have to install.
```
./gradlew clean build
```
```
./gradlew clean check
```

3. Run Spring boot app using mvn wrapper command
```
./gradlew bootRun
```
4. After the above command is finished and app is started use below postman collection to hit the endpoints.\
Postman collection: See the file Belong-Test.postman_collection.json in the repo


To get a view of the database checkout the below file from the repo. This service is using in memory H2 db.
```
data.sql
```

Further improvements:

- Add Spring Security for token validation
- Migrate endpoints over https
