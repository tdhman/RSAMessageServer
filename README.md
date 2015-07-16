# RSAMessageServer
A simple RESTful web service to store RSA public key

# Deploy web service to Heroku
- Setup Heroku : https://devcenter.heroku.com/articles/getting-started-with-java#set-up
- Setup Maven : remember to set $JAVA_HOME
ex: export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_45.jdk/Contents/Home)
- Initialize new project using maven as follow :
mvn archetype:generate -DarchetypeArtifactId=jersey-heroku-webapp \
    -DarchetypeGroupId=org.glassfish.jersey.archetypes -DinteractiveMode=false \
    -DgroupId=com.example -DartifactId=simple-heroku-webapp -Dpackage=com.example \
    -DarchetypeVersion=2.5.1
- Compile the project into WAR file :
mvn clean package
- Deploy it into Heroku :
git init
heroku create
git add .
git commit -m "intital commit"
git push heroku master
- If the build is successful, now you can open browser to Heroku app :
heroku open
- In case of any error, see logs :
heroku logs

# Add database for web service
- Create PostgreSQL database (recommended) :
heroku addons:create heroku-postgresql
- View database url :
heroku config | grep DATABASE_URL
- Each postgresql created by Heroku is assigned a name in color (red, rose, black, etc.). Using the command above to get the color name of created database. For accessing database named "black" in command line use :
heroku pg:psql black
- Similarly to MySQL, you can write query to create table scheme in command line.
- To install MySQL, use :
heroku addons:create cleardb

# Note
- Remember to define necessary dependencies for app in pom.xml


