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

