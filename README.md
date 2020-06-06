## Legacy Bootifying and converting

Used `curl` commands to exercise the Profile microservice app. Console output shown below:

### Console output

### POST

* Creating a profile

```
iain:~$ curl -v --header "Content-Type: application/json"   --request POST   --data '{ "username": "unamerkel","password": "changeme","firstName": "Una","lastName": "Merkel","email": "unamerkel@example.com"}'  localhost:8080/profile
Note: Unnecessary use of -X or --request, POST is already inferred.
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to localhost (127.0.0.1) port 8080 (#0)
> POST /profile HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.58.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 122
> 
* upload completely sent off: 122 out of 122 bytes
< HTTP/1.1 201 
< Location: http://localhost:8080/profile
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Sat, 30 May 2020 08:36:11 GMT
< 
* Connection #0 to host localhost left intact
{"id":1,"username":"unamerkel","password":"changeme","firstName":"Una","lastName":"Merkel","email":"unamerkel@example.com","imageFilename":null,"imageFileContentType":null}iain:~$ curl -v --header "Content-Type: application/json"   --request POST   -hangeme","firstName": "Una","lastName": "Merkel","email": "unamerkel@example.com"}'  localhost:8080/profile | python -mjson.tool
Note: Unnecessary use of -X or --request, POST is already inferred.
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to localhost (127.0.0.1) port 8080 (#0)
> POST /profile HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.58.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 122
> 
} [122 bytes data]
* upload completely sent off: 122 out of 122 bytes
100   122    0     0  100   122      0     29  0:00:04  0:00:04 --:--:--    29< HTTP/1.1 201 
< Location: http://localhost:8080/profile
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Sat, 30 May 2020 08:37:10 GMT
< 
{ [178 bytes data]
100   294    0   172  100   122     33     24  0:00:05  0:00:05 --:--:--    44
* Connection #0 to host localhost left intact
{
    "email": "unamerkel@example.com",
    "firstName": "Una",
    "id": 1,
    "imageFileContentType": null,
    "imageFilename": null,
    "lastName": "Merkel",
    "password": "changeme",
    "username": "unamerkel"
}
iain:~$ 

```


### GET

```
iain:~$ curl -v --header "Content-Type: application/json"  localhost:8080/profile/unamerkel | jq .
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to localhost (127.0.0.1) port 8080 (#0)
> GET /profile/unamerkel HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.58.0
> Accept: */*
> Content-Type: application/json
> 
  0     0    0     0    0     0      0      0 --:--:--  0:00:02 --:--:--     0< HTTP/1.1 200 
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Sat, 30 May 2020 08:38:41 GMT
< 
{ [178 bytes data]
100   172    0   172    0     0     59      0 --:--:--  0:00:02 --:--:--    59
* Connection #0 to host localhost left intact
{
  "id": 1,
  "username": "unamerkel",
  "password": "changeme",
  "firstName": "Una",
  "lastName": "Merkel",
  "email": "unamerkel@example.com",
  "imageFilename": null,
  "imageFileContentType": null
}

```

### GET (not found)
```
iain:~$ curl -v --header "Content-Type: application/json"  localhost:8080/profile/russcolombo | jq .
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to localhost (127.0.0.1) port 8080 (#0)
> GET /profile/russcolombo HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.58.0
> Accept: */*
> Content-Type: application/json
> 
< HTTP/1.1 404 
< Content-Length: 0
< Date: Sat, 30 May 2020 08:39:49 GMT
< 
  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0
* Connection #0 to host localhost left intact

```


### POST imageUpload
* Upload in progress..


### GET imageDownload


## Building a docker image

* Used Spring Boot 2.3.0 (recent GA) support for building docker images initially. Followed https://spring.io/guides/gs
/spring-boot-docker/

