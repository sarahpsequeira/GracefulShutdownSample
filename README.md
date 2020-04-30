GracefulShutdownSample application explains and reproduces the problem in the spring graceful shutdown (https://github.com/spring-projects/spring-boot/issues/20935)

# Steps to run the application:
1) Clone the GracefulShutdownSample repository
2) Create a topic in AWS SNS
3) Build the application to create the jar
```mvn clean package```
4) Go to the target folder get the jar name and run the application 
5) Make sure you have add the topic name, aws region and aws accountId in the command line parameters as follows:

   -- cloud.aws.sns.topic.topicName // Topic which you created in the Aws SNS, e.g. topicName 
   
   -- cloud.aws.region.static // Region of your aws topic, e.g. us-west-2
   
   -- cloud.aws.credentials.accountId //your aws accountId 
   ```java -Dcloud.aws.sns.topic.topicName=<topicName> -Dcloud.aws.region.static=<aws_region> -Dcloud.aws.credentials.accountId=<accountId> -jar demo-0.0.1-SNAPSHOT.jar```
   
6) Do the following curl: 
  ```curl -ik -X GET "http://localhots:8081/api/users/lastName/Doe```
7) Observe that the request is completed successfully and message is posted to the aws topic you have mentioned.

# Steps to reproduce:
1) Start the application by running the following command:

  `java -Dcloud.aws.sns.topic.topicName=<topicName> -Dcloud.aws.region.static=<aws_region> -Dcloud.aws.credentials.accountId=<accountId> -jar demo-0.0.1-SNAPSHOT.jar`
   
2) Do the following curl command:
  ```curl -ik -X GET "http://localhots:8081/api/users/lastName/Doe```
3) Before the curl command gets complete issue a SIGTERM `(ctrl + c)`
4) Notice that though the request is completed it does not publish message to the topic and error is thrown
