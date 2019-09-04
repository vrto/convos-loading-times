import kotlinx.coroutines.*
import java.lang.Thread.sleep
import java.util.stream.Collectors.toList

// model
class Thread(val id: Long, val convoId: Long)
class Convo(val id: Long, var threads: List<Thread> = emptyList())

// simulation parameters
val convoLoadingDelay = 80L
val threadsLoadingDelay = 50L
val threadsPerConvo = 10
val convoCount = 25

// common
fun getConvo(id: Long): Convo {
    sleep(convoLoadingDelay)
    return Convo(id)
}

fun getConvoThreads(convoId: Long): List<Thread> {
    sleep(threadsLoadingDelay)
    return (1..threadsPerConvo).map { Thread(it * convoId, convoId) }.toList()
}

fun getConvoWithThreads(id: Long): Convo {
    val convo = getConvo(id)
    convo.threads = getConvoThreads(id)
    return convo
}

// loading approaches
fun sequential() = measure("Sequential everything") {
    val convos = (1L..convoCount).map { getConvoWithThreads(it) }
    requireStuffLoaded(convos)
}

fun parallelStreams() = measure("Parallel streams") {
    val convos = (1L..convoCount).toList()
        .parallelStream()
        .map { getConvoWithThreads(it) }
        .collect(toList())

    requireStuffLoaded(convos)
}

fun coroutines() = GlobalScope.launch {
    suspendableMeasure("Coroutines") {
        val deferredConvos: List<Deferred<Convo>> = (1L..convoCount).map {
            async { getConvo(it) }
        }
        val deferredThreads: List<Deferred<List<Thread>>> = (1L..convoCount).map {
            async { getConvoThreads(it) }
        }

        // wait til everything is loaded
        val loadedConvos = deferredConvos.awaitAll()
        val loadedThreads = deferredThreads.awaitAll()

        // combine results
        loadedThreads.forEach { threads ->
            loadedConvos.find { threads[0].convoId == it.id }?.threads = threads
        }

        requireStuffLoaded(loadedConvos)
        println("Coroutines finished")
    }
}

// utils
fun measure(name: String, block: () -> Unit) {
    val start = System.nanoTime()
    block()
    val stop = System.nanoTime()
    println("$name took ${(stop - start) / 1000 / 1000} ms")
}

suspend fun suspendableMeasure(name: String, block: suspend () -> Unit) {
    val start = System.nanoTime()
    block()
    val stop = System.nanoTime()
    println("$name took ${(stop - start) / 1000 / 1000} ms")
}

fun requireStuffLoaded(convos: List<Convo>) {
    require(convos.size == convoCount)
    convos.forEach { require(it.threads.size == threadsPerConvo) }
}

fun main() {
    sequential()
    parallelStreams()
    coroutines()

    println("Main done, awaiting coroutines ...")
    sleep(2000) // worst case scenario, must be greater than T(sequential)
    println("Program finished")
}