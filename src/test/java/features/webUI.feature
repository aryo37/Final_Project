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

  @web
  Scenario: End to end test from login to checkout
    Given user is on login page
    When user input to username with "Aryo"
    And user input to password with "ingat123"
    And user click login button
    Then user will redirect to homepage
    When user choose product "Samsung galaxy s6"
    And user click "Add to Cart" button
    And user click "Cart" button
    And user click "Place Order" button
    And user fill shipping information with:
      | Name | Aldi |
      | Country   | Indonesia |
      | City      | Jakarta |
      | Credit Card | 4111111111111111 |
      | Month   | 12 |
      | Year   | 25 |
    And user click "Purchase" button
    Then user see confirmation message "Thank you for your purchase!"

  @web
  Scenario: Checkout with empty shipping information
    Given user is logged in as "aldi2025"
    And user has product "Samsung galaxy s6" in cart
    When user click "Place Order" button
    And user leave all shipping information empty
    And user click "Purchase" button
    Then A message appears "Please fill out Name and Creditcard."


