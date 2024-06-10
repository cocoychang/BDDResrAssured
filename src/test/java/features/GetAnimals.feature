Feature: Getting animals from the get Api

  Scenario: Validation of positive response code for Get Animals from the application
    Given i am an authenticated user
    When i hit the get animals api url
    Then i get 200 as the response code

  Scenario Outline: Validation of positive response body for Get Animals from the application
    Given i am an authenticated user
    When i hit the get animals api url
    Then i get animals in the response body of the api validate the value of "<animal>"
    Examples:
      | animal |
      | Dog    |

  Scenario: Validation of negative response code for Get Animals from the application
    Given i am an unauthenticated user
    Then i get 401 as the response code