Feature: Integration Testing for API

  Scenario: Reset User Password
    Given User with username user555 with password test123 want to change his password
    When When user user555 has changed his password to test555 successfully
    Then Username user555 can login with new password test555 successfully
