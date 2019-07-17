Feature: Integration Testing for API

  Scenario: Activate new user
    Given New user with username test with password test123 has been registered successfully
    When When user test has been activated successfully
    Then Username test with password test123 can be used to login successfully
