# JPacman

[![Build Status](https://travis-ci.com/SERG-Delft/jpacman.svg?branch=master)](https://travis-ci.com/SERG-Delft/jpacman)

## About

Pacman-like game used for teaching software testing.
It exposes students to the use of git, Gradle, JUnit, and mockito.

Parts of the code are well tested, whereas others are left untested intentionally. As a student in software testing, you can extend the test suite, or use the codebase to build extensions in a test-driven way. As a teacher, you can use JPacman to create your own testing exercises.

We have developed and are using this code at a software testing course at Delft University of Technology, The Netherlands. Teachers interested in seeing the exercises I use there are invited to contact me.

Other universities who have used this material include Antwerp, Mons, Eindhoven, and UBC (Vancouver).
At TU Delft, we use it in combination with GitLab as continuous integration and feedback server.

If you have any suggestions on how to improve JPacman, please do not hesitate to contact us, open an issue, or provide a pull request. Since testing is deliberately left as an exercise, pull requests that "solve" exercises or offer full coverage are less likely to be merged.

Main contributors:
* Arie van Deursen (versions 1.0-5.x, 2003-2013, updates to versions 6.x and further, 2014-...)
* Jeroen Roosen (major rewrite, version 6.0, 2014)

## Getting Started

### IntelliJ
1. Git clone the project
2. Open IntelliJ and create new project "from existing sources"
3. Select 'Gradle' in the following screen as external model, and click 'Next'
4. In the next screen, optionally adjust the Gradle options and click 'Finish'
5. To see JPacman in action: run `nl.tudelft.jpacman.Launcher`
5. To run the test suite in IntelliJ: right click on a test or directory -> `Run` or `Run ...Test`

### Command line
1. Git clone the project
2. To see JPacman in action: `./gradlew run`
3. To run the test suite and static analysis tools: `./gradlew check`
    1. For tests only run `./gradlew test`
    2. For static analysis tools only run `./gradlew staticAnalysis`
	 
