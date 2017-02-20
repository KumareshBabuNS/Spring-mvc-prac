
# CSYE 6250 Web development tools and methods #

<br/>


## Prerequisites ##
* tomcat 8.0.*
* java 1.8
* mysql 5.7.*
* DBUtils  v1.6 (https://commons.apache.org/proper/commons-dbutils/download_dbutils.cgi)
* DBCP v2.1 (https://commons.apache.org/proper/commons-dbcp/download_dbcp.cgi)
* Commons Pool v2.4 (http://commons.apache.org/proper/commons-pool/download_pool.cgi)
* Commons Email v1.4 (https://commons.apache.org/proper/commons-email/download_email.cgi)
* Javax Mail v1.4 (https://mvnrepository.com/artifact/javax.mail/mail/1.4.7)

## Description ##

This is a simple Spring MVC messaging application to enable a user to sign up using an email, login using the registered email and 
view all new messages. User can reply to any message in inbox. An Email is trigged on registering and replying using Java Mail API. 
The application uses mySql Database hosted on localhost and connected via Apache Commons DBCP API.
