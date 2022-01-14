# VoiceJump

VoiceJump is a plugin for the [IntelliJ Platform](https://github.com/JetBrains/intellij-community/) that leverages [Talon](https://talonvoice.com/)
to allow you to manipulate code with your voice. This project is a fork of [AceJump](https://github.com/acejump/AceJump) and is  
inspired by [Cursorless](https://github.com/pokey/cursorless-vscode).

- [Getting Started Guide](docs/README.md)

- [watch demo](https://www.youtube.com/watch?v=vpV6_L10Yqw)

## Installing

VoiceJump can be [installed directly from the IDE](https://www.jetbrains.com/help/idea/managing-plugins.html#install), via 
**Settings | Plugins | Browse Repositories... | 🔍 "VoiceJump"**.

## Building

*Prerequisites: [JDK 8 or higher](http://openjdk.java.net/install/).*

To build VoiceJump, clone and run the Gradle task [`buildPlugin`](https://github.com/JetBrains/gradle-intellij-plugin#tasks) like so:

* `git clone https://github.com/RonWalker22/VoiceJump && cd VoiceJump`
* For Linux and Mac OS: `./gradlew buildPlugin`
* For Windows: `gradlew.bat buildPlugin`

The build artifact will be placed in `build/distributions/`.

*Miscellaneous: VoiceJump is built using [Gradle](https://gradle.com/) with the [Gradle Kotlin DSL](https://docs.gradle.org/5.1/userguide/kotlin_dsl.html) and the [gradle-intellij-plugin](https://github.com/JetBrains/gradle-intellij-plugin).*

## Contributing

To start [IntelliJ IDEA CE](https://github.com/JetBrains/intellij-community) with VoiceJump installed, run `./gradlew runIde -PluginDev [-x test]`.

To just run [the tests](src/test/kotlin/AceTest.kt), execute `./gradlew test` - this is usually much faster than starting an IDE.

For documentation on plugin development, see the [IntelliJ Platform SDK](http://www.jetbrains.org/intellij/sdk/docs/).

## Release notes

Please [see here](/docs/CHANGES.md) for a detailed list of changes.


## Acknowledgments

Copied the simple server setup and general commands from [Intellij-voicecode](https://github.com/anonfunc/intellij-voicecode).
