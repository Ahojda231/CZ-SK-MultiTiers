<div align="center">

# ⚔️ CZ/SK-MultiTIers `v1.0.0`

**The ultimate Fabric mod for Minecraft PvP players with native dual-tierlist support.**  
*Seamlessly integrates Global (PvPTiers, MCTiers, Subtiers) and Czechoslovak (CZ/SK) competitive PvP ranking systems.*

[![Fabric](https://img.shields.io/badge/ModLoader-Fabric-1f2328.svg?logo=fabric&logoColor=white)](https://fabricmc.net/)
[![Minecraft Versions](https://img.shields.io/badge/Minecraft-26.2_%7C_1.21.x-388e3c.svg?logo=minecraft&logoColor=white)](https://minecraft.net/)
[![Java](https://img.shields.io/badge/Java-21_%7C_25-f89820.svg?logo=openjdk&logoColor=white)](https://adoptium.net/)
[![Version](https://img.shields.io/badge/Release-v1.0.0-0969da.svg)](https://github.com/Ahojda231/CZ-SK-MultiTiers)
[![License](https://img.shields.io/badge/License-GPL--3.0-blue.svg)](LICENSE)

<br>

**Author:** [Saukr](https://github.com/Ahojda231) • **Original Project:** Fork of [PvPTiers/Tiers](https://github.com/PvPTiers/Tiers) by **Flavio6561**

</div>

---

## 📸 In-Game Showcase

### ⚙️ In-Game Configuration GUI (`/tiers -config`)
Configure all features, toggle individual tierlist integrations (Global & CZ/SK), adjust rendering, and customize HUD badges directly in-game using the `/tiers -config` command:

![In-Game Configuration GUI](assets/tiers-ukazka.png)
*Interactive settings GUI opened via `/tiers -config` or ModMenu*

<br>

### 🔍 Dual-Tab Player Profile Inspection (`Key: H` or `/tiers <player>`)
Inspect any nearby player or yourself in real time with instant tab switching between Global and Czechoslovak competitive rankings:

| 🌐 Global Leaderboards Tab (`[Globální]`) | 🇨🇿🇸🇰 CZ/SK Community Leaderboards Tab (`[CZ/SK]`) |
| :---: | :---: |
| ![Global Tiers Profile](assets/tiers-global.png) | ![CZ/SK Tiers Profile](assets/tiers-czsk.png) |
| *Displays MCTiers, PvPTiers & Subtiers rankings* | *Displays CZSKTiers.com, CZSK (b0tfleyz) & Subtiers rankings* |

---

## ✨ Key Features

- **🏆 6 Supported Leaderboard & Tier Systems:**
  - 🌐 **MCTiers** (`mctiers.com`)
  - 🌐 **PvPTiers** (`pvptiers.com`)
  - 🌐 **Subtiers** (`subtiers.net`)
  - 🇨🇿🇸🇰 **CZSKTiers.com** (Official Czechoslovak ranking via real-time REST API)
  - 🇨🇿🇸🇰 **CZSK Tiers (b0tfleyz)** (Czechoslovak community snapshot)
  - 🇨🇿🇸🇰 **CZSK Subtiers (b0tfleyz)** (Czechoslovak subtiers snapshot)

- **👀 Closest Player Inspection (`Key: H` or `/tiers <player>`):**  
  Instantly displays the 3D skin, overall rank, regional standing, points, and kit badges of the player nearest to you with tab switching between Global and CZ/SK rankings.

- **⚙️ In-Game Configuration (`/tiers -config`):**  
  Open the settings GUI directly with `/tiers -config` to customize visible tierlists, kit colors, badges, and render preferences.

- **🎯 Intelligent Auto-Detect Kit (`Key: Y`):**  
  Scans your current inventory hotbar and automatically determines the active PvP kit (Sword, Axe, Crystal, Pot, UHC, Mace, NethPot, etc.).

- **🔄 Fast Gamemode Switching (`Keys: U / I`):**  
  Cycle through competitive gamemodes on the fly without ever opening a menu.

- **🎨 Deep In-Game Integration:**
  - Custom Tab list tier badges and colors
  - Chat nametags and rank prefixes
  - Text Display entity badges
  - Dedicated config GUI (`/tiers -config` or ModMenu)

---

## 📥 Downloads (All 16 Minecraft Versions)

All pre-compiled and verified `.jar` packages are available in the [`jars/`](jars/) directory as well as the official [GitHub Releases](https://github.com/Ahojda231/CZ-SK-MultiTiers/releases/tag/v1.0.0).  
Simply download the JAR for your version and drop it into your `.minecraft/mods/` folder!

### 📦 All-In-One Release Bundles

For quick access to all 16 versions at once, download the pre-packaged bundle archive from the [GitHub Releases](https://github.com/Ahojda231/CZ-SK-MultiTiers/releases/tag/v1.0.0):

| Package Archive | Contents | Recommended For |
| :--- | :--- | :--- |
| [🗜️ **CZ-SK-MultiTIers-v1.0.0-All-Jars.rar**](https://github.com/Ahojda231/CZ-SK-MultiTiers/releases/download/v1.0.0/CZ-SK-MultiTIers-v1.0.0-All-Jars.rar) | All 16 `.jar` files (v1.0.0) | WinRAR users (ultra-compact ~5.4 MB solid archive) |
| [🗜️ **CZ-SK-MultiTIers-v1.0.0-All-Jars.zip**](https://github.com/Ahojda231/CZ-SK-MultiTiers/releases/download/v1.0.0/CZ-SK-MultiTIers-v1.0.0-All-Jars.zip) | All 16 `.jar` files (v1.0.0) | Universal ZIP extractors (~70 MB) |
| [🗜️ **CZ-SK-MultiTIers-v1.0.0-Complete-Folder.rar**](https://github.com/Ahojda231/CZ-SK-MultiTiers/releases/download/v1.0.0/CZ-SK-MultiTIers-v1.0.0-Complete-Folder.rar) | Full repository (all 16 jars, 8 source trees, assets, docs) | Developers & complete offline archive (~10.6 MB) |

### 🎮 Individual Mod JARs

| Minecraft Version | Java Requirement | Direct Download Link | Size |
| :--- | :---: | :--- | :---: |
| **26.2** | Java 25 | [📦 CZ-SK-MultiTIers-1.0.0+26.2.jar](jars/CZ-SK-MultiTIers-1.0.0+26.2.jar) | 5.19 MB |
| **26.1.2** | Java 25 | [📦 CZ-SK-MultiTIers-1.0.0+26.1.2.jar](jars/CZ-SK-MultiTIers-1.0.0+26.1.2.jar) | 5.19 MB |
| **26.1.1** | Java 25 | [📦 CZ-SK-MultiTIers-1.0.0+26.1.1.jar](jars/CZ-SK-MultiTIers-1.0.0+26.1.1.jar) | 5.19 MB |
| **26.1** | Java 25 | [📦 CZ-SK-MultiTIers-1.0.0+26.1.jar](jars/CZ-SK-MultiTIers-1.0.0+26.1.jar) | 5.19 MB |
| **1.21.11** | Java 21 | [📦 CZ-SK-MultiTIers-1.0.0+1.21.11.jar](jars/CZ-SK-MultiTIers-1.0.0+1.21.11.jar) | 5.19 MB |
| **1.21.10** | Java 21 | [📦 CZ-SK-MultiTIers-1.0.0+1.21.10.jar](jars/CZ-SK-MultiTIers-1.0.0+1.21.10.jar) | 5.19 MB |
| **1.21.9** | Java 21 | [📦 CZ-SK-MultiTIers-1.0.0+1.21.9.jar](jars/CZ-SK-MultiTIers-1.0.0+1.21.9.jar) | 5.19 MB |
| **1.21.8** | Java 21 | [📦 CZ-SK-MultiTIers-1.0.0+1.21.8.jar](jars/CZ-SK-MultiTIers-1.0.0+1.21.8.jar) | 5.19 MB |
| **1.21.7** | Java 21 | [📦 CZ-SK-MultiTIers-1.0.0+1.21.7.jar](jars/CZ-SK-MultiTIers-1.0.0+1.21.7.jar) | 5.19 MB |
| **1.21.6** | Java 21 | [📦 CZ-SK-MultiTIers-1.0.0+1.21.6.jar](jars/CZ-SK-MultiTIers-1.0.0+1.21.6.jar) | 5.19 MB |
| **1.21.5** | Java 21 | [📦 CZ-SK-MultiTIers-1.0.0+1.21.5.jar](jars/CZ-SK-MultiTIers-1.0.0+1.21.5.jar) | 5.19 MB |
| **1.21.4** | Java 21 | [📦 CZ-SK-MultiTIers-1.0.0+1.21.4.jar](jars/CZ-SK-MultiTIers-1.0.0+1.21.4.jar) | 5.19 MB |
| **1.21.3** | Java 21 | [📦 CZ-SK-MultiTIers-1.0.0+1.21.3.jar](jars/CZ-SK-MultiTIers-1.0.0+1.21.3.jar) | 5.19 MB |
| **1.21.2** | Java 21 | [📦 CZ-SK-MultiTIers-1.0.0+1.21.2.jar](jars/CZ-SK-MultiTIers-1.0.0+1.21.2.jar) | 5.19 MB |
| **1.21.1** | Java 21 | [📦 CZ-SK-MultiTIers-1.0.0+1.21.1.jar](jars/CZ-SK-MultiTIers-1.0.0+1.21.1.jar) | 5.19 MB |
| **1.21** | Java 21 | [📦 CZ-SK-MultiTIers-1.0.0+1.21.jar](jars/CZ-SK-MultiTIers-1.0.0+1.21.jar) | 5.19 MB |

---

## 📂 Repository Structure

The repository is organized into distinct directories for source code and compiled binaries:

```text
CZ-SK-MultiTiers/
├── assets/                    # Screenshots and branding assets used in documentation
│   ├── tiers-ukazka.png
│   ├── tiers-global.png
│   └── tiers-czsk.png
├── jars/                      # Pre-compiled ready-to-use JAR files for all 16 versions
│   ├── CZ-SK-MultiTIers-1.0.0+26.2.jar
│   ├── ...
│   └── CZ-SK-MultiTIers-1.0.0+1.21.jar
├── sources/                   # Complete source trees organized by Minecraft API era
│   ├── 26.2/                  # Source code for Minecraft 26.2 (RenderPipelines, Bed items)
│   ├── 26.1.x/                # Source code for Minecraft 26.1, 26.1.1, 26.1.2
│   ├── 1.21.11/               # Source code for Minecraft 1.21.11 (Identifier API)
│   ├── 1.21.9-1.21.10/        # Source code for Minecraft 1.21.9 & 1.21.10
│   ├── 1.21.6-1.21.8/         # Source code for Minecraft 1.21.6, 1.21.7, 1.21.8
│   ├── 1.21.5/                # Source code for Minecraft 1.21.5
│   ├── 1.21.2-1.21.4/         # Source code for Minecraft 1.21.2, 1.21.3, 1.21.4
│   └── 1.21-1.21.1/           # Source code for Minecraft 1.21 & 1.21.1
├── .gitignore
└── README.md
```

---

## 🛠️ Building from Source

To compile the mod from source for any version:

1. Clone the repository:
   ```bash
   git clone https://github.com/Ahojda231/CZ-SK-MultiTiers.git
   ```
2. Navigate into your desired version folder, for example `sources/1.21.4`:
   ```bash
   cd sources/1.21.4
   ```
3. Run the Gradle build:
   - **On Linux / macOS:**
     ```bash
     ./gradlew build
     ```
   - **On Windows:**
     ```powershell
     .\gradlew.bat build
     ```
4. The compiled JAR will be generated in `build/libs/`.

---

## 📜 Credits & Attribution

- **Mod Developer:** **Saukr** ([Ahojda231](https://github.com/Ahojda231))
- **Original Mod Creator:** **Flavio6561** ([PvPTiers/Tiers](https://github.com/PvPTiers/Tiers))
- **License:** GNU General Public License v3.0 ([GPL-3.0](LICENSE))
