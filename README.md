# Java OOP Exercises

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=flat-square)](LICENSE)
![Java](https://img.shields.io/badge/Java-17-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![OOP](https://img.shields.io/badge/paradigm-OOP-6f42c1?style=flat-square)
![Stream API](https://img.shields.io/badge/Stream%20API-0A66C2?style=flat-square)
![Dependencies](https://img.shields.io/badge/dependencies-none-2ea44f?style=flat-square)

**16 standalone Java 17 exercises walking from encapsulation and inheritance to interfaces, the repository pattern and the Stream API — each one a plain `src/main/java` tree with zero dependencies. ☕**

Coursework project — BSc in Applied Data Science, Universitat Oberta de Catalunya (UOC), Object-Oriented Design and Programming course.

## Objective

Solutions to the four graded assignments of the course, organized here by what each exercise implements. Each exercise follows a closed specification: class names, method signatures, constants and error messages were dictated by the course's JUnit 5 test suites, so the design of the public API is the course's — what is mine is the implementation. The UOC-provided test suites are **not** included in this repository because they are university course material.

Only my own `src/main/java` sources are published here. IDE metadata, Gradle wrappers, compiled classes and bundled libraries from the original submissions have been stripped out.

## Contents

### Java fundamentals
- `compound-interest-calculator/` — Compound-interest calculator with console I/O and input validation (runnable `main`).
- `impact-density-checker/` — Impact-density calculations over arrays (static methods, constants).
- `grid-spawn-probability/` — Coordinate validation and probability lookup on a 2D chunk grid.

### Class design and encapsulation
- `firewall-rule/` — `FirewallRule`: attribute validation through setters, checked exceptions with specific messages, `LocalDate` handling.
- `firewall-rule-extended/` — Extended `FirewallRule`: adds Javadoc, rule criticality/expiration checks, traffic applicability against source/destination/protocol/port tuples, and allowed/denied traffic percentages.
- `roman-numeral-converter/` — `RomanConverter`: bidirectional decimal/Roman-numeral conversion using parallel value/symbol tables, with input validation.

### Inheritance, composition and a repository pattern
- `castle-encapsulation/` — `Castle` / `Keep` (composition, validation).
- `castle-kingdom-aggregation/` — Adds `Kingdom` (aggregation of castles).
- `castle-kingdom-enum/` — Adds the `CastleType` enum.
- `student-repository-pattern/` — `Student` + `StudentRepository` interface + `InMemoryStudentRepository` backed by a `HashMap`, with a small demo `Main` (runnable).

### Abstraction, interfaces, exceptions and packages
- `space-game-core/` — `SpaceGame` / `SpaceShip` with custom exception classes and the `SpaceShipRolType` enum.
- `space-game-inheritance/` — Full hierarchy: abstract `Alien` and `EtherealAlien` with concrete subtypes, the `ShapeShifter` interface, and `Cargo` / `Cruiser` / `DeathStar` ship subclasses.
- `space-game-streams/` — The same domain reorganized into `pac4` and `pac4.ship` packages (cross-package visibility, `protected` members) and using the Stream API (`Collectors`).
- `notification-service-pattern/` — Notification services: `EmailNotifier` and `SmsNotifier` interfaces, `Notifier` extending both, and composition via `MultipleNotificationService` (runnable `Main`).

### `earlier-edition/` — Selected work from a previous edition of the course
The course was taken across two editions; the exercises above are the definitive (2025) submissions. The earlier (2023) edition used a different final-assignment domain that no longer exists in the current edition, so its two strongest exercises are kept here, clearly separated:

- `ecommerce-order-management/` — E-commerce domain: `User`/`Address`, a `Product` hierarchy (`PrintedBook`, `DigitalBook`, `Merchandising`) with `Billable` and `Manufacturable` interfaces, `Order` implementing `Comparable` with a secondary sort key, custom exception classes, and `OrderBatch`, which uses the Stream API (`filter`, `sorted`, `flatMap`, `mapToDouble`) for order delivery, largest-order retrieval and per-product income auditing.
- `deep-cloning/` — Deep copying via `Cloneable` across a `User` / `Address` / `PaymentCard` object graph.

## Tech stack

- Java 17 (uses `LocalDate`, enums, interfaces, custom exceptions, and the Stream API)
- Originally developed in IntelliJ IDEA against JUnit 5 test suites provided by the course (not included here)

## How to run

There are no build files — each exercise folder is a plain `src/main/java` tree that compiles standalone:

```bash
cd space-game-inheritance
javac -d out $(find src/main/java -name '*.java')
```

Most classes are library code that was exercised by the course's test suites, so they have no entry point. The runnable demos are:

| Exercise | Entry point |
|---|---|
| `compound-interest-calculator/` | `PAC1Ex1` |
| `student-repository-pattern/` | `edu.uoc.pac3.Main` |
| `notification-service-pattern/` | `edu.uoc.pac4.Main` |

For example:

```bash
cd student-repository-pattern
javac -d out $(find src/main/java -name '*.java')
java -cp out edu.uoc.pac3.Main
```

Fictional e-mail addresses used in demo code have been replaced with `example.com` placeholders.

## Repository structure

```
java-oop-exercises/
├── compound-interest-calculator/     # console I/O, input validation (runnable)
├── impact-density-checker/           # static methods over arrays
├── grid-spawn-probability/           # 2D grid lookups and validation
├── firewall-rule/                    # encapsulation, setters, checked exceptions
├── firewall-rule-extended/           # criticality, expiration, traffic matching
├── roman-numeral-converter/          # bidirectional conversion
├── castle-encapsulation/             # Castle/Keep composition
├── castle-kingdom-aggregation/       # + Kingdom aggregation
├── castle-kingdom-enum/              # + CastleType enum
├── student-repository-pattern/       # interface + in-memory impl (runnable)
├── space-game-core/                  # domain core, custom exceptions, enum
├── space-game-inheritance/           # abstract classes, interfaces, subclasses
├── space-game-streams/               # packages, protected members, Stream API
├── notification-service-pattern/     # interface composition (runnable)
│   └── (each folder is a plain src/main/java tree)
└── earlier-edition/                  # 2023-edition exercises not present in 2025
    ├── ecommerce-order-management/   # Order/OrderBatch domain with Stream API
    └── deep-cloning/                 # deep copying via Cloneable
```

The internal Java package names (`edu.uoc.pacN`, `pac4`) are kept exactly as submitted — they are part of the specification the test suites enforced.
