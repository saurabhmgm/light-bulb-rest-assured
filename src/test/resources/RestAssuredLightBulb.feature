Feature: Turn Lights On and Off
	IN ORDER TO use the bulb
	AS A user
	I WANT TO to be able to operate it

#  Just writing the basic tests to showcase how it's done

  Scenario Outline:User should be able to operate the light
	Given a user wants to operate the light bulb
	When the user switches <state> the bulb
	Then the bulb should turn <state>
	And the reported message should be <message>
	Examples:
	  | state | message 				|
	  | On    | Switch set successfully |
	  | Off   | Switch set successfully |

#There are 2 major bugs:
#	  1. There is no segregation of response messages for On/Off. In bot cases it says "Switch Set Successfully"
#	     If it is not coded properly it will give false positives
#	  2. Power values does not make any difference it can go tany Negative number, Alphanumeric etc
