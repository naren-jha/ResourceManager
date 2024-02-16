# Resource Manager Application

**Concepts used in this project :**

- Uses design patterns like - strategy, factory
- Uses Java multithreading concetps with concurrency handling
- Uses Java 8 features like - default method in interface, lambda

**Possible improvements :**
- Explore [visitor](https://refactoring.guru/design-patterns/visitor) design pattern for resource allocation strategy + task execution with multiple resources (with multiple resources, a task can be executed in two ways : one in a distributed manner and other as replica - distributed as in lets say if a task requires x cpu then x cpu is taken from multiple resources, and replica as in simply running multiple instances of the same task) 
