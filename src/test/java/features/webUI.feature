Feature: Test Automation Web UI

  @web
  Scenario: Sign up with valid username and password
    Given user is on sign up page
    When user input username with "aldi2025"
    And user input password with "ingat123"
    And user click sign up button
    Then user will redirect to homepage

  @web
  Scenario: Login with valid username and password
    Given user is on login page
    When user input to username with "aldi2025"
    And user input to password with "ingat123"
    And user click login button
    Then user will redirect to homepage

  @web
  Scenario: Login with invalid username and password
    Given user is on login page
    When user input to username with "user_lain"
    And user input to password with "coba_coba"
    And user click login button
    Then A message appears "User does not exist."