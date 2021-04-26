Feature: Tests for /validateBatch api

  Background: Set up the Service domain and API details
    Given I have the "localhost" domain
    And I have the "validateBatch" api
    And the header "content-type" as "json"

  Scenario: Verify that validate POST call returns correct responses for 1 entity
    When I hit the POST api
    Then I get 200 response code
    And I get the status as "SUCCESS" for 1 request

  Scenario Outline: Verify batch validation with multiple entities
    And I create a new entity with <key> as "<value>" for batch request
    When I hit the POST api
    Then I get <responsecode> response code
    And I get the <responsekey1> as "<responsevalue1>" for 1 request
    And I get the <responsekey2> as "<responsevalue2>" for 2 request

    Examples:
      | key      | value  | responsecode | responsekey1 | responsevalue1 | responsekey2    | responsevalue2                                                                             |
      | customer | PLUTO2 | 200          | status       | SUCCESS        | status          | SUCCESS                                                                                    |
      | customer | PLUTO3 | 200          | status       | SUCCESS        | status;messages | ERROR;Counterparty [PLUTO3] is not supported. Supported counterparties: [[PLUTO2, PLUTO1]] |
      | ccypair  | ABCABC | 200          | status       | SUCCESS        | status;messages | ERROR;Invalid currency pair [ABCABC]                                                       |


