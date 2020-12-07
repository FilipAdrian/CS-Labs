Feature: Check search functionality

  Scenario: Verify opening google page
    When Populate URL box with "https://www.google.co.in"
    Then Redirects to google main page

  Scenario: Verify number of search results on a single page
    Given The google page is loaded
    When Populate search box with "test"
    And Hit ENTER key
    Then Present list of 10 search result

  Scenario: Verify empty search
    Given The google page is loaded
    When Hit ENTER key
    Then Nothing happens

  Scenario: Verify irrelevant search
    Given The google page is loaded
    When Populate search box with "famebook"
    And Hit ENTER key
    Then Correct search "facebook" appears