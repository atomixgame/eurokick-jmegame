GoalWidth = 100

//use to set up the sweet spot calculator
NumSweetSpotsX = 13
NumSweetSpotsY = 6

//these values tweak the various rules used to calculate the support spots
Spot_CanPassScore = 2.0
Spot_CanScoreFromPositionScore = 1.0
Spot_DistFromControllingPlayerScore = 2.0
Spot_ClosenessToSupportingPlayerScore= 0.0
Spot_AheadOfAttackerScore = 0.0 

//how many times per second the support spots will be calculated
SupportSpotUpdateFreq = 1

//the chance a player might take a random pot shot at the goal
ChancePlayerAttemptsPotShot = 0.005

//this is the chance that a player will receive a pass using the arrive
//steering behavior, rather than Pursuit
ChanceOfUsingArriveTypeReceiveBehavior= 0.5

BallSize = 5.0
BallMass = 1.0
Friction = -0.015

//the goalkeeper has to be this close to the ball to be able to interact with it
KeeperInBallRange = 10.0
PlayerInTargetRange = 10.0

//player has to be this close to the ball to be able to kick it. The higher
//the value this gets, the easier it gets to tackle. 
PlayerKickingDistance = 6.0

//the number of times a player can kick the ball per second
PlayerKickFrequency = 8

PlayerMass = 3.0
PlayerMaxForce = 1.0
PlayerMaxSpeedWithBall = 1.2
PlayerMaxSpeedWithoutBall = 1.6
PlayerMaxTurnRate = 0.4
PlayerScale = 1.0

//when an opponents comes within this range the player will attempt to pass
//the ball. Players tend to pass more often, the higher the value
PlayerComfortZone = 60.0

//in the range zero to 1.0. adjusts the amount of noise added to a kick,
//the lower the value the worse the players get.
PlayerKickingAccuracy = 0.99

//the number of times the SoccerTeam::CanShoot method attempts to find
//a valid shot
NumAttemptsToFindValidStrike 5

MaxDribbleForce = 1.5
MaxShootingForce = 6.0
MaxPassingForce = 3.0


//the distance away from the center of its home region a player
//must be to be considered at home
WithinRangeOfHome = 15.0

//how close a player must get to a sweet spot before he can change state
WithinRangeOfSweetSpot = 15.0

//the minimum distance a receiving player must be from the passing player
MinPassDistance = 120.0
//the minimum distance a player must be from the goalkeeper before it will
//pass the ball
GoalkeeperMinPassDistance = 50.0

//this is the distance the keeper puts between the back of the net 
//and the ball when using the interpose steering behavior
GoalKeeperTendingDistance = 20.0

//when the ball becomes within this distance of the goalkeeper he
//changes state to intercept the ball
GoalKeeperInterceptRange 100.0

//how close the ball must be to a receiver before he starts chasing it
BallWithinReceivingRange = 10.0

//these (boolean) values control the amount of player and pitch info shown
//1=ON; 0=OFF
ViewStates = 1
ViewIDs = 1
ViewSupportSpots = 1
ViewRegions = 0
bShowControllingTeam = 1
ViewTargets = 0
HighlightIfThreatened = 0

//simple soccer's physics are calculated using each tick as the unit of time
//so changing this will adjust the speed
FrameRate = 60;


//--------------------------------------------steering behavior stuff
SeparationCoefficient = 10.0;

//how close a neighbour must be to be considered for separation
ViewDistance = 30.0;

//1=ON; 0=OFF
bNonPenetrationConstraint = 0
