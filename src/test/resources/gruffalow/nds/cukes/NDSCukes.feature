Feature: Basic setup and running of the NDS system

  Scenario: Default application running is setup correctly
    Given The NDS system is started
    Then the NDS Engine exists
    And the NDS Engine is setup with the default codec

  Scenario: Local copy codec produces the same output as the input
    Given The NDS system is started with the local copy codec
    When a file is encoded
    Then the output file is identical to the input file