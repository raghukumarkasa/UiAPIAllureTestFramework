@API
Feature: Pizza Recommendation API

  @Smoke
  Scenario Outline: Get a specific pizza recommendation by ID
    When I send a GET request to "/api/pizza/{id}" for id <id>
    Then the response status should be 200
    And the JSON Response body matches the user schema "pizza-recommendation-schema.json"

    Examples:
    |id|
    |1|
    |47|
  @Smoke @Regression
  Scenario: Post a user's Pizza need to get a recommendation
    When I send a POST request to "/api/pizza" with body as "pizza-request-schema.json"
    Then the response status should be 200
    And the JSON Response body matches the user schema "pizza-recommendations-response-schema.json"
    Then the pizza is vegetarian
    And the response excludes ingredients "bacon, chicken"