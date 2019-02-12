@api @clear-db
Feature: Perform Listening

  Scenario: Listening with an active endpoint
    Given active endpoint is added with path "app_1"
    When POST request is sent to "/endpoints/app_1" with the following body
    """
    Foo Bar
    """
    Then the following response should be returned with 200 code
    """
    {"message":"Request is captured successfully"}
    """

  Scenario: Listening with an inactive endpoint
    Given inactive endpoint is added with path "app_1"
    When POST request is sent to "/endpoints/app_1" with the following body
    """
    Foo Bar
    """
    Then the following response should be returned with 409 code matching body
    """
    \{
      "timestamp":"\S+",
      "status":409,
      "error":"Conflict",
      "message":"/endpoints/app_1 is not an active endpoint",
      "path":"/endpoints/app_1"
    \}
    """
