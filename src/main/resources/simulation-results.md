### Scenarios

**Optimistic scenario** - convo loads in 50 ms, threads load in 30 ms

**Realistic scenario** - convo loads in 80ms, threads load in 50 ms

**Pessimistic scenario** - convo loads in 200ms, threads load in 100 ms

**Catastrophic scenario** - convo loads in 500ms, threads load in 200 ms

### 5 convos

| Scenario   |      Sequential      |  Parallel streams | Coroutines |
|----------|:-------------:|------:|------:|
| Optimistic | 462 ms  | 202 ms | 110 ms
| Realistic | 728 ms      | 200 ms   | 115 ms
| Pessimistic | 1,56 s | 383 ms    | 213 ms
| Catastrophic | 3,5 s | 840 ms    | 715 ms

### 10 convos

| Scenario   |      Sequential      |  Parallel streams | Coroutines |
|----------|:-------------:|------:|------:|
| Optimistic | 897 ms | 241 ms    | 131 ms
| Realistic | 1,38 s  | 332 ms | 200 ms
| Pessimistic |  3,1 s     | 666 ms    | 418 ms
| Catastrophic | 7,1 s | 1,5 s    | 1,21 s

### 50 convos

| Scenario   |      Sequential      |  Parallel streams | Coroutines |
|----------|:-------------:|------:|------:|
| Optimistic | 4,34 s  | 653 ms | 561 ms
| Realistic |   6,8 s    |  1 s  | 912 ms
| Pessimistic | 15,4 s  | 2,21 s    | 1,96 s
| Catastrophic | 35 s |  5 s   | 4,5 s
    