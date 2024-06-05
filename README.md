# Sulfur Engine 1.1.0

## About
Sulfur is a cross-platoform open-sourced 2D and Psudeo-3D game engine that uses the swing graphics library.

## Getting Started
*There are no external dependencies for Sulfur*
1. Clone/Download the source code. Most of your programming will be done in the Main class and you have access to all of Sulfurs internal engine to work with
2. Look at the example projects to learn Sulfurs workflow. Each version of Sulfur comes with an example project.
3. Keep all of your assets in the resources folder in the src directory, this will help with exporting.
4. Make sure that you export to a runnable jar file, and if you kept your resources in the src folder, it should just run.

## Features

### Scene manager
Quick scene loading with support for multiple scenes.

### Rendering
Right now the engine is only capable of rendering squares, but you can put on a circle texture with alpha to render circles. You are able to render both solid colors and textures, using either .png or .jpg formats. The renderer also supports text rendering and line rendering

### Physics
Sulfur has a Rigidbody System with Square Colliders

### Sounds
Sulfur is able to play sounds using .wav or .mp3 formats.

### Ui
Right now sulfur can render any entity as a Ui Component

### Limitations
* There is no current native support for spritesheets but plans to add these features.
* It is meant only simple games, and is single threaded making it quite slow, but just fine for small scale projects. I did manage to get a real time raytracer working in the engine. (See commit v 0.2.1)

### Animations
Right now Sulfur is only able to render animations using .GIF format.

### Raycasting
Sulfur has simple Raycasting that is very easy to get up and running

## Games using Sulfur
[Mushroom Heist](https://cbeaver05.itch.io/mushroom-heist)
[Just Keep Swimming](https://cbeaver05.itch.io/just-keep-swimming)