Feature: Execute different Http Methods

Background:
* def authToken = call read("AuthToken.feature")
* def token = authToken.token

Scenario: HttpMethod Get
* def authToken = call read("AuthToken.feature")
* def token = authToken.token
* print token
Given url baseUrl+'/booking'
When method Get
Then status 200
* print response
* def bookingId = response[0].bookingid
* print bookingId


Scenario: HttpMethod Post
* def book =
"""
{
    "firstname" : "Siva",
    "lastname" : "Brown",
    "totalprice" : 111,
    "depositpaid" : true,
    "bookingdates" : {
        "checkin" : "2018-01-01",
        "checkout" : "2019-01-01"
    },
    "additionalneeds" : "Breakfast"
}
"""
Given header Content-Type = 'application/json'
And header Accept = 'application/json'
When url  baseUrl+'/booking'
And request book
When method post
Then status 200
* print response

Scenario: HttpMethod Put
* def authToken = call read("AuthToken.feature")
* def tokenDef = authToken.token
* def book =
"""
{
    "firstname" : "Siva",
    "lastname" : "sankar",
    "totalprice" : 111,
    "depositpaid" : true,
    "bookingdates" : {
        "checkin" : "2018-01-01",
        "checkout" : "2019-01-01"
    },
    "additionalneeds" : "Breakfast"
}
"""
Given header Content-Type = 'application/json'
And header Accept = 'application/json'
And header Cookie = 'token='+tokenDef
When url  baseUrl+'/booking'
And path 1490
And request book
When method put
Then status 200
* print response

Scenario: HttpMethod Patch
* def authToken = call read("AuthToken.feature")
* def tokenDef = authToken.token
* def book =
"""
{
    "firstname" : "Patch",
    "lastname" : "sankar",
    "totalprice" : 113,
    "additionalneeds" : "Dinner"
}
"""
Given header Content-Type = 'application/json'
And header Accept = 'application/json'
And header Cookie = 'token='+tokenDef
When url  baseUrl+'/booking'
And path 1490
And request book
When method patch
Then status 200
* print response

Scenario: HttpMethod delete
* def authToken = call read("AuthToken.feature")
* def tokenDef = authToken.token
Given header Content-Type = 'application/json'
And header Cookie = 'token='+tokenDef
When url  baseUrl+'/booking'
And path 121
When method delete
Then status 201
* print response
