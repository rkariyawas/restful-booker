#Feature: Feature file to test booking api validation

@bookingApis
Feature: Api Validation bookings

  @getBookingIds
  Scenario: Validate get booking Ids api
    Given user prepare the generic request for "GETBOOKINGIDS"
    When user invoke the request for "GETBOOKINGIDS"
    Then user should get the list for bookingIds

  @getBookingById
  Scenario Outline: Validate get booking by id api
    Given user prepare the request for "GETBOOKINGBYID" using id "100"
    When user invoke the request for "GETBOOKINGBYID"
    Then user should get payload "<jsonResponse>"
    Examples:
      | jsonResponse |
      | JSON_127     |

  @createBooking
  Scenario: Validate create booking api
    Given user prepare the generic request for "CREATEBOOKING"
    When user invoke the request for "CREATEBOOKING"
    Then user should get the generic booking success response for "CREATEBOOKING"

  @updateBooking
  Scenario: Validate update booking api
    Given user prepare the generic request for "CREATEBOOKING"
    When user invoke the request for "CREATEBOOKING"
    Then user should get the generic booking success response for "CREATEBOOKING"
    And User get the valid token
    And user prepare the generic request for "UPDATEBOOKING"
    When user invoke the request for "UPDATEBOOKING"
    Then user should get the generic booking success response for "UPDATEBOOKING"

  @partialUpdateBooking
  Scenario Outline: Validate partial update booking api
    Given user prepare the generic request for "CREATEBOOKING"
    When user invoke the request for "CREATEBOOKING"
    Then user should get the generic booking success response for "CREATEBOOKING"
    And User get the valid token
    And user prepare the partial update request with payload <payload>
    When user invoke the request for "PARTIALUPDATEBOOKING"
    Then verify updated values in the payload response

    Examples:
      | payload                                   |
      | "{'firstname':'Kevin','lastname':'Nerm'}" |

  @deleteBooking
  Scenario: Validate Delete booking api
    Given user prepare the generic request for "CREATEBOOKING"
    When user invoke the request for "CREATEBOOKING"
    Then user should get the generic booking success response for "CREATEBOOKING"
    And User get the valid token
    And user prepare the generic request for "DELETEBOOKING"
    When user invoke the request for "DELETEBOOKING"
    Then verify able to delete successfully

