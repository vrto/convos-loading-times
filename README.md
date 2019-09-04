### Description

This is a small simulation of using three different approaches for loading convos+threads:

- sequential blocking code
- Java's native parallel streams
- Kotlin's coroutines

### Domain

Batch-loading a batch of conversations and then for each conversation loading its threads.

Let's assume that loading all convos at once is not possible (due to existing business logic).

### Running

Simply run [ConvoLoading#main](https://github.com/vrto/convos-loading-times/blob/master/src/main/kotlin/ConvoLoading.kt#L90) function.

### Results

See https://github.com/vrto/convos-loading-times/blob/master/src/main/resources/simulation-results.md