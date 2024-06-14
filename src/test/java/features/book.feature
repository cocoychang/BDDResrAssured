Feature: Getting the list and order from book

  Scenario Outline: Validate that able to get the list of book
    Given I hit the url to get the list endpoint
    When I pass the url in the request with type "<booktype>"
    Then Received the response body with list of books and 200 status code
    Examples:
      | booktype |
      | fiction  |

  Scenario Outline: Validate that api able to get single book
    Given I hit the url to get the list endpoint
    When I pass the url in the request with bookId of "<bookid>"
    Then Received the response body with single book has current stock and 200 status code
    Examples:
      | bookid |
      | 4      |
      | 1      |
  Scenario Outline: Validate that api able to generate auth token thru endpoint
    Given I hit the url to get the list endpoint
    When I pass the url the api authentication endpoint
    And I pass the response body with client name "<client_name>" and email address "<email_address>"
    Then Received the response body with authenticationToken and 201 status code
    Examples:
      | client_name | email_address |
      | cocoy28     | c28@gmail.com |

  Scenario Outline: Validate that api able to submit order thru endpoint
      Given that user has authentication
      When Trigger endpoint with auth token and for orders request body with "<bookId>" and "<customer_name>"
      Then Received the response body with successfully created order and 201 status code
      Examples:
        | bookId | customer_name |
        | 4      | cocoy         |

    Scenario: Validate that the api able to get all orders
      Given that user has authentication
      When Trigger the url with endpoint of get orders
      Then Received response with all orders and 200 status code