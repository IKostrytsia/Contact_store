Sprig boot application for searching Contacts using regular expression.

Application uses emedded inmemory H2 database. Data inserts during spring initialization (DataInit class -WORD_COUNT = 100;).
App is running as classic Spring boot application with main class com.appstore.contactboot.ContactBootApplication

Web accessing URL http://localhost:8080/contact?nameFilter=[REGEXP]
or http://localhost:8080/  to view all records

App build: mvn clean install
App docker image build and run: 
  -> mvn clean install dockerfile:build
  -> docker run -p 8080:8080 -t springio/appstore-boot

