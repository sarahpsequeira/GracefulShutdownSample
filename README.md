GracefulShutdownSample application explains and reproduces the problem in the spring graceful shutdown (https://github.com/spring-projects/spring-boot/issues/20935)

### Steps to run the application:
1) Clone the GracefulShutdownSample repository
2) Create a topic in AWS SNS
3) Build the application to create the jar.  
```mvn clean package```
4) Go to the target folder get the jar name and run the application 
5) Make sure you have added the topic name, aws region and aws accountId in the command line parameters as follows:  
   -- cloud.aws.sns.topic.topicName // Topic which you created in the Aws SNS, e.g. topicName   
   -- cloud.aws.region.static // Region of your aws topic, e.g. us-west-2.  
   -- cloud.aws.credentials.accountId //your aws accountId.   
   
   ```java -Dcloud.aws.sns.topic.topicName=<topicName> -Dcloud.aws.region.static=<aws_region> -Dcloud.aws.credentials.accountId=<accountId> -jar demo-0.0.1-SNAPSHOT.jar```  
   
6) Do the following curl: 
  ```curl -ik -X GET "http://localhots:8081/api/users/lastName/Doe```  
7) Observe that the request is completed successfully and message is posted to the aws topic you have mentioned.

### Steps to reproduce:
1) Start the application by running the following command:  

  `java -Dcloud.aws.sns.topic.topicName=<topicName> -Dcloud.aws.region.static=<aws_region> -Dcloud.aws.credentials.accountId=<accountId> -jar demo-0.0.1-SNAPSHOT.jar`
  
2) Do the following curl command:  

  ```curl -ik -X GET "http://localhots:8081/api/users/lastName/Doe```  

