 # GracefulShutdownSample
    
GracefulShutdownSample application explains and reproduces the problem in the spring graceful shutdown. On SIGTERM signal, spring should also serves the async task before closing down but in this application we find that async task ( to send message to MQ) is not completed and spring assumes that the task is done.

 - Clone the GracefulShutdownSample repository
 - Create a topic in AWS SNS and subscribe a queue to that topic
 - Update the topic name and the AWS credentials n the application.yml file.
 - Build the application
 - Go to the target folder get the jar name   and run the application
	 - Run the command "java -Dserver.port=8081 -jar {jar name}    
 - Go to ApiTesting.java and run the test test_getUsersByLastName. We have kept a Thread.sleep() for 5 seconds before it serves the request.
 - Now, issue a s SIGTERM to the application. 
 - You will find that the message wasn't sent to the topic. 
 
