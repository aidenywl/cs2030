# Parallelism

## Concurrent vs Parallel programming.

Most programs we have written runs sequentially. At any one time, there is only one instruction of the program running on a processor.

### Concurrency

A single core processor can only execute one instruction at one time -- this means that only one *process* (or less precisely speaking, one application) can run at any one time. Yet, when we use the computer it feels as if we are running multiple processes at the same time. The OS, behind the scenes, is actually switching between the different processes, to give the user an illusion that they are running at the same time.

We can write a program to run concurrently -- by dividing the computation into subtasks called threads. The OS can switch between different threads behind the scenes to give the user an illusion that the threads are running at the same time. Multi-thread programs are useful in two ways.

1. Unrelated tasks can be separated into threads and written separately.
2. Improves utilisation of the processor. For instance, if I/O is in one thread, and UI rendering is in another, then when the processor is waiting for I/O to complete, it can switch to the rendering thread to make sure that the slow I/O does not affect the responsiveness of UI.

### Parallelism

Parallel computing refers to the scenario where multiple subtasks are truly running at the same time -- either we have a processor that is capable of running multiple instructions at the same time, or we have multiple cores / processors and dispatch the instructions to the cores / processors so that they are executed at the same time.

All parallel programs are concurrent, but not all concurrent programs are parallel.


## Parallel Programming

If the task is too trivial or the fork threshold too low, the overhead of wrapping the computation in an object will create more work than the time saved by running it asynchronously.