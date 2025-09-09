@UI
Feature: Login functionality for Swag Labs Open Cart

  As a user of the Swag Labs OpenCart website
  I want to be able to login with my account
  So that I can access my account related features and manage orders

  Background:
    Given I am on the Swag Labs login page

  @Smoke
  Scenario: Successful login with valid credentials
    Given I have entered a valid username and password
    When I click on the login button
    Then I should be logged in successfully

  @Negative
  Scenario Outline: Login unsuccesful with invalid credentials
    Given I have entered an invalid "<UserName>" and "<Password>"
    When I click on the login button
    Then I should see an error message indicating "<error_message>"
    Examples:
      | UserName        | Password        | error_message                                                             |
      | locked_out_user | secret_sauce    | Epic sadface: Sorry, this user has been locked out.                       |
      | invaliduser     | secret_sauce    | Epic sadface: Sorry, this user has been locked out. |
      | standard_user   | invalidpassword | Epic sadface: Username and password do not match any user in this service |
