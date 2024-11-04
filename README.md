# Cph Business School - Datamatiker - 3. Semester

## Backend Exam - Programming and Technique

 - **api urls**: https://exam.obhnothing.dk/api/{endpoint}

 - **name**: Oskar Bahner Hansen

 - **email**: cph-oh82@cphbusiness.dk

### Task 1

 - src/main/java/dk/obhnothing/persistence/dto/PlantDTO.java

#### `src/test/http/CRUD_plants_local.http` output:

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                      Running IntelliJ HTTP Client with                      │
├──────────────────────┬──────────────────────────────────────────────────────┤
│        Files         │ CRUD_plants_local.http                               │
├──────────────────────┼──────────────────────────────────────────────────────┤
│  Public Environment  │                                                      │
├──────────────────────┼──────────────────────────────────────────────────────┤
│ Private Environment  │                                                      │
└──────────────────────┴──────────────────────────────────────────────────────┘
Request 'LOGIN' POST http://localhost:9999/auth/login
= request =>
POST http://localhost:9999/auth/login
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
Date: Sat, 02 Nov 2024 06:10:38 GMT
Content-Type: application/json
Content-Length: 208

{"token":"eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJwb2tlY29ycCIsInN1YiI6ImFkbWluIiwiZXhwIjoxNzMwNTI5NjM4LCJyb2xlcyI6ImFkbWluIiwidXNlcm5hbWUiOiJhZG1pbiJ9.fO_fJEw6B4qShN57ypepFEBeSP1ZrXkQJoaTaUxBg4Y","username":"admin"}

Response code: 200 (OK); Time: 512ms (512 ms); Content length: 208 bytes (208 B)

Request 'TEST ADMIN PRIVS' GET http://localhost:9999/protected/admin_demo
= request =>
GET http://localhost:9999/protected/admin_demo
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJwb2tlY29ycCIsInN1YiI6ImFkbWluIiwiZXhwIjoxNzMwNTI5NjM4LCJyb2xlcyI6ImFkbWluIiwidXNlcm5hbWUiOiJhZG1pbiJ9.fO_fJEw6B4qShN57ypepFEBeSP1ZrXkQJoaTaUxBg4Y
User-Agent: IntelliJ HTTP Client/CLI 2024.2
Accept-Encoding: br, deflate, gzip, x-gzip
Accept: */*
content-length: 0

###

<= response =
HTTP/1.1 200 OK
Date: Sat, 02 Nov 2024 06:10:39 GMT
Content-Type: application/json
Content-Length: 36

{"msg":"Hello from ADMIN Protected"}

Response code: 200 (OK); Time: 13ms (13 ms); Content length: 36 bytes (36 B)

Request 'GET ALL' GET http://localhost:9999/api/plants
= request =>
GET http://localhost:9999/api/plants
User-Agent: IntelliJ HTTP Client/CLI 2024.2
Accept-Encoding: br, deflate, gzip, x-gzip
Accept: */*
content-length: 0

###

<= response =
HTTP/1.1 200 OK
Date: Sat, 02 Nov 2024 06:10:39 GMT
Content-Type: application/json
Content-Length: 1127

[{"id":1,"planttype":"leopard Metis","name":"Rusty Keyes","maxheight":71,"price":66.34},{"id":2,"planttype":"gnu Perses","name":"Rita Story","maxheight":58,"price":55.45},{"id":3,"planttype":"dolphin Eurynome","name":"Diane Toluvia","maxheight":20,"price":82.53},{"id":4,"planttype":"gorilla Hyperion","name":"Darryl Likt","maxheight":96,"price":43.27},{"id":5,"planttype":"locust Eurybia","name":"Duane Pipe","maxheight":43,"price":3.11},{"id":6,"planttype":"koala Aura","name":"Sarah Doctorinthehouse","maxheight":22,"price":54.76},{"id":7,"planttype":"mallard Eos","name":"Phil DeGrave","maxheight":45,"price":8.62},{"id":8,"planttype":"trout Eurynome","name":"Carrie Oakey","maxheight":11,"price":83.21},{"id":9,"planttype":"caribou Selene","name":"Shandy Lear","maxheight":44,"price":53.09},{"id":10,"planttype":"swan Metis","name":"Rusty Irons","maxheight":36,"price":58.05},{"id":11,"planttype":"test_type","name":"test_name","maxheight":33,"price":55.53},{"id":12,"planttype":"test_type","name":"test_name","maxheight":33,"price":55.53},{"id":13,"planttype":"test_type","name":"test_name","maxheight":33,"price":55.53}]

Response code: 200 (OK); Time: 10ms (10 ms); Content length: 1127 bytes (1.13 kB)

