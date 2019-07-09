Feature: Integration Testing for API

  Scenario: Register new user
    Given Register new user with username test with password test123 and role ROLE_ADMIN using register API
    When When user has been registered successfully
    Then user test with password test can invoke login APi successfully