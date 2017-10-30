# pollingtest
Polling test assignment project to show as job assessment.

Technical Assignment #1.1 - Java BackEnd - Middle/Senior

Design and implement in (java) a service monitoring class. This monitor will be used to monitor the status of multiple services. A service is defined as a host/port combination. To check if a service is up, the monitor will establish a TCP connection to the host on the specified port. If a connection is established, the service is up, if the connection is refused, the service is not up.

The monitor will allow callers to register interest in a service, and a polling frequency. The callers will be notified when the service goes up and down.

The monitor should detect multiple callers registering interest in the same service, and should not poll any service more frequently than once a second.

At any time a service can be configured with a planned service outage; however, not all services need to specify an outage. The service outage will specify a start and end time for which no notifications for that service will be delivered.

The monitor should allow callers to define a grace time.  If a service is not responding, the monitor will wait for the grace time to expire before notifying any clients.  If the service goes back on line during this grace time, no notification will be sent.  If the grace time is less than the polling frequency, the monitor should schedule extra checks of the service.

 
The code should include a set of unit tests, and you are advised to think about implementing good coding practices, design patterns, OOP concepts, and other high-level elements to show your skills as a senior. 


Candidates are provided seven(7) days to complete this test assignment, and should be delivered via GitHub.

clientService collection
{
    "serviceName": "localServiceTest",
    "host": "localhost", // composite primary key
    "port": 8888, // composite primary key
    "upStatus": true,
    "outage": { // optional, nullable
    		"startTime": ISODate("2017-10-22T06:00:00Z"), // UTC timezone
    		"endTime": ISODate("2017-10-23T06:00:00Z")
	},
    "callerConfigs": [
        {
            "callerId": 1,
            "pollingFrequency": 5, // in seconds && 1sec <= x and active till upStatus = true and can be updated to upStatus = false
            "nextPoll": 3, // in seconds, on create nextPoll = pollingFrequency
            "notifyEmail": ["test@gmail.com"],
            "graceTime": 2, // in seconds & extra run if less than pollingFrequency and active from upStatus = false and can be updated to upStatus = true or email
            "graceTimeExpiration": 1 // in seconds & if upStatus = false, extra run will be executed and if no service then email
        },
        {
            "callerId": 2,
            "pollingFrequency": 3,
            "nextPoll": 2,
            "notifyEmail": ["test@gmail.com"],
            "graceTime": 2,
            "graceTimeExpiration": 1
        }
    ]
}

caller collection
{
	"_id" : 1,
	"username" : "user1",
	"password" : "1234",
	"callerName" : "firstName1 lastName1"
}
{
	"_id" : 2,
	"username" : "user2",
	"password" : "1234",
	"callerName" : "firstName2 lastName2"
}

Java: JDK-1.8_144
MongoDB 3.4
Maven 3.0.3
No security concerns are addressed such as db user/pass, app authentication and authorization
mongod --dbpath=/Users/tikka/Installs/mongodb-osx-x86_64-3.4.9/data/db --auth
change application.properties configs

db commands:

mongo 127.0.0.1:27017/monitoring_db

db.caller.createIndex( { username: 1, password: 1 }, { unique: true } );
db.clientService.createIndex( { host: 1, port: 1 }, { unique: true } );
db.clientService.createIndex( { host: 1, port: 1, 'callerConfigs.callerId': 1 }, { unique: true } );

db.caller.insert({"_id" : ObjectId("59ecea6a80d63052a7491a81"),"username" : "user1","password" : "1234","callerName" : "firstName1 lastName1"});
db.caller.insert({"_id" : ObjectId("59ecea6b80d63052a7491a82"),"username" : "user2","password" : "1234","callerName" : "firstName2 lastName2"});

db.clientService.insert({"_id":ObjectId("59eceb7080d63052a7491a83"),"serviceName":"localServiceTest","host":"localhost","port":8888,"upStatus":true,"outage": {"startTime": ISODate("2017-10-22T06:00:00Z"),"endTime": ISODate("2017-10-23T06:00:00Z")},"callerConfigs":[{"callerId":"59ecea6a80d63052a7491a81","pollingFrequency":5,"nextPoll":3,"notifyEmail":["senanayakachinthaka@gmail.com"],"graceTime":2,"graceTimeExpiration":1},{"callerId":"59ecea6b80d63052a7491a82","pollingFrequency":3,"nextPoll":2,"notifyEmail":["senanayakachinthaka@gmail.com"],"graceTime":2,"graceTimeExpiration":1}]});

db.clientService.insert({"_id":ObjectId("59eceb7080d63052a7491a84"),"serviceName":"localServiceTest","host":"localhost","port":8889,"upStatus":true,"outage": {"startTime": ISODate("2017-10-27T06:00:00Z"),"endTime": ISODate("2017-10-28T06:00:00Z")},"callerConfigs":[{"callerId":"59ecea6a80d63052a7491a81","pollingFrequency":5,"nextPoll":3,"notifyEmail":["senanayakachinthaka@gmail.com"],"graceTime":2,"graceTimeExpiration":1},{"callerId":"59ecea6b80d63052a7491a82","pollingFrequency":3,"nextPoll":2,"notifyEmail":["senanayakachinthaka@gmail.com"],"graceTime":2,"graceTimeExpiration":1}]});

build:
mvn clean install

run:
java -agentlib:jdwp=transport=dt_socket,server=y,address=8000,suspend=n -jar pollingtest-1.0.0.0-SNAPSHOT.jar

TODO:
1. Unit testing
2. Code comments: class level, method level and interfaces
3. Fix readme file with contracts

Assumptions:
1. Developing a UI is not in the requirements, thus only fully functional API is developed.
2. No security aspects of this application are addressed.
3. Only up to desired level of design patterns and performance concerns addressed because this is only a recruitment assignment and cannot address all of the concerns (such as perf concerns) within given deadline.