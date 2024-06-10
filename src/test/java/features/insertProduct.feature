Feature: insert a products using Post method API


  Scenario Outline: Validate post product api works correctly
    Given I hit the url to post the products api endpoint
    When I pass the url in the request
    And I pass the request body of product title "<ProductTitle>"
    Then I receive the response code as 200
    Examples:
      | ProductTitle     |
      | Anak ng kumander |


  Scenario Outline: Validate post product api response body
    Given I hit the url to post the products api endpoint
    When I pass the url in the request
    And I pass the request body of product title "<ProductTitle>"
    Then I receive the response body with as a "<id>"
    Examples:
      | ProductTitle     | id |
      | Anak ng kumander | 21 |