I used the  Spring Boot image generator to generate the docker image (https://github.com/spring-guides/gs-spring-boot-docker)

```
./mvnw spring-boot:build-image
docker build -t iainardo/m2k8s:latest .
#docker image tag a5c4e6591116 iainardo/m2k8s:latest
docker login iainardo
docker push iainardo/m2k8s:latest
```

### Issues

#### Profile app container not accessing the profiledb container 
* I hit issues where profile app cannot access a db connection (although I can verify the connection in a terminal)

#### Error info 

 * Stacktrace when starting up the profileapp:
````$xslt
Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'mvcConversionService' defined in class path resource [org/springframework/boot/autoconfigure/web/servlet/WebMvcAutoConfiguration$EnableWebMvcConfiguration.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [org.springframework.format.support.FormattingConversionService]: Factory method 'mvcConversionService' threw exception; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'profileRepository' defined in com.manning.liveproject.m2k8s.data.ProfileRepository defined in @EnableJpaRepositories declared on JpaRepositoriesRegistrar.EnableJpaRepositoriesConfiguration: Cannot resolve reference to bean 'jpaMappingContext' while setting bean property 'mappingContext'; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'jpaMappingContext': Invocation of init method failed; nested exception is javax.persistence.PersistenceException: [PersistenceUnit: default] Unable to build Hibernate SessionFactory; nested exception is org.hibernate.exception.JDBCConnectionException: Unable to open JDBC Connection for DDL execution
	at org.springframework.beans.factory.support.ConstructorResolver.instantiate(ConstructorResolver.java:656)
	at org.springframework.beans.factory.support.ConstructorResolver.instantiateUsingFactoryMethod(ConstructorResolver.java:636)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.instantiateUsingFactoryMethod(AbstractAutowireCapableBeanFactory.java:1338)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1177)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:557)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:517)
	at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:323)
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:226)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:321)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:202)
	at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1306)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1226)
	at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:885)
	at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:789)
	... 28 common frames omitted
Caused by: org.springframework.beans.BeanInstantiationException: Failed to instantiate [org.springframework.format.support.FormattingConversionService]: Factory method 'mvcConversionService' threw exception; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'profileRepository' defined in com.manning.liveproject.m2k8s.data.ProfileRepository defined in @EnableJpaRepositories declared on JpaRepositoriesRegistrar.EnableJpaRepositoriesConfiguration: Cannot resolve reference to bean 'jpaMappingContext' while setting bean property 'mappingContext'; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'jpaMappingContext': Invocation of init method failed; nested exception is javax.persistence.PersistenceException: [PersistenceUnit: default] Unable to build Hibernate SessionFactory; nested exception is org.hibernate.exception.JDBCConnectionException: Unable to open JDBC Connection for DDL execution
	at org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:185)
	at org.springframework.beans.factory.support.ConstructorResolver.instantiate(ConstructorResolver.java:651)
	... 42 common frames omitted
Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'profileRepository' defined in com.manning.liveproject.m2k8s.data.ProfileRepository defined in @EnableJpaRepositories declared on JpaRepositoriesRegistrar.EnableJpaRepositoriesConfiguration: Cannot resolve reference to bean 'jpaMappingContext' while setting bean property 'mappingContext'; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'jpaMappingContext': Invocation of init method failed; nested exception is javax.persistence.PersistenceException: [PersistenceUnit: default] Unable to build Hibernate SessionFactory; nested exception is org.hibernate.exception.JDBCConnectionException: Unable to open JDBC Connection for DDL execution
	at org.springframework.beans.factory.support.BeanDefinitionValueResolver.resolveReference(BeanDefinitionValueResolver.java:342)
	at org.springframework.beans.factory.support.BeanDefinitionValueResolver.resolveValueIfNecessary(BeanDefinitionValueResolver.java:113)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyPropertyValues(AbstractAutowireCapableBeanFactory.java:1699)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.populateBean(AbstractAutowireCapableBeanFactory.java:1444)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:594)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:517)
	at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:323)
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:226)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:321)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:207)
	at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1114)
	at org.springframework.data.repository.support.Repositories.cacheRepositoryFactory(Repositories.java:99)
	at org.springframework.data.repository.support.Repositories.populateRepositoryFactoryInformation(Repositories.java:92)
	at org.springframework.data.repository.support.Repositories.<init>(Repositories.java:85)
	at org.springframework.data.repository.support.DomainClassConverter.setApplicationContext(DomainClassConverter.java:109)
	at org.springframework.data.web.config.SpringDataWebConfiguration.addFormatters(SpringDataWebConfiguration.java:130)
	at org.springframework.web.servlet.config.annotation.WebMvcConfigurerComposite.addFormatters(WebMvcConfigurerComposite.java:81)
	at org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration.addFormatters(DelegatingWebMvcConfiguration.java:78)
	at org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration$EnableWebMvcConfiguration.mvcConversionService(WebMvcAutoConfiguration.java:435)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:154)
	... 43 common frames omitted
Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'jpaMappingContext': Invocation of init method failed; nested exception is javax.persistence.PersistenceException: [PersistenceUnit: default] Unable to build Hibernate SessionFactory; nested exception is org.hibernate.exception.JDBCConnectionException: Unable to open JDBC Connection for DDL execution
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1796)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:595)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:517)
	at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:323)
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:226)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:321)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:202)
	at org.springframework.beans.factory.support.BeanDefinitionValueResolver.resolveReference(BeanDefinitionValueResolver.java:330)
	... 66 common frames omitted
