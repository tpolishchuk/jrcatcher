@ui @clear-db
Feature: Endpoint Actions Buttons

  Scenario: Activate an endpoint on root page
    Given inactive endpoint is added with path "app_1"
    When a root page is opened
    Then an endpoint "app_1" should be shown in endpoints list as inactive
    When an endpoint "app_1" is activated on root page
    Then an endpoint "app_1" should be shown in endpoints list as active

  Scenario: Deactivate an endpoint on root page
    Given active endpoint is added with path "app_1"
    When a root page is opened
    Then an endpoint "app_1" should be shown in endpoints list as active
    When an endpoint "app_1" is deactivated on root page
    Then an endpoint "app_1" should be shown in endpoints list as inactive
