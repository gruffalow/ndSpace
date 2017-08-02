Feature: Basic setup and running of the NDS system

  Scenario: Default application running is setup correctly
    Given The NDS system is started
    Then the NDS Engine exists
    And the NDS Engine is setup with the default codec