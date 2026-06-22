# AGENTS.md

This file provides guidance to coding agents when working with code in this repository.

## Build & Test

```bash
./gradlew build                                  # compile + test + jar
./gradlew test                                   # run all tests
./gradlew jacocoTestReport                        # test + coverage (build/reports/jacoco/)
./gradlew test --tests "com.kkasztel.utils.IterablesTest"               # single test class
./gradlew test --tests "com.kkasztel.utils.IterablesTest.methodName"    # single test method
```

Tests run in parallel by default (`src/test/resources/junit-platform.properties`), so tests must be independent and free of shared mutable state.

## Architecture

Zero-runtime-dependency Java 8 utility library published to JitPack/Maven (group `com.kkasztel`, base package `com.kkasztel.utils`). Built with Gradle (`java-library`).

Package layout (mirrors the module groupings in `README.md`):
- `com.kkasztel.utils` — general utilities (`CachedSupplier`, `Memo`, `Predicates`, `Optionals`, `Iterables`, `MoreCollectors`, `UUIDs`, `RomanNumbers`, `ExcelIndex`, `ZipUtil`, etc.)
- `com.kkasztel.utils.io` — IO monad (`IO`, `Effect`, `Unit`)
- `com.kkasztel.utils.trampoline` — stack-safe recursion (`TailCall`, `Done`, `Suspend`)
- `com.kkasztel.utils.tuple` — `Pair`
- `com.kkasztel.utils.conditional` — `Condition`, `Statement`
- `com.kkasztel.utils.datetime.converter` — `Converter` interface + bidirectional Date↔Java-time converters
- `com.kkasztel.utils.datetime.parser` — format-detecting date/time parser

## Key Conventions

- **Java 8 target**: source and bytecode must stay Java 8 compatible. Do not use Java 9+ APIs (e.g. `Optionals` exists to backport `ifPresentOrElse`/`stream`).
- **No external runtime deps**: rely only on the JDK standard library. Lombok is **compile-only** via the `io.freefair.lombok` plugin — it must never appear under `dependencies { implementation ... }`.
- **Static factories**: instantiable classes use Lombok `staticName`/`staticConstructor = "of"` with private constructors (e.g. `Pair.of(l, r)`). Non-instantiable utility classes use `@NoArgsConstructor(access = PRIVATE)`.
- **Map ordering**: `MoreCollectors` and `Iterables` operations that produce maps return insertion-order-preserving `LinkedHashMap`s.
- **Javadoc**: every public class has a class-level Javadoc comment.
- **Tests**: JUnit 5 (`org.junit.jupiter.api`). Use `assertThrows` for expected exceptions. No third-party assertion libraries — only `org.junit.jupiter.api.Assertions` static imports.
