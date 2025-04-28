# MegaBlast Mayhem

**MegaBlast Mayhem** is a high-energy, competitive 2D action-battle game, reimagining classic Bomberman-style gameplay with modern innovations.  
Built from the ground up in Java with custom frameworks for pixel-precise gameplay, dynamic multiplayer, and advanced AI.

Led by **Trevor Hicks**, MegaBlast Mayhem delivers a true arcade experience, built for both solo and local multiplayer mayhem.

---

## 🎮 Features
- 1–4 Player Local Support (keyboard and full gamepad support)
- **Dynamic Controller Detection** (CRKD, Xbox, DInput, XInput)
- **Adaptive Smart NPCs** that evolve and grow stronger after every match
- **Random Passive Abilities**: Speed Surge, Bomb Acceleration, Blast Resistance
- **Destructible Maps** with dynamic sprite slicing and bitmasking
- **Classic Power-Ups**: Bombs, Fire-Up, Speed Boost, Kick, and more
- **Procedural Map Loading** from simple `.csv` files
- **Retro Pixel Art and Soundscape** for an authentic arcade feel
- **Smooth Animations and Transitions** (high-performance game loop, Java 17 & Maven)
- **Optimized for Competitive Play**: 144 FPS tick-rate target
- **Responsive UI/UX**: Designed for instant player feedback

---

## 💣 New Gameplay Systems
- **Chaos Bomb Mutations**: 20% chance when planting to spawn special bombs:
  - Gravity Bomb (pulls players)
  - Chain Bomb (spawns another bomb)
  - MegaBlast Bomb (massive double explosion)
  - Ghost Bomb (future stealth explosion)
- **Gravity Field Mechanics**: Gravity Bombs pull players dynamically based on distance
- **Chain Reaction Explosions**: Unexpected bonus bombs from Chain mutations
- **Chaos-Based Bomb Glow**: Visual color changes (Cyan, Yellow, Red, Light Gray, White)
- **Floor Pulse System**: The battlefield flashes orange during explosions for added intensity
- **Optimized Sound System**: Resolved music crash bugs and enhanced background management
- **Bomb Resizing and Collider Sync**: Bombs resized to 39x16 for cleaner visual scaling

---

## 🧱 Technology Stack
- Java 17
- Maven
- Java Swing Framework
- Jamepad (Controller API)
- Java Sound API
- BufferedImage and Custom SpriteMap Loader
- Custom-Built Collision and AI Systems
- Dynamic Background Pulse Effects
- Chaos Mutation Bomb Framework
- Gravity Field Interaction System

---

## 🗺️ Dynamic Map System
Maps are loaded via `.csv` blueprints allowing quick creation of new battlefield layouts:

| Symbol | Meaning |
|:------|:--------|
| `H` | Indestructible Wall |
| `S` | Destructible Wall |
| `1–4` | Player Starting Positions |
| `PB`, `PU`, `PM`, `PS`, `PP`, `PK`, `PT` | Various Power-Ups |

Easy to mod, expand, and design new levels without recompiling.

---

## 🕹️ Controls Overview

- **Keyboard Controls**:
  - Arrow Keys: Movement
  - Space: Place Bomb
  - F1: Toggle Legacy Titles HUD (if available)

- **Controller Controls**:
  - D-Pad or Left Analog Stick: Movement
  - X / A button: Place Bomb
  - L1 Button: Toggle Legacy Titles HUD (if available)

Supports CRKD, Xbox, and any standard DInput/XInput gamepads.

---

## 🚀 Building and Running the Game

### Prerequisites
- Java Development Kit (JDK) 11 or newer
- Maven build system

### To Build and Launch
```bash
mvn clean package
java -cp target/csc413-secondgame-blai30-1.0.jar Main


### Developer Notes
The primary launcher for the game is `GameLauncher.java`.  
`Main.java` is also available for splash screen or debug launching.

---

## 🧑‍💻 Credits
- **Trevor Hicks** — Core Development, Game Design, Programming, UI/UX, and Systems Architecture
- **Calvin Bryant** — Early Debugging and QA Assistance (2021–2022)

Sound effects and music are used under educational fair-use and/or public domain licensing.  
All sprites are either original creations or remastered assets for non-commercial and indie commercial use.

---

## 🗝️ License
This project is licensed under the [MIT License](LICENSE).