Caused by: javax.persistence.PersistenceException: [PersistenceUnit: default] Unable to build Hibernate SessionFactory; nested exception is org.hibernate.exception.JDBCConnectionException: Unable to open JDBC Connection for DDL execution

````

##### Troubleshooting

* I can verify I can access the containerised database as shown below
```$xslt
iain:/data/open/IC-MigratingToK8s$ docker logs profiledb -f
2020-06-06 12:42:32+00:00 [Note] [Entrypoint]: Entrypoint script for MySQL Server 5.7.30-1debian10 started.
2020-06-06 12:42:33+00:00 [Note] [Entrypoint]: Switching to dedicated user 'mysql'
2020-06-06 12:42:33+00:00 [Note] [Entrypoint]: Entrypoint script for MySQL Server 5.7.30-1debian10 started.
2020-06-06T12:42:33.998623Z 0 [Warning] TIMESTAMP with implicit DEFAULT value is deprecated. Please use --explicit_defaults_for_timestamp server option (see documentation for more details).
2020-06-06T12:42:34.000389Z 0 [Note] mysqld (mysqld 5.7.30) starting as process 1 ...
2020-06-06T12:42:34.003680Z 0 [Note] InnoDB: PUNCH HOLE support available
2020-06-06T12:42:34.003695Z 0 [Note] InnoDB: Mutexes and rw_locks use GCC atomic builtins
2020-06-06T12:42:34.003699Z 0 [Note] InnoDB: Uses event mutexes
2020-06-06T12:42:34.003702Z 0 [Note] InnoDB: GCC builtin __atomic_thread_fence() is used for memory barrier
2020-06-06T12:42:34.003705Z 0 [Note] InnoDB: Compressed tables use zlib 1.2.11
2020-06-06T12:42:34.003708Z 0 [Note] InnoDB: Using Linux native AIO
2020-06-06T12:42:34.003952Z 0 [Note] InnoDB: Number of pools: 1
2020-06-06T12:42:34.004057Z 0 [Note] InnoDB: Using CPU crc32 instructions
2020-06-06T12:42:34.005647Z 0 [Note] InnoDB: Initializing buffer pool, total size = 128M, instances = 1, chunk size = 128M
2020-06-06T12:42:34.016644Z 0 [Note] InnoDB: Completed initialization of buffer pool
2020-06-06T12:42:34.018671Z 0 [Note] InnoDB: If the mysqld execution user is authorized, page cleaner thread priority can be changed. See the man page of setpriority().
2020-06-06T12:42:34.033599Z 0 [Note] InnoDB: Highest supported file format is Barracuda.
2020-06-06T12:42:34.061347Z 0 [Note] InnoDB: Creating shared tablespace for temporary tables
2020-06-06T12:42:34.061506Z 0 [Note] InnoDB: Setting file './ibtmp1' size to 12 MB. Physically writing the file full; Please wait ...
2020-06-06T12:42:34.141163Z 0 [Note] InnoDB: File './ibtmp1' size is now 12 MB.
2020-06-06T12:42:34.143064Z 0 [Note] InnoDB: 96 redo rollback segment(s) found. 96 redo rollback segment(s) are active.
2020-06-06T12:42:34.143092Z 0 [Note] InnoDB: 32 non-redo rollback segment(s) are active.
2020-06-06T12:42:34.144586Z 0 [Note] InnoDB: 5.7.30 started; log sequence number 12364631
2020-06-06T12:42:34.145045Z 0 [Note] InnoDB: Loading buffer pool(s) from /var/lib/mysql/ib_buffer_pool
2020-06-06T12:42:34.145475Z 0 [Note] Plugin 'FEDERATED' is disabled.
2020-06-06T12:42:34.150761Z 0 [Note] InnoDB: Buffer pool(s) load completed at 200606 12:42:34
2020-06-06T12:42:34.161978Z 0 [Note] Found ca.pem, server-cert.pem and server-key.pem in data directory. Trying to enable SSL support using them.
2020-06-06T12:42:34.162026Z 0 [Note] Skipping generation of SSL certificates as certificate files are present in data directory.
2020-06-06T12:42:34.164693Z 0 [Warning] CA certificate ca.pem is self signed.
2020-06-06T12:42:34.164785Z 0 [Note] Skipping generation of RSA key pair as key files are present in data directory.
2020-06-06T12:42:34.166482Z 0 [Note] Server hostname (bind-address): '*'; port: 3306
2020-06-06T12:42:34.166548Z 0 [Note] IPv6 is available.
2020-06-06T12:42:34.166574Z 0 [Note]   - '::' resolves to '::';
2020-06-06T12:42:34.166609Z 0 [Note] Server socket created on IP: '::'.
2020-06-06T12:42:34.244955Z 0 [Warning] Insecure configuration for --pid-file: Location '/var/run/mysqld' in the path is accessible to all OS users. Consider choosing a different directory.
2020-06-06T12:42:34.309654Z 0 [Note] Event Scheduler: Loaded 0 events
2020-06-06T12:42:34.310349Z 0 [Note] mysqld: ready for connections.
Version: '5.7.30'  socket: '/var/run/mysqld/mysqld.sock'  port: 3306  MySQL Community Server (GPL)
^C
iain:/data/open/IC-MigratingToK8s$ docker ps
CONTAINER ID        IMAGE                   COMMAND                  CREATED              STATUS              PORTS                                        NAMES
906b395d980f        iainardo/m2k8s:latest   "java -jar /app.jar"     About a minute ago   Up About a minute   0.0.0.0:8080->8080/tcp                       profileapp
dc4ed07a287e        mysql:5.7.30            "docker-entrypoint.s…"   19 minutes ago       Up 18 minutes       33060/tcp, 0.0.0.0:33061->3306/tcp           profiledb
50a271bf7558        rodolpheche/wiremock    "/docker-entrypoint.…"   20 hours ago         Up 20 hours         8080/tcp, 8443/tcp, 0.0.0.0:8888->8888/tcp   relaxed_meitner
iain:/data/open/IC-MigratingToK8s$ docker exec -ti profiledb bash
root@dc4ed07a287e:/# mysql -uempuser -ppassword
mysql: [Warning] Using a password on the command line interface can be insecure.
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 22
Server version: 5.7.30 MySQL Community Server (GPL)

Copyright (c) 2000, 2020, Oracle and/or its affiliates. All rights reserved.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> show schemas;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| profiles           |
+--------------------+
2 rows in set (0.00 sec)

mysql> 


```

