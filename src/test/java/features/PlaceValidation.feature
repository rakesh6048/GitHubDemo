Feature: Validating Place API's

@AddPlace @Regeression
Scenario Outline: Verify if place is being Successfully added using AddPlaceAPI
    Given Add Place Payload with "<name>" "<language>" "<address>"
    When user call "AddPlaceAPI" with "Post" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    Then verify palce_Id created maps to "<name>" using "GetPlaceAPI"
    When update the address as "cross center, US" using "UpdatePlaceAPI"
    Then "msg" in response body is "Address successfully updated"
    #When delete the place using "DeletePlaceAPI"
    #Then "status" in response body is "OK"
    Examples:
    | name      | language     | address                  |
    | Raj Singh | French-IN    | 29, side layout, cohen 09|
    #| Mahesh | English  | See cross center, US |
 
@DeletePlace @Regeression
Scenario: Verify delete place place functionality is working 

    Given delete place payload
    When user call "DeletePlaceAPI" with "DELETE" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"
    
    