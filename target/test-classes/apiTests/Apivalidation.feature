Feature: Tests for /validate POST api

  Background: Set up the Service domain and API details
    Given I have the "localhost" domain
    And I have the "validate" api
    And the header "content-type" as "json"

  Scenario: Verify that validate POST call returns correct responses
    When I hit the POST api
    Then I get 200 response code
    And I get the status as "SUCCESS"

  Scenario Outline: Verify the validate POST call responses for valuedate and Tradedate variations
    When I set valuedate as "<valuedate>"
    And I set tradedate as "<tradedate>"
    And I hit the POST api
    Then I get <responsecode> response code
    And I get the status as "ERROR"
    And I get the messages as "<messages>"

    Examples:
      | valuedate  | tradedate  | responsecode | messages                                                                          |
      | 2017-08-18 | 2017-08-19 | 200          | Value date 2017-08-18 cannot be null and it has to be after trade date 2017-08-19 |
      | 2017-08-14 | 2017-08-14 | 200          | Value date 2017-08-14 cannot be null and it has to be after trade date 2017-08-14 |
      | 2017-08-13 | 2017-08-15 | 200          | Value date [2017-08-13] cannot fall on Saturday/Sunday                            |
      | 2017-08-12 | 2017-08-15 | 200          | Value date [2017-08-12] cannot fall on Saturday/Sunday                            |

  Scenario Outline: Verify that only given counterparties are acceptable
    When I set customer as "<counterparty>"
    And I hit the POST api
    Then I get <responsecode> response code
    And I get the status as "<status>"
    And I get the messages as "<messages>"

    Examples:
      | counterparty | responsecode | status  | messages                                                                               |
      | PLUTO1       | 200          | SUCCESS | null                                                                                   |
      | PLUTO2       | 200          | SUCCESS | null                                                                                   |
      | PLUTO3       | 200          | ERROR   | Counterparty [PLUTO3] is not supported. Supported counterparties: [[PLUTO2, PLUTO1]]   |
      | dummy123     | 200          | ERROR   | Counterparty [dummy123] is not supported. Supported counterparties: [[PLUTO2, PLUTO1]] |

  Scenario Outline: Verify that only ISO 4217 certified currency types are accpeted
    When I set ccypair as "<currencypair>"
    And I hit the POST api
    Then I get <responsecode> response code
    And I get the status as "<status>"
    And I get the messages as "<messages>"

    Examples:
      | currencypair | responsecode | status  | messages                          |
      | EUREUR       | 200          | SUCCESS | null                              |
      | USDINR       | 200          | SUCCESS | null                              |
      | ABCEUR       | 200          | ERROR   | Invalid currency pair [ABCEUR]    |
      | EURABC       | 200          | ERROR   | Invalid currency pair [EURABC]    |
      | ABCABC       | 200          | ERROR   | Invalid currency pair [ABCABC]    |
      | EURUSDEUR    | 200          | ERROR   | Invalid currency pair [EURUSDEUR] |

  Scenario Outline: Verify that only CSZurich is the accepted LegalEntity
    When I set legalentity as "<entity>"
    And I hit the POST api
    Then I get <responsecode> response code
    And I get the status as "<status>"
    And I get the messages as "<messages>"

    Examples:
      | entity   | responsecode | status  | messages                                                               |
      | CSZurich | 200          | SUCCESS | null                                                                   |
      | NASDAQ   | 200          | ERROR   | legalEntiry [NASDAQ] is not support. Supported legalEntiry: [CSZurich] |

  Scenario Outline: Verify that spot, Forward product types are the acceptable types
    When I set type as "<productType>"
    And I hit the POST api
    Then I get <responsecode> response code
    And I get the status as "<status>"
    And I get the messages as "<messages>"

    Examples:
      | productType | responsecode | status  | messages                                                                                             |
      | Spot        | 200          | SUCCESS | null                                                                                                 |
      | Forward     | 200          | SUCCESS | null                                                                                                 |
      | MutualFunds | 200          | ERROR   | ProductType [MutualFunds] is not support. Supported ProductType ids : [Forward, Spot, VanillaOption] |

  Scenario Outline: Verify that VanillaOption is acceptable Option product types
    When I update the existing body for type as VanillaOption
    And I set style as "<value>"
    And I hit the POST api
    Then I get <responsecode> response code
    And I get the status as "<status>"
    And I get the messages as "<message>"

    Examples:
      | value    | responsecode | status  | message                                                                          |
      | EUROPEAN | 200          | SUCCESS | null                                                                             |
      | AMERICAN | 200          | SUCCESS | null                                                                             |
      | CANADIAN | 200          | ERROR   | Invalid option style [ CANADIAN ]. Valid option styles are: [AMERICAN, EUROPEAN] |
      | INDIAN   | 200          | ERROR   | Invalid option style [ INDIAN ]. Valid option styles are: [AMERICAN, EUROPEAN]   |

  Scenario Outline: Verify VanillaOption trades for AMERICAN type
    When I update the existing body for type as VanillaOption
    And I set style as "<styleType>"
    And I set exerciseStartDate as "<excerciseStartDate>"
    And I set tradedate as "<tradeDate>"
    And I set expiryDate as "<expiryDate>"
    And I set premiumDate as "<premiumdate>"
    And I set deliveryDate as "<deliveryDate>"
    And I hit the POST api
    Then I get <responsecode> response code
    And I get the status as "<status>"
    And I get the messages as "<message>"

    Examples:
      | styleType | excerciseStartDate | tradeDate  | expiryDate | responsecode | premiumdate | deliveryDate | status  | message                                                                  |
      | AMERICAN  | 2017-08-27         | 2017-08-26 | 2017-08-28 | 200          | 2017-08-29  | 2017-08-30   | SUCCESS | null                                                                     |
      | AMERICAN  | 2017-08-23         | 2017-08-23 | 2017-08-23 | 200          | 2017-08-29  | 2017-08-30   | ERROR   | Expiry date 2017-08-23 has to be after trade date 2017-08-23             |
      | AMERICAN  | 2017-08-27         | 2017-08-27 | 2017-08-28 | 200          | 2017-08-29  | 2017-08-30   | ERROR   | excerciseStartDate 2017-08-27 has to be after tradeDate  2017-08-27      |
      | AMERICAN  | 2017-08-23         | 2017-08-27 | 2017-08-29 | 200          | 2017-08-29  | 2017-08-30   | ERROR   | excerciseStartDate 2017-08-23 has to be after the tradeDate 2017-08-27   |
      | AMERICAN  | 2017-08-30         | 2017-08-27 | 2017-08-29 | 200          | 2017-08-29  | 2017-08-30   | ERROR   | excerciseStartDate 2017-08-30 has to be before the expiryDate 2017-08-29 |
      | AMERICAN  | 2017-08-30         | 2017-08-27 | 2017-09-01 | 200          | 2017-08-29  | 2017-08-30   | ERROR   | Expiry date 2017-09-01 has to be before delivery date 2017-08-30         |
      | AMERICAN  | 2017-08-30         | 2017-08-27 | 2017-08-29 | 200          | 2017-09-01  | 2017-08-30   | ERROR   | Premium date 2017-09-01 has to be before delivery date 2017-08-30        |




