# Exercise for SOLID Principles and Clean Code

You find in this repo a **text adventure** implementation. It is an attempt to show proper usage of SOLID principles 
and Clean Code Rules. 

(Challenge me where you think to find violations! Coding is an iterative process, so this piece of code will
have areas for improvement, too.)


## What Is a Text Adventure?

A **text adventure** is a type of game dating back to the pre-graphical-UI times. It was a hugely popular genre in the 1980ies and 90ties, and some classical games from that time can still be played using browser-based emulators.

The idea is simple: The only interface to the game is your console. You find yourself in some unknown location, have to explore it, survive various threats, and solve a quest. Usually, you move in a grid: You can only go North, East, West, and South. During your quest, you need to pick up items and use them to open doors or to fight threats.

The only instructions you get are short sentences printed out on console. You type your input as command shortcuts into the console, e.g. that you want to go North, or that you want to pick up something you have found. As a reaction, the system tells you what you find as a result of your command. 

A typical sequence in a classical text adventure (immediately after starting the game) could look like this: 

```
You wake up from a long, deep sleep and find  yourself on a beach.
> go south
You can't go that way, there is the ocean.
> go west
You are still on the beach.
> go west
You are still on the beach.
> go west
In the sand you see a box.
> open box
It's locked.
> take box
Taken.
> go north
You are about to enter a dense jungle. It smells funny.
> go north
A mighty ork appears in some distance. You smell his scent of rotten meat.
> go north
The ork catches you and eats you.
GAME OVER
```

## The Rules For This Text Adventure

To make it a bit simpler, this text adventure was supposed to have exactly seven commands:

| Command | Meaning | 
|-------------|-------------| 
| n | go North | 
| e | go East | 
| s | go South | 
| w | go West | 
| t `<object>` | take `<object>` | 
| d `<object>` | drop `<object>` | 
| u `<object>` | use `<object>` | 


## Additional Rules for the Game (to make it simple)

* The dungeon is a square of 5x5 or larger.
* In this dungeon, there is you (the player) and at least one monster. 
* After each move, the dungeon is printed on the console. It shows the player's and monsters' positions. 
    (This is intended as a debug / development feature, which might be disabled later.)
* On each dungeon field, there can only be **one** creature - be it player or monster.
* Monsters move arbitrarily, at most one field in s/w/e/n direction. You can decide on a movement strategy for 
    each monster.  
* When the player is on a field adjacant to a monster, the monster will attack. 
* As a reaction, the player can fight by using a weapon ("u"), or move away.
* Weapons are located as items on the dungeon fields, where the player can collect them. Weapons can have
    different levels of power. Some weapons may only be used a limited amount of times. 
* If the player is adjacent to several monsters, using a weapon will attack (and hurt) all of them alike. 
* Monsters also attach each other.