Request 'POST PLANT NO AUTHORIZATION' POST http://localhost:9999/api/plants
= request =>
POST http://localhost:9999/api/plants
Content-Type: application/json
Content-Length: 100
User-Agent: IntelliJ HTTP Client/CLI 2024.2
Accept-Encoding: br, deflate, gzip, x-gzip
Accept: */*

< /home/oskar/Documents/mock-exam-2/src/test/http/./../resources/json/plant1.json

###

<= response =
HTTP/1.1 401 Unauthorized
Date: Sat, 02 Nov 2024 06:10:39 GMT
Content-Type: text/plain
Content-Length: 28

Authorization header missing

Response code: 401 (Unauthorized); Time: 11ms (11 ms); Content length: 28 bytes (28 B)

Request 'POST PLANT' POST http://localhost:9999/api/plants
= request =>
POST http://localhost:9999/api/plants
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJwb2tlY29ycCIsInN1YiI6ImFkbWluIiwiZXhwIjoxNzMwNTI5NjM4LCJyb2xlcyI6ImFkbWluIiwidXNlcm5hbWUiOiJhZG1pbiJ9.fO_fJEw6B4qShN57ypepFEBeSP1ZrXkQJoaTaUxBg4Y
Content-Length: 100
User-Agent: IntelliJ HTTP Client/CLI 2024.2
Accept-Encoding: br, deflate, gzip, x-gzip
Accept: */*

< /home/oskar/Documents/mock-exam-2/src/test/http/./../resources/json/plant1.json

###

<= response =
HTTP/1.1 201 Created
Date: Sat, 02 Nov 2024 06:10:39 GMT
Content-Type: application/json
Content-Length: 81

{"id":14,"planttype":"test_type","name":"test_name","maxheight":33,"price":55.53}

Response code: 201 (Created); Time: 13ms (13 ms); Content length: 81 bytes (81 B)

Request 'GET POSTED' GET http://localhost:9999/api/plants/14
= request =>
GET http://localhost:9999/api/plants/14
User-Agent: IntelliJ HTTP Client/CLI 2024.2
Accept-Encoding: br, deflate, gzip, x-gzip
Accept: */*
content-length: 0

###

<= response =
HTTP/1.1 200 OK
Date: Sat, 02 Nov 2024 06:10:39 GMT
Content-Type: application/json
Content-Length: 81

{"id":14,"planttype":"test_type","name":"test_name","maxheight":33,"price":55.53}

Response code: 200 (OK); Time: 8ms (8 ms); Content length: 81 bytes (81 B)
```

### Task 2

| HTTP method | REST Ressource                | Response & statuscode | Exceptions & statuscode |
|-------------|-------------------------------|---|---------------------------|
| GET         | ```/api/plants```             | [{ ... }, { ... }] (200)  | Internal Server Error (500),   |
| GET         | ```/api/plants/{id}```        | { ... } (200) | Internal Server Error (500), Bad Request (400), Not Found (404)                         |
| GET         | ```/api/plants/type/{type}``` | [{ ... }, { ... }] (200) | Internal Server Error (500), Bad Request (400), Not Found (404)                         |
| POST        | ```/api/plants```             | { ... } (201) | Internal Server Error (500), Unauthorized (401)                         |

### Task 3

 - Streams API from Java 8 is inspired by the functional programming paradigm.

### Task 4

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                      Running IntelliJ HTTP Client with                      │
├──────────────────────┬──────────────────────────────────────────────────────┤
│        Files         │ CRUD_plants_local.http                               │
├──────────────────────┼──────────────────────────────────────────────────────┤
│  Public Environment  │                                                      │
├──────────────────────┼──────────────────────────────────────────────────────┤
│ Private Environment  │                                                      │
└──────────────────────┴──────────────────────────────────────────────────────┘
Request 'LOGIN' POST http://localhost:9999/auth/login
= request =>
POST http://localhost:9999/auth/login
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
Date: Sat, 02 Nov 2024 10:11:04 GMT
Content-Type: application/json
Content-Length: 208

{"token":"eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJwb2tlY29ycCIsInN1YiI6ImFkbWluIiwiZXhwIjoxNzMwNTQ0MDY0LCJyb2xlcyI6ImFkbWluIiwidXNlcm5hbWUiOiJhZG1pbiJ9.kmH6M1PtdJgxobzdfWcab-Ksb13kfast2k6HiVjKEz8","username":"admin"}

Response code: 200 (OK); Time: 568ms (568 ms); Content length: 208 bytes (208 B)

Request 'TEST ADMIN PRIVS' GET http://localhost:9999/protected/admin_demo
= request =>
GET http://localhost:9999/protected/admin_demo
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJwb2tlY29ycCIsInN1YiI6ImFkbWluIiwiZXhwIjoxNzMwNTQ0MDY0LCJyb2xlcyI6ImFkbWluIiwidXNlcm5hbWUiOiJhZG1pbiJ9.kmH6M1PtdJgxobzdfWcab-Ksb13kfast2k6HiVjKEz8
User-Agent: IntelliJ HTTP Client/CLI 2024.2
Accept-Encoding: br, deflate, gzip, x-gzip
Accept: */*
content-length: 0

###

<= response =
HTTP/1.1 200 OK
Date: Sat, 02 Nov 2024 10:11:04 GMT
Content-Type: application/json
Content-Length: 36

