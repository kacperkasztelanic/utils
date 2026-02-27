[![](https://jitpack.io/v/kacperkasztelanic/utils.svg)](https://jitpack.io/#kacperkasztelanic/utils)
[![](https://jitci.com/gh/kacperkasztelanic/utils/svg)](https://jitci.com/gh/kacperkasztelanic/utils)

# Utils

A collection of useful Java 8 utility classes with no external runtime dependencies (Lombok compile-only).

## Installation

### Gradle

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.kacperkasztelanic:utils:<version>'
}
```

### Maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.kacperkasztelanic</groupId>
    <artifactId>utils</artifactId>
    <version>VERSION</version>
</dependency>
```

## Modules

### General Utilities

| Class | Description |
|---|---|
| `CachedSupplier<T>` | Thread-safe `Supplier` that caches the result of a delegate and supports invalidation. |
| `Memo<T, U>` | Memoizes a `Function` using a `ConcurrentHashMap`. |
| `Predicates` | Combinator methods for predicates: `and`, `or`, and `distinctBy` (for use with `Stream.filter`). |
| `Optionals` | Convenience factory methods (`maybe`, `some`, `none`) and Java 9+ backports (`ifPresentOrElse`, `stream`). |
| `Iterables` | Operations on iterables, lists, sets, and maps: `foldLeft`, `head`, `zip`, `combine`, set algebra (`differentiate`, `subtract`, `intersect`), `invert`. |
| `MoreCollectors` | Stream `Collector` implementations that collect into insertion-order-preserving `LinkedHashMap`s. |
| `ThrowingSupplier<T, E>` | `Supplier`-like functional interface that allows throwing checked exceptions. |
| `UUIDs` | UUID generation, parsing, and validation utilities. |

### Functional / IO

| Class | Description |
|---|---|
| `IO<T>` | A simple IO monad for deferring and composing side-effectful computations (`map`, `flatMap`, `andThen`, `sequence`). |
| `Effect<T>` | Functional interface representing a side-effectful computation. |
| `Unit` | Represents the absence of a meaningful value (analogous to `void`). |

### Trampoline

| Class | Description |
|---|---|
| `TailCall<T>` | Trampoline interface for stack-safe recursive computations. |
| `Done<T>` | Terminal trampoline step holding a final result. |
| `Suspend<T>` | Suspended trampoline step deferring the next computation. |

### Tuples

| Class | Description |
|---|---|
| `Pair<L, R>` | Immutable 2-tuple with multiple accessor aliases (`left`/`right`, `first`/`second`, `key`/`value`). |

### Conditional Matching

| Class | Description |
|---|---|
| `Condition` | Functional pattern matching — evaluate a series of `Statement`s and return the first matching action. |
| `Statement<T>` | A boolean condition paired with a lazily-evaluated action. |

### Converters

| Class | Description |
|---|---|
| `Converter<S, T>` | Generic converter functional interface. |
| `DateToLocalDateConverter` | `Date` → `LocalDate` (configurable `ZoneId`). |
| `DateToLocalDateTimeConverter` | `Date` → `LocalDateTime`. |
| `DateToZonedDateTimeConverter` | `Date` → `ZonedDateTime`. |
| `LocalDateToDateConverter` | `LocalDate` → `Date`. |
| `LocalDateTimeToDateConverter` | `LocalDateTime` → `Date`. |
| `ZonedDateTimeToDateConverter` | `ZonedDateTime` → `Date`. |
| `DurationToLongConverter` | `Duration` → `Long` (nanoseconds). |
| `LongToDurationConverter` | `Long` (nanoseconds) → `Duration`. |

### Date/Time Parsing

| Class | Description |
|---|---|
| `LocalDateTimeParser` | Parses date, time, and date-time strings by auto-detecting their format. Supports DMY and MDY conventions. |
| `LocalDateTimePatternRecognizer` | Regex-based pattern recognizer for date/time strings. |

### Other

| Class | Description |
|---|---|
| `ExcelIndex` | Converts between 1-based numeric column indices and Excel-style alphabetical labels (e.g. 1 ↔ A, 28 ↔ AB). |
| `RomanNumbers` | Converts between Arabic and Roman numeral representations. |
| `ZipUtil` | Creates in-memory ZIP archives from a `Map<String, byte[]>`. |
| `UnicodeControl` | `ResourceBundle.Control` that loads `.properties` files as UTF-8. |

## License

[MIT](LICENSE) 
