Feature: Integration Testing for API

  Scenario: Register new user
    Given Register new user with username test with password test123 email srf.hidayat@gmail.com and role ROLE_USER using register API
    When When user has been registered successfully
    Then We can populate data for username test using find-user-by-username api
    And user test with password test123 cannot login successfully
