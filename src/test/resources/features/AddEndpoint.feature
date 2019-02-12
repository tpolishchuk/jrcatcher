@ui @clear-db
Feature: Add Endpoint

  Background:
    Given a root page is opened
    When I press "Add Listener" button
    Then an endpoint addition form should be shown

  Scenario: Add a valid endpoint
    When I add "app_1" endpoint
    Then an endpoint addition form should be hidden
    And an endpoint "app_1" should be shown in endpoints list as active

  Scenario: Add an existing endpoint
    When I add "app_1" endpoint
    Then an endpoint addition form should be hidden
    When I press "Add Listener" button
    Then an endpoint addition form should be shown
    When I add "app_1" endpoint
    Then errors alert should be displayed
    And errors list should match the following errors list
      | Given endpoint already exists |

  Scenario Outline: Add an endpoint with invalid symbols in path
    When I add "<path>" endpoint
    Then errors alert should be displayed
    And errors list should match the following errors list
      | Only word characters [a-zA-Z_0-9] are allowed. |

    Examples:
      | path  |
      | app-1 |
      | äpp   |
      | ?app  |
      | /app  |
      | app 1 |

  Scenario: Add an endpoint with blank path
    When I add "" endpoint
    Then errors alert should be displayed
    And errors list should match the following errors list
      | Path value should not be blank.                     |
      | Only word characters [a-zA-Z_0-9] are allowed.      |
      | Path length should be between 1 and 255 characters. |

  @excluded
  Scenario: Add an endpoint with too long path
    When I add endpoint with path containing 255 characters
    Then errors alert should be displayed
    And errors list should match the following errors list
      | Path length should be between 1 and 255 characters. |
