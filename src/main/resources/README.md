# Property Marketplace Coursework

## Project title
Property Viewer

## Purpose of project
Property Viewer by FABSPointerException is a GUI for exploring properties in London that are available for temporary rental.

## Authors
Cosmo Colman (21090628),  Syraj Alkhalil (21007329),
Ayesha Dorani (1907316) & Burhan Tekcan (21013451)


## How to start this project
1. Run in a terminal from the same directory as the project directory `./gradlew run` on GNU/Linux and Mac, or `gradlew run` on Windows.
2. Alternatively, in IntelliJ IDEA, open the Gradle tab on the right and execute `run` under `Tasks` → `application`.

(Taken and modified from the FabricMC discord: https://discord.gg/v6v4pMv)

## How to import this project in IntelliJ IDEA
1. In the IDEA main menu, select `Import Project` (or `File` → `Open…` if you already have a project open).
2. Select the project's build.gradle file to import the project.
3. Go to `File` → `Project Structure` → `Project Settings` → `Project` and set `Language level` to `11 - Local variable syntax for lambda parameters`. (This is to ensure Java features that are incompatible with BlueJ won't be used, as BlueJ uses Java 11.)

(Taken and modified from the FabricMC wiki: https://fabricmc.net/wiki/tutorial:setup)

## How to export this project as a BlueJ compatible JAR file
1. Run in a terminal from the same directory as the project directory `./gradlew blueJJar` on GNU/Linux and Mac, or `gradlew blueJJar` on Windows.
2. Alternatively, in IntelliJ IDEA, open the Gradle tab on the right and execute `blueJJar` under `Tasks` → `build`.
3. The JAR should appear in `${projectDir}/build/libs`.
4. If the JAR opens in BlueJ but doesn't compile, you will need to delete all `.class` files in the BlueJ project's root directory, and if that still doesn't work, delete all `.class` files in the whole project.

(Taken and modified from the FabricMC discord: https://discord.gg/v6v4pMv)
