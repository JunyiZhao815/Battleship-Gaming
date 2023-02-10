# Battleship-Game
It is a project from ECE651: Software Engineering from Duke University
This project uses java to write a battleship game, in aim to give us a brief introduction about software engieering design pattern.

There are four game modes: 
- human vs human
- human vs computer
- computer vs human
- computer vs computer
# Game start
It is a game running in linux. 
So the basic linux commands to start the game are:
```
cd ece651-sp23-jz423-battleship
./gradlew  installDist         
cd ./app
tee src/test/resources/input5.txt | ./build/install/app/bin/app | tee src/test/resources/output5.txt
```
Then you can find the game is on your screen!

First you have to choose the game mode.

Second you need to place all of your ships.

Third you can start choosing three actions: fire, move ship, sonar scan.

Have fun :star_struck:	
