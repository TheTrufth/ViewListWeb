## Building
### 1. Clean
```mvn clean```
### 2. Build
`mvn build`
### 3. Create WAR file.
`mvn tomcat:deploy`

## Deploying
### 1. Run Tomcat
`/usr/local/opt/tomcat/bin/catalina run`
### 2. Go to manager tomcat and log in.
`http://localhost:8080/manager/html`

_(If you do not know your password then reset it in the `tomcat-users.xml` file inside the `/usr/local/opt/tomcat@10/libexec/conf` folder.)_
### 3 Deploy WAR file.
Drag and drop the WAR file.
