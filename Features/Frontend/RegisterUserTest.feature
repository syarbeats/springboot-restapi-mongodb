Feature: Integration Testing for API

  Scenario: Register new user
    Given Register new user with username test with password test123 firstname Syarif lastname Hidayat email srf.hidayat@gmail.com and role ROLE_ADMIN using register API
    When When user has been registered successfully will go to register-successfully url
    Then User test with password test123 can be there on table list of user
    And User test with password test123 still cannot login successfully without activate it firstly
