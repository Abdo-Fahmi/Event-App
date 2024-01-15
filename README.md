# Event Scheduling/Management App
### This is a simple CRUD application to showcase programming skills in Java core, as well as some JavaFX
Developed using IntelliJ Idea, allows for account registration, deletion, event creation and registration, changing of user information (passwords, emails, etc.). <br/>
All user input is validated and checked before a new account/event is created or modified. <br/>

### The code also makes use of multiple design patterns, such as proxy, observer, flyweight, and iterator:
1- Proxy and Flyweight are used for efficiency when browsing within the app. <br/>
2- Observer is used to update multiple pages within the application. <br/>
3- *Iterator is used to navigate through the collection of events on the browsing page. <br/>

### PHPMyAdmin is used for an interface with the SQL database
With the addition of triggers and stored procedures to deal with race conditions, might change to MySQL later.


*Iterator pattern will soon be removes as the data will no longer be fetched from the database all at once, but I will use the Pagination approach instead.
