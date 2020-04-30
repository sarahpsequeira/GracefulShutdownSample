
# GracefulShutdownSample  
  GracefulShutdownSample application explains and reproduces the problem in the spring graceful shutdown. On SIGTERM signal, spring should also serves the async task before closing down but in this application we find that async task ( to send message to MQ) is not completed and spring assumes that the task is done.  
  
 - Clone the GracefulShutdownSample repository  
 - Create a topic in AWS SNS and subscribe a queue to that topic  
 - Update the topic name and the AWS credentials in the application.yml file.  
 - Build the application to create the jar   
   `mvn clean package`  
 - Go to the target folder get the jar name and run the application after updating the properties  
 --  cloud.aws.sns.topic.topicName // Topic which you created in the Aws SNS, e.g. topicName
 -- cloud.aws.region.static // Region of your aws topic, e.g. us-west-2
 -- cloud.aws.credentials.accountId //your aws accountId
   `java -Dcloud.aws.sns.topic.topicName=<topicName> -Dcloud.aws.region.static=<aws_region> -Dcloud.aws.credentials.accountId=<accountId>  -jar demo-0.0.1-SNAPSHOT.jar`      
- Issue the following cURL request to get the users. We have kept a Thread.sleep() for 5 seconds before it serves the request.  
 - Notice the application log. You will see that message was sent to SNS  
 - Issue the same cURL again to get the list of users.   
 - Now, issue a s SIGTERM to the application.   
 - You will find that the message wasn't sent to the topic.   
   
It is expected that it should serve the async request to send message to MQ. It fails to do so.