Feature: Login functionality for Open Cart

  As a user of the OpenCart website
  I want to be able to login with my account
  So that I can access my account related features and manage orders

  Background:
    Given I am on the Open cart login page

  Scenario: Successful login with valid credentials
    Given I have entered a valid username and password
    When I click on the login button
    Then I should be logged in successfully

  Scenario Outline: Login unsuccesful with invalid credentials
    Given I have entered an invalid "<UserName>" and "<Password>"
    When I click on the login button
    Then I should see an error message indicating "<error_message>"
    Examples:
      | UserName          | Password        | error_message                                         |
      | invalid@email.com | invalidpassword | Warning: No match for E-Mail Address and/or Password. |
      | abccc             | validPassword   | Warning: No match for E-Mail Address and/or Password. |
      | valid@email.com   | abccc           | Warning: No match for E-Mail Address and/or Password. |