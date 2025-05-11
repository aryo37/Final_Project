Feature: Test Automation Web UI

  @web
  Scenario: Sign up with valid username and password
    Given user is on sign up page
    When user input username with "aldi2025"
    And user input password with "ingat123"
    And user click sign up button
    Then user will redirect to homepage after sign up

  @web
  Scenario: Login with valid username and password
    Given user is on login page
    When user input to username with "jecko909"
    And user input to password with "jeje123"
    And user click login button
    Then user will redirect to homepage after login

  @web
  Scenario: Login with invalid username and password
    Given user is on login page
    When user input to username with "user_tidak_dipakai"
    And user input to password with "coba_coba"
    And user click login button
    Then A message appears "No alert present"

  @web
  Scenario: End to end test from login to checkout
    Given user is on login page
    When user input to username with "cicinew"
    And user input to password with "cicibaru"
    And user click login button
    Then user will redirect to homepage
    Given user is on the product page
    When user choose product "Nexus 6"
    And user click Add to cart button
    Then user should be redirected to Cart page with title "STORE"
    And user click "Cart" button
    And User should be redirected to "https://www.demoblaze.com/cart.html"
    And user click "Place Order" button
    And User fills the order form with:
      | Name         | Country   | City      | Credit card | Month | Year |
      | aldi     | Indonesia | Jakarta   | 1234567890  | 12    | 2025 |
    And user click "Purchase" button
    Then "Thank you for your purchase!" message should appear

  @web
  Scenario: Checkout with empty shipping information
    Given user is on login page
    When user input to username with "bobanew"
    And user input to password with "bobabaru"
    And user click login button
    Then user will redirect to homepage
    Given user is on the product page
    When user choose product "Nexus 6"
    And user click Add to cart button
    Then user should be redirected to Cart page with title "STORE"
    And user click "Cart" button
    And User should be redirected to "https://www.demoblaze.com/cart.html"
    And user click "Place Order" button
    When User attempts to checkout without filling required fields
    Then an alert with text "Please fill out Name and Creditcard." should appear


