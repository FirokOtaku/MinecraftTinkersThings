# Vein Feature

## Overview

The vein feature generator has the type `vein` and attempts to spawn a long, snaking "vein" of an ore in the world. It does this by choosing a starting point in a chunk, picking a direction to go and "simulating" a walk for the length of the vein, at each step deciding on a somewhat random new direction to move in. It then goes back and at each of those single block steps tries to generate a small node of the specified block(s).

## Parameters

As no canonical example of this generator exists, here are the parameters it takes and an explanation of them:

* minHeight - the minimum Y level a vein can spawn at or reach
* maxHeight - the maximum Y level a vein can spawn at or reach
* variation - the amount of variation in the size of the "node" generated at each "step" of the vein as well as the difference in length of the vein
* frequency - how frequently the feature will be generated
* minAttempts - minimum number of times to try and generate this feature
* maxAttempts - maximum number of times to try and generate this feature
* length - mean length of any given vein
* startFacing - which direction will the vein prefer to head in on spawn ? ("UP", "DOWN", "NORTH", "SOUTH", "EAST", "WEST", "RANDOM" (randomly choose), "VERTICAL" (choose either up or down randomly), "HORIZONTAL" (choose any but "UP" or "DOWN" randomly)
* size - how many blocks will spawn as a "node" of the vein at each "step" along its length ?
