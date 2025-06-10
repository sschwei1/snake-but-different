
# Featurelist

## Game Mechanics
- Toggling walls on/off<br/> Walls will show up on the edge of the board if enabled
- Presets, for different difficulties
- Food editor<br/>Manage all food properties, add new food, remove existing ones
- Increase snake speed per collected food<br/>The snake speed value will be increased by a factor each time a food is consumed. This value is the same for each food. This could be changed to be bound per food or per size increase.

## Project Structure
### OOP-Principles
- Encapsulation, fields are never public, only available through getters and setters
- Inheritance + Polymorphism, `AbstractEntity` includes some base logic, `SnakePartEntity`, `FoodEntity`, `WallEntity` and others contain specific logic for their type
- Simple form of dependency injection (`SnakeGame` handles initializing of all classes and passes down references if needed for example for the `Renderer`)
- Single Responsibility Principle for pretty much all classes

### Design Patterns
- Factory Pattern for `GameSettings` and creation of some other objects (e.g.: `Point2D`)
- Strategy Pattern in form of `AbstractRenderer` can easily be swapped out for a different renderer.<br /> Originally it was intended to switch to an open world mode, however this was not implemented as of now.
- Observer Pattern for `SnakeGame`, events are emitted, where the controller of the UI can subscribe to different events to trigger according UI changes.
- Command Pattern for different events happening in the game (Entities which are consumed will trigger different events)

## Performance (see design decisions below)
- [Food spawning](#food-spawning)
- [Efficient Rendering](#efficient-rendering)

# Feature ideas - not implemented

Here are some features which were on my mind while developing, however due to time constraints, I wasn't able to implement them.

- Map editor<br/>Since the addition of walls, it would be nice to have a map editor, to customize walls on the map to make it harder to play
- Open World Mode<br/>The idea is to have a big map in the background, while the player always sees the snake in the center and only sees the current fields in an X big radius.
- Settings Persister<br/>A way to save/load settings and also remember the last used settings.
- Different Food Spawn Styles<br/>Currently the food will be spawned after consuming them, while this allows some flexibility, other ideas included timed food spawning.
- Handling for highscores<br/>This actually gave me the idea of preset game modes because highscores won't make sense if you can configure the points awarded for consuming foods.
- Wall-pass randomizer<br/>A way to randomly determine what wall the snake will spawn when passing through the side.
- Minesweeper Mode<br/>Also had a cool idea about mines spawning, sadly this was much to implemented and not prioritized.
- Performance Monitoring<br/>While I added some basic outs to monitor the performance of different parts in the code, the idea for generalized performance monitoring was there.
- GLog<br/>While this is actually implemented, it currently can only enable/disable global logging, the idea would be to specify which log types you want to see.

- Powerups

For further information about the ideas, see the [GitHub issues](https://github.com/sschwei1/snake-but-different/issues)

# Design Decisions
Here are some things I put much thought into while developing, it probably consumed a bit too much time, but I am generally happy with the results.

## Food Spawning
I compared 3 different approaches
- Custom Function
- Floyd's Algorithm
- Random Spawning (brute force)

### Custom Function
Basically the idea is to gather the position of all parts of the snake (unique per position and sorted after their position) and then generate an integer value between `0 - (fieldsize - differentSnakePositions)` and for each snake part where the integer equivalent of its positions is less than or equal to the generated value, we increment the value by 1. When the first snake position is greater than the food pos, we can break the loop. Now it ensured, that the value is not on a snake field.

### Floyd's Algorithm
The idea behind Floyd's algorithm is, that we create a list of available cells and take a random position from that list.<br/>
The list is created by filling a list with all possible positions and remove the occupied positions by the snake (or other entities).

### Random Spawning (brute force)
The most basic approach: generate a new position, untile no entity has the same position.<br/>
Easy to do, but feels wrong to do so. Will also get more inefficient the entites are on the field.

### Performance Comparison
Here we can see the performance of the different approaches for a field of 100x100 cells.<br />
We can see that the random generation is very efficient for almost all cases, Floyd's algorithm will outperform random generation if the field is filled with more than 99% of elements.
My algorithm is not bad for a low field population, however it will fall of at a field filled around 10%.<br/>

![performance_comparison.png](performance_comparison.png)

### Final implementation
In the end I decided for an approach similar to Floyd's algorithm, however, I won't create a list with all empty field each time I want to spawn a new food, instead the entity manager will keep track of them while registering/unregistering entities.<br/>
This will give us the performance of Floyd's algorithm with less overhead, however the memory consumption might rise a bit while running the game.

Brute-forcing the food position might be the most efficient way, however it feels wrong to do so.

## Efficient Rendering

### Why did I optimize this?
After adding some features to the game and playing around with different settings, I noticed heavy performance issues for large fields filled to 100% with entities. Since the used field sizes and also the amount of entities could theoretically happen in a real scenario, I knew I had to fix this.

### Problem and fix
The problem was that each render, all cells on the field are cleared to an empty state, then, we run through all entities and set the fields accordingly.

While this works somewhat good for nearly empty fields, it will cause performance issues, the more updates happen to entities. As far as I understood, the css styles will be recalculated each time the style of an element is updated, which would explain why the issue is happening for many entites.

So what was the fix?<br/>
Since we already have a single instance handling all entities, it now also keeps track of dirty entities, which we have 2 of a kind:<br/>
- `created` (needs to be drawn)
- `removed` (needs to be cleared)

After changing the renderer to only update dirty cells, we the performance issues are completely gone.

### Drawbacks
Even tho efficient render is cool, it has one big drawback:<br/>
These changes will not work 1:1 for the open world mode.

I wrote about the idea of this game mode above and this is probably the reason why this is not implemented yet. If we want to apply this logic to also work for the open world mode, we would also need to change the way the entity manager handles dirty fields.