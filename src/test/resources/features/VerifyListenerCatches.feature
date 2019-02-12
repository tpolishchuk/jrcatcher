@api @clear-db
Feature: Verify Listener Catches

  @excluded
  Scenario: Verify listener catches without cookies
    Given active endpoint is added with path "app_listener"
    When POST request is sent to "/endpoints/app_listener" with the following body
    """
    Foo Bar
    """
    And endpoint information page is opened for endpoint with id 1
    Then the first request data should equal "POST http://localhost:7070/endpoints/app_listener"
    When the first request view is expanded
    Then the first request should have body
    """
    Foo Bar
    """
    And the first request should have the following headers
      | Name            | Value                                 |
      | accept          | */*                                   |
      | content-type    | text/plain; charset=ISO-8859-1        |
      | content-length  | 7                                     |
      | host            | localhost:7070                        |
      | connection      | Keep-Alive                            |
      | user-agent      | Apache-HttpClient/4.5.6 (Java/11.0.1) |
      | accept-encoding | gzip,deflate                          |
    And the first request should have empty cookies list
