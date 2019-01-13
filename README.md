# About the project
This task gradually opens up a complex system that enables the collection of geographic information, the production of insights from this information, and the presentation of information in graphical tools. 
It generates information about traffic loads in real time by accumulating waze information. We will look at an application such as many practices, and improve it.
the project consist Ex2 Ex3 Ex4 

# Ex2 
In this project we have developed an infrastructure for representing geographic information containing:
Geom:
A package of geometry that includes points, lines, paths, circles, squares.

Coords: 
A package that enables the conversion of coordinates from global coordinates to localis coordinates and return them

Gis:
Geographic - geometric information, divided into layers, including reference to time, place text, color

Algorithms:
General algorithms such as: selection within rectangle, selection of distance, moving, cloning, rithms deletion, conversion of coordinates,and a multicsv reader the scan folder for csv files and convert them to kml files  

File_formats:
A: a package that allows saving and restoring geographic information, text formatting, and _formats

# Ex3

In this phase, the geographic system continues to evolve, with some interesting twists.

A new Packman game was added to the mix, play the good old packman game over a real picture of a map.

the main target of exercise was to build and algorithm that will finish the game as soon as possible.

![ariel](https://user-images.githubusercontent.com/44799500/50387675-2fd47c00-070b-11e9-8fdb-d3af3bd4fd41.png)

The game ends when all Fruits have been eaten by the packmen.

Finding the shortest path possible for a single player and a group of packmen has been build Achieving impressive results.

A Game can be built By several ways, including from a proper csv file, and manually from the Jframe (MyFrame) window.

Also , view game's rewind on google earth , with "Game2Kml". 

for Ex2 and Ex3 visit our reposetory --> https://github.com/amitznaft/GIS-project

# Ex4 
for this version of exricise we have added new features to answer the following challanges:

(1) from now , there is only one special personal player.
for this player , new algorithms and classes was added to plan a path , just like ex3.
(2) games have been modilfied to include ghosts( the same ghosts that you are familliar with , just like the clasic packman)
(3) In addition , game features black boxes , which are forrbidened to step on\in.

Games motivation is to keep it simple:
Don't touch any black boxes (costs one point)
Don't let any ghost touch you , because it kiils you and will cost you 20 points! (not really)..

Game can Played by 2 different ways:
From the class Main at the ex4_gui package:


mannualy:


just load one of the nine examples attached to repository ,locate your player's requested position and than choose "Run By Mouse" option.
where ever your mouse in pressed on map , is where your personal player will be headed to.


automaticly:


if you want to play automaticly , first locate your player's requested position.
Then choose the "Run automatic" option.
The game will be played as long as there are fruits that havent been eaten yet , or until time limitation (100 seconds).

In addition , through the Play object API , all results of score , are updated live thourgh 
server , which we can play against class mates.
When the game is finished , you can view a full time-lined statistics of game
and personal results history , which is nice to improve.
Also you can view your rank compared to other class mates to the specific game choosen by user.

In this project you will need to use the J'ars:
- Jdk 11
- download the folder "Ex4_OOP" and add the 3 J'ars files to the project.
