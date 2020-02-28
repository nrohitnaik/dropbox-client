# Dropbox-Client

Dropbox client is a command line app which accepts several command line arguments. It is build using Spring boot 2.2.4.RELEASE and Java 11.
 * [Rohit Naik] -Author 
 * [git-repo-url] - Source 
 * [dropbox] - Dropbox developer documentation 
 
##### Prerequisite: app key and secret which can be obtained using
[dropbox-developer-console]


# Features!

  - auth - authenticates and authorizes the access to Dropbox account
  - info - retrieves and prints user's account information  
  - list - prints files and folders information for specified path
  
### Auth
```sh
java -jar dropbox-client.jar auth {appKey} {appSecret}
```
- auth - the command name (mandatory)
- {appKey} - the Dropbox application key (mandatory)
- {appSecret} - the Dropbox application secret code (mandatory)

#### Info
```sh
java -jar dropbox-client.jar info {accessToken} {locale}
```
- info - the command name (mandatory)
- {accessToken} - the access token, which could be generated using auth command (mandatory) 
- {locale} - the users locale, see - specification (optional)

### List##
```sh
java -jar dropbox-client.jar list {accessToken} {path} {locale}
```
- info - the command name (mandatory)
- {accessToken} - the access token, which could be generated using auth command (mandatory) 
- {path} - the path to a file or folder to list details about (mandatory)
- {locale} - the users locale, see specification (optional)



### Tech

Lab Inventory Service uses a number of open source projects to work properly:

* [Spring Boot] - Spring Boot makes it easy to create stand-alone production ready applciations.
* [Lombok] - is a java library that allows to avoid repetitive codes like getters and setter.
* [Maven] - Project management and build.

### Running the application

This application requires [Java 11] to run.

From the command line 

```sh
java -jar dropbox-client.jar {param1} {param2} {param3}
```











 
  
   [git-repo-url]: <https://github.com/nrohitnaik/dropbox-client.git>
   [Rohit Naik]: <https://github.com/nrohitnaik>
   [spring boot]: <https://spring.io/projects/spring-boot>
   [Java 11]: <https://openjdk.java.net/projects/jdk/11/>
   [Lombok]: <https://projectlombok.org/>
   [Maven]: <https://maven.apache.org/>
   [dropbox]: <https://www.dropbox.com/developers/documentation?_tk=pilot_lp&_ad=topbar1&_camp=docs>
   [dropbox-developer-console]: <https://www.dropbox.com/developers/apps?_tk=pilot_lp&_ad=topbar4&_camp=myapps>
 

  
