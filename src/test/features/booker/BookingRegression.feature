#Feature: Feature file to test end to end api flow

@regression
Feature: End to end api flow

  Scenario Outline: End to end api invoke
    Given user prepare the generic request for "CREATEBOOKING"
    When user invoke the request for "CREATEBOOKING"
    Then user should get the generic booking success response for "CREATEBOOKING"
    Then validate booking id is available in the booking Id List
    When user prepare the request for "GETBOOKINGBYID" using bookingId
    And user invoke the request for "GETBOOKINGBYID"
    Then user should validate the response with data store for "CREATEBOOKING" request
    And User get the valid token
    And user prepare the generic request for "UPDATEBOOKING"
    When user invoke the request for "UPDATEBOOKING"
    Then user should get the generic booking success response for "UPDATEBOOKING"
    And user prepare the partial update request with payload <payload>
    When user invoke the request for "PARTIALUPDATEBOOKING"
    Then verify updated values in the payload response
    And user prepare the generic request for "DELETEBOOKING"
    When user invoke the request for "DELETEBOOKING"
    Then verify able to delete successfully
    When user prepare the request for "GETBOOKINGBYID" using bookingId
    Then verify booking details unavailable for the booking Id
    Examples:
      | payload                                   |
      | "{'firstname':'Kevin','lastname':'Nerm'}" |

  Scenario Outline: get booking Ids by multiple parameters
    Given user prepare the generic request for "GETBOOKINGIDS" by param <query>
    When user invoke the request for "GETBOOKINGIDS"
    Then user should get the list for bookingIds
    Examples:
    |query|
    |"firstname=josh&lastname=Allen"|


