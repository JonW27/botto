# Botto
![Progress Status](https://img.shields.io/badge/Progress-in--dev-brightgreen.svg)
[![License](https://img.shields.io/packagist/l/doctrine/orm.svg)](LICENSE.md)

![botto logo](https://cdn.rawgit.com/Edmond120/finalProject/jonathan/botto/botto.svg)
## Preamble
🤖botto🤖 is an easy-to-setup framework that allows you to communicate with various devices (think IoT!) through channels like discord, fb messenger, and slack. It attempts to leave a minimal footprint in terms of core functionality, and has been developed with the power user in mind. botto can:
- get greetings
- run terminal commands
- understand human input (nlp, to-do)
- extend capabilities with plugins (to-do)
- encrypt your mnf.botto file to prevent credential stealing

### About
botto is created by Edmond and Jonathan Wong, respectively. It is licensed under the MIT license. All respecting third party programs included in this repository are distributed under their own licenses.

## Installation
### Pre-reqs
Our program has been tested with Java 8, and has not had any further testing on lower Java versions. It is safe to assume that it will probably be extremely buggy with older java versions, and throw many errors, so do so at your own risk.  
**TLDR**: Don't use a Java version lower than 8 unless you're desperate.  

In addition, if you want to use the discord functionality, please also have Chrome installed. Our program currently ships with only ChromeDriver and GhostDriver, and GhostDriver (PhantomJS) lacks some key ES6 bindings for it to work with Discord.
### Download
```bash
$ git clone https://github.com/Edmond120/finalProject.git
$ cd PATH/TO/WHEREEVER/YOU/DOWNLOADED/IT/
$ java Botto # You're all set!
```
### Setup

Follow the guided prompt to create your mnf.botto file, storing your credentials. When doing this, you may want to create an external account on one of the platforms to act as the bot, as talking to yourself can be quite boring (although we support it, for you privacy-minded individuals). After doing that run
`$ java Botto [program]`

## Plugins

Plugin development is currently in the works! Please check back later.

## Attribution

We currently make use of some *awesome* third-party libraries. These include [selenium webdriver](https://github.com/SeleniumHQ/selenium), [jBCrypt](https://github.com/jeremyh/jBCrypt), [Encryption](https://github.com/simbiose/Encryption) (which includes a Base64 class from the Android Open Source Project under the Apache License), [apache commons fileutils](https://commons.apache.org/proper/commons-io/), and [apache opennlp](https://opennlp.apache.org/). As noted above, these are **distributed under their own respective licenses.**

## Bugs we're aware of
We've got a long list. If you want to see our development please ask us to join our *asana* board.

**Note to teacher grading**: You've been sent an invite to join our asana team to see our bugs/tasks.

## Contributing / Pull Requests

We have an open policy regarding issues and pull requests! Please make sure that your issue has not been addressed already in some sort of FAQ page or closed issue, and give us a good reason to merge your pull request.
