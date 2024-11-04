# Cph Business School - Datamatiker - 3. Semester

## Backend Exam - Programming and Technique

### Task 1

  - done

### Task 2

  - done

### Task 3

  - output for `src/test/http/dev.http`:


```
┌─────────────────────────────────────────────────────────────────────────────┐
│                      Running IntelliJ HTTP Client with                      │
├──────────────────────┬──────────────────────────────────────────────────────┤
│        Files         │ CRUD_trip_local.http                                 │
├──────────────────────┼──────────────────────────────────────────────────────┤
│  Public Environment  │                                                      │
├──────────────────────┼──────────────────────────────────────────────────────┤
│ Private Environment  │                                                      │
└──────────────────────┴──────────────────────────────────────────────────────┘
Request '[Security] LOGIN FAIL' POST http://localhost:9999/api/auth/login
= request =>
POST http://localhost:9999/api/auth/login
Content-Type: application/json
Content-Length: 50
User-Agent: IntelliJ HTTP Client/CLI 2024.2
Accept-Encoding: br, deflate, gzip, x-gzip
Accept: */*

{
    "username": "admin",
    "password": "lol"
}

###

<= response =
HTTP/1.1 401 Unauthorized
Date: Mon, 04 Nov 2024 11:41:52 GMT
Content-Type: application/json
Content-Length: 24

{"msg":"Wrong password"}

Response code: 401 (Unauthorized); Time: 615ms (615 ms); Content length: 24 bytes (24 B)

Request '[Security] LOGIN' POST http://localhost:9999/api/auth/login
= request =>
POST http://localhost:9999/api/auth/login
Content-Type: application/json
Content-Length: 52
User-Agent: IntelliJ HTTP Client/CLI 2024.2
Accept-Encoding: br, deflate, gzip, x-gzip
Accept: */*

{
    "username": "admin",
    "password": "admin"
}

###

<= response =
HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:41:53 GMT
Content-Type: application/json
Content-Length: 202

{"token":"eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJMT0wiLCJzdWIiOiJhZG1pbiIsImV4cCI6MTczMDcyMjMxMywicm9sZXMiOiJhZG1pbiIsInVzZXJuYW1lIjoiYWRtaW4ifQ.qDMm4tddLYklX8GZElz-Zw2-lIlV4_TpPOuw0BSiV0o","username":"admin"}

Response code: 200 (OK); Time: 89ms (89 ms); Content length: 202 bytes (202 B)

Request '[Security] TEST USER PRIVS FAIL' GET http://localhost:9999/api/protected/user_demo
= request =>
GET http://localhost:9999/api/protected/user_demo
Authorization: Bearer "LOL"
User-Agent: IntelliJ HTTP Client/CLI 2024.2
Accept-Encoding: br, deflate, gzip, x-gzip
Accept: */*
content-length: 0

###

<= response =
HTTP/1.1 401 Unauthorized
Date: Mon, 04 Nov 2024 11:41:53 GMT
Content-Type: application/json
Content-Length: 83

{"message":"401 Unauthorized","status":"401","timestamp":"2024-41-04 12:41:53.349"}

Response code: 401 (Unauthorized); Time: 18ms (18 ms); Content length: 83 bytes (83 B)

Request '[Security] TEST ADMIN PRIVS FAIL' GET http://localhost:9999/api/protected/admin_demo
= request =>
GET http://localhost:9999/api/protected/admin_demo
Authorization: Bearer "LOL"
User-Agent: IntelliJ HTTP Client/CLI 2024.2
Accept-Encoding: br, deflate, gzip, x-gzip
Accept: */*
content-length: 0

###

<= response =
HTTP/1.1 401 Unauthorized
Date: Mon, 04 Nov 2024 11:41:53 GMT
Content-Type: application/json
Content-Length: 83

{"message":"401 Unauthorized","status":"401","timestamp":"2024-41-04 12:41:53.425"}

Response code: 401 (Unauthorized); Time: 8ms (8 ms); Content length: 83 bytes (83 B)

Request '[Security] TEST ADMIN PRIVS' GET http://localhost:9999/api/protected/admin_demo
= request =>
GET http://localhost:9999/api/protected/admin_demo
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJMT0wiLCJzdWIiOiJhZG1pbiIsImV4cCI6MTczMDcyMjMxMywicm9sZXMiOiJhZG1pbiIsInVzZXJuYW1lIjoiYWRtaW4ifQ.qDMm4tddLYklX8GZElz-Zw2-lIlV4_TpPOuw0BSiV0o
User-Agent: IntelliJ HTTP Client/CLI 2024.2
Accept-Encoding: br, deflate, gzip, x-gzip
Accept: */*
content-length: 0

###

<= response =
HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:41:53 GMT
Content-Type: application/json
Content-Length: 36

{"msg":"Hello from ADMIN Protected"}

Response code: 200 (OK); Time: 17ms (17 ms); Content length: 36 bytes (36 B)

Request '[API] GET ALL' GET http://localhost:9999/api/trips
= request =>
GET http://localhost:9999/api/trips
User-Agent: IntelliJ HTTP Client/CLI 2024.2
Accept-Encoding: br, deflate, gzip, x-gzip
Accept: */*
content-length: 0

###

<= response =
HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:41:53 GMT
Content-Type: application/json
Content-Encoding: gzip
Content-Length: 887

[{"id":1302,"starttime":[2025,10,10,12,40,9,957897000],"endtime":[2025,10,21,12,40,9,957897000],"startposition":"Luanda","name":"Trip to Luanda","price":74683.65219291388,"category":"BADEFERIE","guide":{"id":1354,"firstname":"Epifania","lastname":"Medhurst","email":"Epifania@Howe-Kutch.rw","phone":"(212) 768-3799","yearsOfExperience":20,"trips":null}},{"id":1303,"starttime":[2024,11,26,12,40,9,958092000],"endtime":[2024,12,26,12,40,9,958092000],"startposition":"Oslo","name":"Trip to Oslo","price":10057.74984072416,"category":"CAMPING","guide":{"id":1354,"firstname":"Epifania","lastname":"Medhurst","email":"Epifania@Howe-Kutch.rw","phone":"(212) 768-3799","yearsOfExperience":20,"trips":null}},{"id":1304,"starttime":[2026,2,18,12,40,9,958132000],"endtime":[2026,3,18,12,40,9,958132000],"startposition":"Stanley","name":"Trip to Stanley","price":48003.07059260517,"category":"CAMPING","guide":{"id":1354,"firstname":"Epifania","lastname":"Medhurst","email":"Epifania@Howe-Kutch.rw","phone":"(212) 768-3799","yearsOfExperience":20,"trips":null}},{"id":1305,"starttime":[2025,4,27,12,40,9,958163000],"endtime":[2025,5,17,12,40,9,958163000],"startposition":"The Valley","name":"Trip to The Valley","price":29914.01325640431,"category":"CAMPING","guide":{"id":1352,"firstname":"Tony","lastname":"Miller","email":"Tony@Abshire Inc.pt","phone":"1-532-031-7491","yearsOfExperience":8,"trips":null}},{"id":1306,"starttime":[2026,2,4,12,40,9,958190000],"endtime":[2026,3,20,12,40,9,958190000],"startposition":"Port Moresby","name":"Trip to Port Moresby","price":57488.297973796536,"category":"BADEFERIE","guide":{"id":1355,"firstname":"Augustus","lastname":"Considine","email":"Augustus@Pollich Inc.bz","phone":"(277) 947-4257","yearsOfExperience":25,"trips":null}},{"id":1352,"starttime":[2025,5,27,12,41,48,316192000],"endtime":[2025,6,19,12,41,48,316192000],"startposition":"Washington","name":"Trip to Washington","price":77134.45141827271,"category":"CAMPING","guide":{"id":1353,"firstname":"Lili","lastname":"Rau","email":"Lili@O'Reilly, Monahan and Kirlin.rw","phone":"(962) 077-8710","yearsOfExperience":37,"trips":null}},{"id":1353,"starttime":[2024,11,16,12,41,48,316335000],"endtime":[2025,1,4,12,41,48,316335000],"startposition":"Muscat","name":"Trip to Muscat","price":79657.67055767211,"category":"BADEFERIE","guide":{"id":1353,"firstname":"Lili","lastname":"Rau","email":"Lili@O'Reilly, Monahan and Kirlin.rw","phone":"(962) 077-8710","yearsOfExperience":37,"trips":null}},{"id":1354,"starttime":[2024,11,29,12,41,48,316373000],"endtime":[2024,12,21,12,41,48,316373000],"startposition":"Road Town","name":"Trip to Road Town","price":80723.93613199523,"category":"CAMPING","guide":{"id":1352,"firstname":"Tony","lastname":"Miller","email":"Tony@Abshire Inc.pt","phone":"1-532-031-7491","yearsOfExperience":8,"trips":null}},{"id":1355,"starttime":[2025,6,16,12,41,48,316400000],"endtime":[2025,7,14,12,41,48,316400000],"startposition":"Praia","name":"Trip to Praia","price":31962.89244270074,"category":"CAMPING","guide":{"id":1356,"firstname":"Cesar","lastname":"Keeling","email":"Cesar@Mitchell, Beer and Becker.ht","phone":"769-337-3173","yearsOfExperience":42,"trips":null}},{"id":1356,"starttime":[2025,3,28,12,41,48,316428000],"endtime":[2025,5,15,12,41,48,316428000],"startposition":"San Juan","name":"Trip to San Juan","price":8476.202424544113,"category":"CAMPING","guide":{"id":1354,"firstname":"Epifania","lastname":"Medhurst","email":"Epifania@Howe-Kutch.rw","phone":"(212) 768-3799","yearsOfExperience":20,"trips":null}}]

Response code: 200 (OK); Time: 46ms (46 ms); Content length: 3545 bytes (3,54 kB)

Request '[API + SECURITY] POST TRIP' POST http://localhost:9999/api/trips
= request =>
POST http://localhost:9999/api/trips
Content-Type: application/json
Content-Length: 475
User-Agent: IntelliJ HTTP Client/CLI 2024.2
Accept-Encoding: br, deflate, gzip, x-gzip
Accept: */*

< /home/oskar/Documents/real-exam/src/test/http/./../resources/json/trip1.json

###

<= response =
HTTP/1.1 401 Unauthorized
Date: Mon, 04 Nov 2024 11:41:53 GMT
Content-Type: application/json
Content-Length: 83

{"message":"401 Unauthorized","status":"401","timestamp":"2024-41-04 12:41:53.706"}

Response code: 401 (Unauthorized); Time: 17ms (17 ms); Content length: 83 bytes (83 B)

Request '[API] POST TRIP' POST http://localhost:9999/api/trips
= request =>
POST http://localhost:9999/api/trips
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJMT0wiLCJzdWIiOiJhZG1pbiIsImV4cCI6MTczMDcyMjMxMywicm9sZXMiOiJhZG1pbiIsInVzZXJuYW1lIjoiYWRtaW4ifQ.qDMm4tddLYklX8GZElz-Zw2-lIlV4_TpPOuw0BSiV0o
Content-Length: 475
User-Agent: IntelliJ HTTP Client/CLI 2024.2
Accept-Encoding: br, deflate, gzip, x-gzip
Accept: */*

< /home/oskar/Documents/real-exam/src/test/http/./../resources/json/trip1.json

###

<= response =
HTTP/1.1 404 Not Found
Date: Mon, 04 Nov 2024 11:41:53 GMT
Content-Type: application/json
Content-Length: 79

{"message":"404 Not Found","status":"404","timestamp":"2024-41-04 12:41:53.79"}

Response code: 404 (Not Found); Time: 28ms (28 ms); Content length: 79 bytes (79 B)

Failed CRUD_trip_local.http#7 post trip

response status is not 201 404
  this.assert (at js-graalvm-response-handler.js:32)
  :anonymous (at /home/oskar/Documents/real-exam/src/test/http/CRUD_trip_local.http:109)
Request '[API] GET POSTED' GET http://localhost:9999/api/trips/{{trip_id}}
Unable to get response for CRUD_trip_local.http#8:
java.net.URISyntaxException: Illegal character in path at index 32: http://localhost:9999/api/trips/{{trip_id}}
Request '[API] GET FAIL 400' GET http://localhost:9999/api/trips/fail
= request =>
GET http://localhost:9999/api/trips/fail
User-Agent: IntelliJ HTTP Client/CLI 2024.2
Accept-Encoding: br, deflate, gzip, x-gzip
Accept: */*
content-length: 0

###

<= response =
HTTP/1.1 400 Bad Request
Date: Mon, 04 Nov 2024 11:41:53 GMT
Content-Type: application/json
Content-Length: 82

{"message":"400 Bad Request","status":"400","timestamp":"2024-41-04 12:41:53.862"}

Response code: 400 (Bad Request); Time: 9ms (9 ms); Content length: 82 bytes (82 B)

Request '[API] GET FAIL 404' GET http://localhost:9999/api/trips/240345
= request =>
GET http://localhost:9999/api/trips/240345
User-Agent: IntelliJ HTTP Client/CLI 2024.2
Accept-Encoding: br, deflate, gzip, x-gzip
Accept: */*
content-length: 0

###

<= response =
HTTP/1.1 404 Not Found
Date: Mon, 04 Nov 2024 11:41:53 GMT
Content-Type: application/json
Content-Length: 80

{"message":"404 Not Found","status":"404","timestamp":"2024-41-04 12:41:53.971"}

Response code: 404 (Not Found); Time: 49ms (49 ms); Content length: 80 bytes (80 B)

Request '[API] PUT TRIP GUIDE' PUT http://localhost:9999/api/trips/{{trip_id}}
Unable to get response for CRUD_trip_local.http#11:
java.net.URISyntaxException: Illegal character in path at index 32: http://localhost:9999/api/trips/{{trip_id}}
Request '[API] GET BY CAT' GET http://localhost:9999/api/trips/category/BADEFERIE
= request =>
GET http://localhost:9999/api/trips/category/BADEFERIE
User-Agent: IntelliJ HTTP Client/CLI 2024.2
Accept-Encoding: br, deflate, gzip, x-gzip
Accept: */*
content-length: 0

###

<= response =
HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:41:54 GMT
Content-Type: application/json
Content-Length: 1077

[{"id":1302,"starttime":[2025,10,10,12,40,9,957897000],"endtime":[2025,10,21,12,40,9,957897000],"startposition":"Luanda","name":"Trip to Luanda","price":74683.65219291388,"category":"BADEFERIE","guide":{"id":1354,"firstname":"Epifania","lastname":"Medhurst","email":"Epifania@Howe-Kutch.rw","phone":"(212) 768-3799","yearsOfExperience":20,"trips":null}},{"id":1306,"starttime":[2026,2,4,12,40,9,958190000],"endtime":[2026,3,20,12,40,9,958190000],"startposition":"Port Moresby","name":"Trip to Port Moresby","price":57488.297973796536,"category":"BADEFERIE","guide":{"id":1355,"firstname":"Augustus","lastname":"Considine","email":"Augustus@Pollich Inc.bz","phone":"(277) 947-4257","yearsOfExperience":25,"trips":null}},{"id":1353,"starttime":[2024,11,16,12,41,48,316335000],"endtime":[2025,1,4,12,41,48,316335000],"startposition":"Muscat","name":"Trip to Muscat","price":79657.67055767211,"category":"BADEFERIE","guide":{"id":1353,"firstname":"Lili","lastname":"Rau","email":"Lili@O'Reilly, Monahan and Kirlin.rw","phone":"(962) 077-8710","yearsOfExperience":37,"trips":null}}]

Response code: 200 (OK); Time: 14ms (14 ms); Content length: 1077 bytes (1,08 kB)

Request '[API] GET BY SUMS' GET http://localhost:9999/api/trips/guidespricesum
= request =>
GET http://localhost:9999/api/trips/guidespricesum
User-Agent: IntelliJ HTTP Client/CLI 2024.2
Accept-Encoding: br, deflate, gzip, x-gzip
Accept: */*
content-length: 0

###

<= response =
HTTP/1.1 400 Bad Request
Date: Mon, 04 Nov 2024 11:41:54 GMT
Content-Type: application/json
Content-Length: 82

{"message":"400 Bad Request","status":"400","timestamp":"2024-41-04 12:41:54.101"}

Response code: 400 (Bad Request); Time: 9ms (9 ms); Content length: 82 bytes (82 B)

Failed CRUD_trip_local.http#13 get by sums

response status is not correct 400
  this.assert (at js-graalvm-response-handler.js:32)
  :anonymous (at /home/oskar/Documents/real-exam/src/test/http/CRUD_trip_local.http:178)



14 requests completed, 4 have failed tests
RUN FAILED
```


  - comment: Not everything is working.

### Task 4

  - done
  - implemented in `MasterController.java`

### Task 5

  - part 2 is not working

### Task 6

  - not don.

### Task 7

  - somewhat, some tests fail.

### Task 8

  - implemented in `src/test/java/restassured/TestAPIEndPoints.java`



