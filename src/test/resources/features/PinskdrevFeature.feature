#language: en
Feature: Pinskdrev-ru testing feature
#Scenarios for pinskdrev test


  #Get list of Pinskdrev-ru items
  Scenario: Get list of Pinskdrev-ru items
    Given I have a method which find a list of Pinskdrev-ru items
    When I connect to pinskdrev-ru table
    Then The list size is more then one