# Copilot Instructions

## Build & Test

```bash
./gradlew build          # compile + test + jar
./gradlew test           # run all tests
./gradlew jacocoTestReport  # test + coverage report (build/reports/jacoco/)

# Run a single test class
./gradlew test --tests "com.kkasztel.utils.IterablesTest"

# Run a single test method
./gradlew test --tests "com.kkasztel.utils.IterablesTest.someMethodName"
```

Tests run in parallel by default (configured in `src/test/resources/junit-platform.properties`).

## Architecture

This is a zero-runtime-dependency Java 8 utility library. Lombok is **compile-only** — it must never appear in `dependencies { implementation ... }`, only in the Lombok Gradle plugin (`io.freefair.lombok`) which handles annotation processing automatically.

Package layout mirrors the module groupings in `README.md`:
- `com.kkasztel.utils` — general utilities (`CachedSupplier`, `Memo`, `Predicates`, `Optionals`, `Iterables`, `MoreCollectors`, etc.)
- `com.kkasztel.utils.io` — IO monad (`IO`, `Effect`, `Unit`)
- `com.kkasztel.utils.trampoline` — stack-safe recursion (`TailCall`, `Done`, `Suspend`)
- `com.kkasztel.utils.tuple` — `Pair`
- `com.kkasztel.utils.conditional` — `Condition`, `Statement`
- `com.kkasztel.utils.datetime.converter` — `Converter` interface + bidirectional Date↔Java-time converters
- `com.kkasztel.utils.datetime.parser` — format-detecting date/time parser

## Key Conventions

**Static factory methods**: All instantiable classes use `staticName = "of"` or `staticConstructor = "of"` from Lombok (e.g., `CachedSupplier.of(supplier)`, `Pair.of(l, r)`). Constructors are private.

**Utility classes**: Non-instantiable utility classes (e.g., `Iterables`, `Predicates`) use `@NoArgsConstructor(access = PRIVATE)` from Lombok.

**Javadoc**: Every public class has a class-level Javadoc comment.

**`MoreCollectors` / `Iterables`**: Operations that produce maps always return insertion-order-preserving `LinkedHashMap`s.

**Java 8 target**: Source and bytecode must remain compatible with Java 8. Do not use Java 9+ APIs.

**Tests**: JUnit 5 (`org.junit.jupiter.api`). Use `assertThrows` for expected exceptions. No third-party assertion libraries — only `org.junit.jupiter.api.Assertions` static imports.

**No external runtime deps**: When adding new utilities, rely only on the JDK standard library. Lombok annotations are fine (compile-only).