* I changed docker compose from using default docker bridge to a custom network

```
13:55:01.433 [main] INFO  c.m.liveproject.m2k8s.Application - No active profile set, falling back to default profiles: default
13:55:02.745 [main] INFO  o.s.d.r.c.RepositoryConfigurationDelegate - Bootstrapping Spring Data JPA repositories in DEFERRED mode.
13:55:02.877 [main] INFO  o.s.d.r.c.RepositoryConfigurationDelegate - Finished Spring Data repository scanning in 121ms. Found 1 JPA repository interfaces.
13:55:03.860 [main] INFO  o.s.b.w.e.tomcat.TomcatWebServer - Tomcat initialized with port(s): 8080 (http)
13:55:03.886 [main] INFO  o.a.coyote.http11.Http11NioProtocol - Initializing ProtocolHandler ["http-nio-8080"]
13:55:03.887 [main] INFO  o.a.catalina.core.StandardService - Starting service [Tomcat]
13:55:03.887 [main] INFO  o.a.catalina.core.StandardEngine - Starting Servlet engine: [Apache Tomcat/9.0.35]
13:55:04.119 [main] INFO  o.a.c.c.C.[Tomcat].[localhost].[/] - Initializing Spring embedded WebApplicationContext
13:55:04.119 [main] INFO  o.s.web.context.ContextLoader - Root WebApplicationContext: initialization completed in 2619 ms
13:55:04.439 [main] INFO  o.f.c.i.license.VersionPrinter - Flyway Community Edition 6.4.3 by Redgate
13:55:04.472 [main] INFO  com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Starting...
13:56:38.792 [main] DEBUG com.zaxxer.hikari.pool.PoolBase - HikariPool-1 - Failed to create/setup connection: Could not create connection to database server. Attempted reconnect 3 times. Giving up.
13:56:38.792 [main] DEBUG com.zaxxer.hikari.pool.PoolBase - HikariPool-1 - Failed to create/setup connection: Could not create connection to database server. Attempted reconnect 3 times. Giving up.
13:56:38.794 [main] DEBUG com.zaxxer.hikari.pool.HikariPool - HikariPool-1 - Cannot acquire connection from data source
java.sql.SQLNonTransientConnectionException: Could not create connection to database server. Attempted reconnect 3 times. Giving up.
        at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:110)
        at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:97)
        at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:89)
        at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:63)

```