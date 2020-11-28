Feature: Test Lab_5

  Scenario: Check If Logo is present after search
    Given I'm on https://999.md page
    When input search word is computer
    Then website logo is displayed