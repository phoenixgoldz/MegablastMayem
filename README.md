# MegaBlast Mayhem

**MegaBlast Mayhem** is a high-energy, competitive 2D action-battle game, reimagining classic Bomberman-style gameplay with modern innovations.  
Built from the ground up in Java with custom frameworks for pixel-precise gameplay, dynamic multiplayer, and advanced AI.

Led by **Trevor Hicks**, MegaBlast Mayhem delivers a true arcade experience, built for both solo and local multiplayer mayhem.

---

## 🎮 Features
- 1–4 Player Support (keyboard and full gamepad support)
- **Dynamic Controller Detection**: Seamless gamepad recognition (CRKD, Xbox, and others)
- **Adaptive Smart NPCs**: AI opponents that evolve and grow stronger after every match
- **Random Passive Abilities**: Every player and NPC gains unique boosts like Speed Surge, Bomb Acceleration, and Blast Resistance
- **Destructible Maps**: Blast through breakable walls with dynamic sprite slicing and bitmasking
- **Classic Power-Ups**: Bomb, Fire-Up, Speed Boost, Kick, and more
- **Procedural Map Support**: Load new maps instantly from simple `.csv` files
- **Retro Pixel Art and Soundscape**: Handcrafted visuals and sound effects for an authentic arcade feel
- **Smooth Animations and Transitions**: Professional game loop architecture (Java 17 & Maven)
- **Responsive UI/UX**: Designed for instant feedback and intuitive controls
- **Optimized for Competitive Play**: High tick-rate game logic targeting 144 FPS

---

## 🧱 Technology Stack
- Java 17
- Maven
- Java Swing Framework
- Jamepad (Gamepad Controller API)
- Java Sound API
- BufferedImage and Custom SpriteMap Loader
- Custom-built Collision and AI Systems

---

## 🗺️ Dynamic Map System
Maps are loaded via `.csv` blueprints allowing quick iterations and new battlefield designs:
- `H` = Indestructible Wall
- `S` = Destructible Wall
- `1–4` = Player Starting Positions
- `PB`, `PU`, `PM`, `PS`, `PP`, `PK`, `PT` = Power-Ups

Easy to mod, expand, and iterate new level designs.

---

## 🕹️ Controls Overview
Players are mapped to individual control schemes for smooth 1–4 player gameplay.

- **Keyboard Controls**:
  - P1: Arrow Keys + Slash for Bomb
  - P2: WASD + E for Bomb
  - P3: TGFH + Y for Bomb
  - P4: IJKL + O for Bomb

- **Controller Support**:
  - CRKD, Xbox, and any standard DInput/XInput controllers
  - Full analog stick and D-Pad movement support
  - Dedicated Bomb Planting button

Use **F1** anytime in-game to review control mappings.

---

## 🚀 Building and Running the Game

### Prerequisites
- Java Development Kit (JDK) 11 or newer
- Maven build system

### To Build and Launch:
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
