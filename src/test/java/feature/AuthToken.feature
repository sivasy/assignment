Feature: Get the token and store it in a variable

Scenario: get the token
* def query = 
"""
{
    "username" : "admin",
    "password" : "password123"
}
"""
Given url baseUrl+'/auth'
And request query
When method post
Then status 200
* def token = response.token
* print response
* def tokenResponse = response.token
* print tokenResponse
And match $.token == '#present'