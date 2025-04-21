# Megablast Mayhem

**Megablast Mayhem** is a chaotic, fast-paced, 2D Bomberman-inspired multiplayer game built in Java using Swing. This revival project was originally a class project, now reimagined and enhanced after a 4-year break.

## 🎮 Features
- 1 to 4 Player support (keyboard-controlled)
- Destructible and indestructible walls with dynamic sprite slicing
- Classic power-ups like Bomb, Fire-Up, Speed, and Kick
- Multiple map support via `.csv` files
- Retro-style UI screens and audio effects
- Background music and SFX with looping and transitions
- Custom hard wall tile logic with bitmasking for clean sprite corners

## 🧱 Technologies
- Java 17
- Maven
- Java Swing
- Audio via Java Sound API
- 2D Graphics with BufferedImage
- Custom sprite slicing and map loading system

## 🗺️ Map System
Maps are loaded from `.csv` files where characters like:
- `H` = Hard wall
- `S` = Soft wall
- `1`–`4` = Player spawn points
- `PB`, `PU`, `PM`, `PS`, `PP`, `PK`, `PT` = Power-ups

## 👾 Controls
Each player is mapped to unique keys. Press **F1** in-game to view controls, or refer to the controls table in the menu.

## 🚀 Getting Started

### Prerequisites
- Java 11+
- Maven

### To Run
```bash
mvn clean package
java -cp target/csc413-secondgame-blai30-1.0.jar Main
```

### Developer Note
The main launcher is `GameLauncher.java` and optionally `Main.java`.

## 🧑‍💻 Credits
Created by **Trevor Hicks** and **Calvin Bryant**

Sound effects and music used under educational fair-use and/or public domain licensing. Sprites are original or recreated for non-commercial use.

## 🗝️ License
[MIT License](LICENSE)
