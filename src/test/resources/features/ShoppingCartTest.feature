Feature: Shopping Cart Testing

  Scenario: Adding more than 2 product in shopping cart
    Given I'm on https://adoring-pasteur-3ae17d.netlify.app/mens.html page
    When Click on "Add to cart" button of more than 2 products
    Then Shopping Cart Must be Displayed
    And All selected products are diplayed in shopping cart
    And Total Price must be correct

  Scenario: Check input text box response from shopping cart
    Given I'm on https://adoring-pasteur-3ae17d.netlify.app/womens.html page
    When A item id added to shopping cart, its quantity is changed to 50
    Then New price is computed right

  Scenario: Verify if products can be removed from shopping cart
    Given I'm on https://adoring-pasteur-3ae17d.netlify.app/mens.html page
    And Adding more than 2 product to shopping cart
    When 2nd item is deleted
    Then Shopping Cart Must be Displayed
    And Item must be removed from the list