3) Before the curl command gets complete issue a SIGTERM `(ctrl + c)`.  
 #### Expected Result
 Request is completed and message is published to the topic. You can see the success log here [graceful_shutdown_happy_path.log](https://github.com/sarahpsequeira/GracefulShutdownSample/blob/master/logs/graceful_shutdown_happy_path.log)

 #### Actual Result
 Request is completed but message is not published to topic. You can see the failure log here [graceful_shutdown_error_path.log](https://github.com/sarahpsequeira/GracefulShutdownSample/blob/master/logs/graceful_shutdown_error_path.log)
 ```
 2020-04-30 10:39:32.658  INFO 5515 --- [extShutdownHook] o.s.b.w.e.tomcat.TomcatGracefulShutdown  : Graceful shutdown complete
2020-04-30 10:39:32.676 DEBUG 5515 --- [nnection-reaper] com.amazonaws.http.IdleConnectionReaper  : Reaper thread: 

java.lang.InterruptedException: sleep interrupted
	at java.lang.Thread.sleep(Native Method) ~[na:1.8.0_161]
	at com.amazonaws.http.IdleConnectionReaper.run(IdleConnectionReaper.java:188) ~[aws-java-sdk-core-1.11.415.jar!/:na]

2020-04-30 10:39:32.676 DEBUG 5515 --- [nnection-reaper] com.amazonaws.http.IdleConnectionReaper  : Shutting down reaper thread.
2020-04-30 10:39:32.677 DEBUG 5515 --- [ceful-Startup-2] c.a.h.c.ClientConnectionManagerFactory   : 

java.lang.reflect.InvocationTargetException: null
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_161]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_161]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_161]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[na:1.8.0_161]
	at com.amazonaws.http.conn.ClientConnectionManagerFactory$Handler.invoke(ClientConnectionManagerFactory.java:76) ~[aws-java-sdk-core-1.11.415.jar!/:na]
	at com.amazonaws.http.conn.$Proxy87.connect(Unknown Source) [na:na]
	at org.apache.http.impl.execchain.MainClientExec.establishRoute(MainClientExec.java:393) [httpclient-4.5.12.jar!/:4.5.12]
	at org.apache.http.impl.execchain.MainClientExec.execute(MainClientExec.java:236) [httpclient-4.5.12.jar!/:4.5.12]
	at org.apache.http.impl.execchain.ProtocolExec.execute(ProtocolExec.java:186) [httpclient-4.5.12.jar!/:4.5.12]
	at org.apache.http.impl.client.InternalHttpClient.doExecute(InternalHttpClient.java:185) [httpclient-4.5.12.jar!/:4.5.12]
	at org.apache.http.impl.client.CloseableHttpClient.execute(CloseableHttpClient.java:83) [httpclient-4.5.12.jar!/:4.5.12]
	at org.apache.http.impl.client.CloseableHttpClient.execute(CloseableHttpClient.java:56) [httpclient-4.5.12.jar!/:4.5.12]
	at com.amazonaws.http.apache.client.impl.SdkHttpClient.execute(SdkHttpClient.java:72) [aws-java-sdk-core-1.11.415.jar!/:na]
	at com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeOneRequest(AmazonHttpClient.java:1258) [aws-java-sdk-core-1.11.415.jar!/:na]
	at com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeHelper(AmazonHttpClient.java:1074) [aws-java-sdk-core-1.11.415.jar!/:na]
	at com.amazonaws.http.AmazonHttpClient$RequestExecutor.doExecute(AmazonHttpClient.java:745) [aws-java-sdk-core-1.11.415.jar!/:na]
	at com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeWithTimer(AmazonHttpClient.java:719) [aws-java-sdk-core-1.11.415.jar!/:na]
	at com.amazonaws.http.AmazonHttpClient$RequestExecutor.execute(AmazonHttpClient.java:701) [aws-java-sdk-core-1.11.415.jar!/:na]
	at com.amazonaws.http.AmazonHttpClient$RequestExecutor.access$500(AmazonHttpClient.java:669) [aws-java-sdk-core-1.11.415.jar!/:na]
	at com.amazonaws.http.AmazonHttpClient$RequestExecutionBuilderImpl.execute(AmazonHttpClient.java:651) [aws-java-sdk-core-1.11.415.jar!/:na]
	at com.amazonaws.http.AmazonHttpClient.execute(AmazonHttpClient.java:515) [aws-java-sdk-core-1.11.415.jar!/:na]
	at com.amazonaws.services.sns.AmazonSNSClient.doInvoke(AmazonSNSClient.java:2488) [aws-java-sdk-sns-1.11.415.jar!/:na]
	at com.amazonaws.services.sns.AmazonSNSClient.invoke(AmazonSNSClient.java:2457) [aws-java-sdk-sns-1.11.415.jar!/:na]
	at com.amazonaws.services.sns.AmazonSNSClient.invoke(AmazonSNSClient.java:2446) [aws-java-sdk-sns-1.11.415.jar!/:na]
	at com.amazonaws.services.sns.AmazonSNSClient.executePublish(AmazonSNSClient.java:1848) [aws-java-sdk-sns-1.11.415.jar!/:na]
	at com.amazonaws.services.sns.AmazonSNSClient.publish(AmazonSNSClient.java:1820) [aws-java-sdk-sns-1.11.415.jar!/:na]
	at org.springframework.cloud.aws.messaging.core.TopicMessageChannel.sendInternal(TopicMessageChannel.java:69) [spring-cloud-aws-messaging-2.2.1.RELEASE.jar!/:2.2.1.RELEASE]
	at org.springframework.messaging.support.AbstractMessageChannel.send(AbstractMessageChannel.java:136) [spring-messaging-5.2.5.RELEASE.jar!/:5.2.5.RELEASE]
	at org.springframework.messaging.support.AbstractMessageChannel.send(AbstractMessageChannel.java:122) [spring-messaging-5.2.5.RELEASE.jar!/:5.2.5.RELEASE]
	at org.springframework.cloud.aws.messaging.core.support.AbstractMessageChannelMessagingSendingTemplate.doSend(AbstractMessageChannelMessagingSendingTemplate.java:67) [spring-cloud-aws-messaging-2.2.1.RELEASE.jar!/:2.2.1.RELEASE]
	at org.springframework.cloud.aws.messaging.core.support.AbstractMessageChannelMessagingSendingTemplate.doSend(AbstractMessageChannelMessagingSendingTemplate.java:44) [spring-cloud-aws-messaging-2.2.1.RELEASE.jar!/:2.2.1.RELEASE]
	at org.springframework.messaging.core.AbstractMessageSendingTemplate.send(AbstractMessageSendingTemplate.java:109) [spring-messaging-5.2.5.RELEASE.jar!/:5.2.5.RELEASE]
	at org.springframework.messaging.core.AbstractMessageSendingTemplate.convertAndSend(AbstractMessageSendingTemplate.java:151) [spring-messaging-5.2.5.RELEASE.jar!/:5.2.5.RELEASE]
	at org.springframework.messaging.core.AbstractMessageSendingTemplate.convertAndSend(AbstractMessageSendingTemplate.java:129) [spring-messaging-5.2.5.RELEASE.jar!/:5.2.5.RELEASE]
	at org.springframework.cloud.aws.messaging.core.support.AbstractMessageChannelMessagingSendingTemplate.convertAndSend(AbstractMessageChannelMessagingSendingTemplate.java:88) [spring-cloud-aws-messaging-2.2.1.RELEASE.jar!/:2.2.1.RELEASE]
	at org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate.sendNotification(NotificationMessagingTemplate.java:79) [spring-cloud-aws-messaging-2.2.1.RELEASE.jar!/:2.2.1.RELEASE]
	at com.sample.application.demo.event.UserAccessedEventListener.publishMessageToAWS(UserAccessedEventListener.java:37) [classes!/:0.0.1-SNAPSHOT]
	at com.sample.application.demo.event.UserAccessedEventListener.onApplicationEvent(UserAccessedEventListener.java:31) [classes!/:0.0.1-SNAPSHOT]
	at com.sample.application.demo.event.UserAccessedEventListener.onApplicationEvent(UserAccessedEventListener.java:13) [classes!/:0.0.1-SNAPSHOT]
	at org.springframework.context.event.SimpleApplicationEventMulticaster.doInvokeListener(SimpleApplicationEventMulticaster.java:172) [spring-context-5.2.5.RELEASE.jar!/:5.2.5.RELEASE]
	at org.springframework.context.event.SimpleApplicationEventMulticaster.invokeListener(SimpleApplicationEventMulticaster.java:165) [spring-context-5.2.5.RELEASE.jar!/:5.2.5.RELEASE]
	at org.springframework.context.event.SimpleApplicationEventMulticaster.multicastEvent(SimpleApplicationEventMulticaster.java:139) [spring-context-5.2.5.RELEASE.jar!/:5.2.5.RELEASE]
	at org.springframework.context.support.AbstractApplicationContext.publishEvent(AbstractApplicationContext.java:403) [spring-context-5.2.5.RELEASE.jar!/:5.2.5.RELEASE]
	at org.springframework.context.support.AbstractApplicationContext.publishEvent(AbstractApplicationContext.java:360) [spring-context-5.2.5.RELEASE.jar!/:5.2.5.RELEASE]
	at com.sample.application.demo.service.AuditLogService.publishMessage(AuditLogService.java:26) [classes!/:0.0.1-SNAPSHOT]
	at com.sample.application.demo.service.AuditLogService$$FastClassBySpringCGLIB$$1e3955dd.invoke(<generated>) [classes!/:0.0.1-SNAPSHOT]
	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218) [spring-core-5.2.5.RELEASE.jar!/:5.2.5.RELEASE]
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:771) [spring-aop-5.2.5.RELEASE.jar!/:5.2.5.RELEASE]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163) [spring-aop-5.2.5.RELEASE.jar!/:5.2.5.RELEASE]
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749) [spring-aop-5.2.5.RELEASE.jar!/:5.2.5.RELEASE]
	at org.springframework.aop.interceptor.AsyncExecutionInterceptor.lambda$invoke$0(AsyncExecutionInterceptor.java:115) [spring-aop-5.2.5.RELEASE.jar!/:5.2.5.RELEASE]
	at java.util.concurrent.FutureTask.run(FutureTask.java:266) ~[na:1.8.0_161]
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149) ~[na:1.8.0_161]
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624) ~[na:1.8.0_161]
	at java.lang.Thread.run(Thread.java:748) ~[na:1.8.0_161]
Caused by: java.net.SocketException: Socket closed
	at java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:354) ~[na:1.8.0_161]
	at java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:206) ~[na:1.8.0_161]
	at java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:188) ~[na:1.8.0_161]
	at java.net.SocksSocketImpl.connect(SocksSocketImpl.java:392) ~[na:1.8.0_161]
	at java.net.Socket.connect(Socket.java:589) ~[na:1.8.0_161]
	at org.apache.http.conn.ssl.SSLConnectionSocketFactory.connectSocket(SSLConnectionSocketFactory.java:368) ~[httpclient-4.5.12.jar!/:4.5.12]
	at com.amazonaws.http.conn.ssl.SdkTLSSocketFactory.connectSocket(SdkTLSSocketFactory.java:142) ~[aws-java-sdk-core-1.11.415.jar!/:na]
	at org.apache.http.impl.conn.DefaultHttpClientConnectionOperator.connect(DefaultHttpClientConnectionOperator.java:142) ~[httpclient-4.5.12.jar!/:4.5.12]
	at org.apache.http.impl.conn.PoolingHttpClientConnectionManager.connect(PoolingHttpClientConnectionManager.java:376) ~[httpclient-4.5.12.jar!/:4.5.12]
	... 55 common frames omitted

2020-04-30 10:39:32.686 DEBUG 5515 --- [ceful-Startup-2] com.amazonaws.request                    : Retrying Request: POST https://sns.us-west-2.amazonaws.com / Parameters: ({"Action":["Publish"],"Version":["2010-03-31"],"TopicArn":["arn:aws:sns:us-west-2:670055277015:outliers-1-case"],"Message":["{\"source\":{},\"timestamp\":1588268372622,\"message\":\"Test users have been accessed\"}"],"Subject":["Users have been accessed"],"MessageAttributes.entry.1.Name":["NOTIFICATION_SUBJECT_HEADER"],"MessageAttributes.entry.1.Value.DataType":["String"],"MessageAttributes.entry.1.Value.StringValue":["Users have been accessed"],"MessageAttributes.entry.2.Name":["id"],"MessageAttributes.entry.2.Value.DataType":["String"],"MessageAttributes.entry.2.Value.StringValue":["d5846673-3188-4c9a-a3fa-9bf684a8a14c"],"MessageAttributes.entry.3.Name":["contentType"],"MessageAttributes.entry.3.Value.DataType":["String"],"MessageAttributes.entry.3.Value.StringValue":["application/json"],"MessageAttributes.entry.4.Name":["timestamp"],"MessageAttributes.entry.4.Value.DataType":["Number.java.lang.Long"],"MessageAttributes.entry.4.Value.StringValue":["1588268372622"]}Headers: (User-Agent: aws-sdk-java/1.11.415 Mac_OS_X/10.15.3 Java_HotSpot(TM)_64-Bit_Server_VM/25.161-b12 java/1.8.0_161, amz-sdk-invocation-id: caf0044b-537f-9dd4-7de4-2fb6a0fe5f85, ) 
 ```
 