{"msg":"Hello from ADMIN Protected"}

Response code: 200 (OK); Time: 30ms (30 ms); Content length: 36 bytes (36 B)

Request 'GET ALL' GET http://localhost:9999/api/plants
= request =>
GET http://localhost:9999/api/plants
User-Agent: IntelliJ HTTP Client/CLI 2024.2
Accept-Encoding: br, deflate, gzip, x-gzip
Accept: */*
content-length: 0

###

<= response =
HTTP/1.1 200 OK
Date: Sat, 02 Nov 2024 10:11:05 GMT
Content-Type: application/json
Content-Encoding: gzip
Content-Length: 1206

[{.................}]

Response code: 200 (OK); Time: 264ms (264 ms); Content length: 3895 bytes (3.9 kB)

Request 'POST PLANT NO AUTHORIZATION' POST http://localhost:9999/api/plants
= request =>
POST http://localhost:9999/api/plants
Content-Type: application/json
Content-Length: 100
User-Agent: IntelliJ HTTP Client/CLI 2024.2
Accept-Encoding: br, deflate, gzip, x-gzip
Accept: */*

< /home/oskar/Documents/mock-exam-2/src/test/http/./../resources/json/plant1.json

###

<= response =
HTTP/1.1 401 Unauthorized
Date: Sat, 02 Nov 2024 10:11:05 GMT
Content-Type: application/json
Content-Length: 83

{"message":"401 Unauthorized","status":"401","timestamp":"2024-11-02 10:11:05.426"}

Response code: 401 (Unauthorized); Time: 17ms (17 ms); Content length: 83 bytes (83 B)

Request 'POST PLANT' POST http://localhost:9999/api/plants
= request =>
POST http://localhost:9999/api/plants
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJwb2tlY29ycCIsInN1YiI6ImFkbWluIiwiZXhwIjoxNzMwNTQ0MDY0LCJyb2xlcyI6ImFkbWluIiwidXNlcm5hbWUiOiJhZG1pbiJ9.kmH6M1PtdJgxobzdfWcab-Ksb13kfast2k6HiVjKEz8
Content-Length: 100
User-Agent: IntelliJ HTTP Client/CLI 2024.2
Accept-Encoding: br, deflate, gzip, x-gzip
Accept: */*

< /home/oskar/Documents/mock-exam-2/src/test/http/./../resources/json/plant1.json

###

<= response =
HTTP/1.1 201 Created
Date: Sat, 02 Nov 2024 10:11:05 GMT
Content-Type: application/json
Content-Length: 97

{"id":662,"planttype":"test_type","name":"test_name","maxheight":33,"price":55.53,"resellers":[]}

Response code: 201 (Created); Time: 33ms (33 ms); Content length: 97 bytes (97 B)

Request 'GET POSTED' GET http://localhost:9999/api/plants/662
= request =>
GET http://localhost:9999/api/plants/662
User-Agent: IntelliJ HTTP Client/CLI 2024.2
Accept-Encoding: br, deflate, gzip, x-gzip
Accept: */*
content-length: 0

###

<= response =
HTTP/1.1 200 OK
Date: Sat, 02 Nov 2024 10:11:05 GMT
Content-Type: application/json
Content-Length: 97

{"id":662,"planttype":"test_type","name":"test_name","maxheight":33,"price":55.53,"resellers":[]}

Response code: 200 (OK); Time: 50ms (50 ms); Content length: 97 bytes (97 B)

Request 'GET FAIL 400' GET http://localhost:9999/api/plants/fail
= request =>
GET http://localhost:9999/api/plants/fail
User-Agent: IntelliJ HTTP Client/CLI 2024.2
Accept-Encoding: br, deflate, gzip, x-gzip
Accept: */*
content-length: 0

###

<= response =
HTTP/1.1 400 Bad Request
Date: Sat, 02 Nov 2024 10:11:05 GMT
Content-Type: application/json
Content-Length: 82

{"message":"400 Bad Request","status":"400","timestamp":"2024-11-02 10:11:05.661"}

Response code: 400 (Bad Request); Time: 10ms (10 ms); Content length: 82 bytes (82 B)

Request 'GET FAIL 404' GET http://localhost:9999/api/plants/240345
= request =>
GET http://localhost:9999/api/plants/240345
User-Agent: IntelliJ HTTP Client/CLI 2024.2
Accept-Encoding: br, deflate, gzip, x-gzip
Accept: */*
content-length: 0

###

<= response =
HTTP/1.1 404 Not Found
Date: Sat, 02 Nov 2024 10:11:05 GMT
Content-Type: application/json
Content-Length: 80

{"message":"404 Not Found","status":"404","timestamp":"2024-11-02 10:11:05.714"}

Response code: 404 (Not Found); Time: 11ms (11 ms); Content length: 80 bytes (80 B)




8 requests completed, 0 have failed tests
```

### Task 5
### Task 6

## Entities

![uml](https://github.com/Oskar123456/sp-2-pokedex/blob/master/report/ERD.png?raw=true)
