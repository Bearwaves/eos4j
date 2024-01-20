# eos4j

![](https://img.shields.io/maven-central/v/com.bearwaves/eos4j)
![](https://img.shields.io/nexus/s/com.bearwaves/eos4j?server=https%3A%2F%2Fs01.oss.sonatype.org)
![](https://img.shields.io/github/license/Bearwaves/eos4j)

**eos4j** is a minimal Java wrapper around the Epic Online Services (EOS) SDK.
It provides a Java-native interface while aiming to conform very closely to
the structure of the EOS C SDK.

It was originally implemented to support the release of [Feud](
https://store.epicgames.com/en-US/p/feud-19b141
) on the Epic Games Store.

A simple [sample application](https://github.com/Bearwaves/eos4j-sample) showing
basic functionality is available.

## Compatibility

eos4j supports Java 8 and above, and has natives built for the following
platforms:

- Windows x64
- macOS x64
- macOS ARM64 (Apple Silicon)
- Linux x64

A loader based on LWJGL is included, but you are welcome to provide
your own.

## Showcase

Built your game using eos4j? Submit a pull request to put it here!

[![Feud](https://cdn1.epicgames.com/spt-assets/68be7d7f9c75454eba71957bbbbf2e66/feud-am8w9.jpg?h=240&quality=medium&resize=1&w=180)](https://store.epicgames.com/en-US/p/feud-19b141)

## Getting started

Releases are published to Maven Central, so you can use your favourite
dependency management tool to fetch it.

```groovy
// Gradle
dependencies {
    implementation "com.bearwaves:eos4j:1.1.0"
}
```

Snapshots are also published, if you want to be at the bleeding edge.
Make sure you have the OSS Snapshots repository declared.

```groovy
// Gradle
maven { url "https://s01.oss.sonatype.org/content/repositories/snapshots/" }
dependencies {
    implementation "com.bearwaves:eos4j:1.2.0-SNAPSHOT"
}
```

If you just want the JAR, see the [releases page](https://github.com/Bearwaves/eos4j/releases).

eos4j provides utilities to load the native libraries, but it **does
not include** the natives for the EOS SDK itself due to licensing
restrictions. You should download the SDK yourself from [EOS portal](
https://dev.epicgames.com/portal
) and ensure the library binaries are in your classpath. They are:

| Platform | File                         |
|----------|------------------------------|
| Windows  | EOSSDK-Win64-Shipping.dll    |
| macOS    | libEOSSDK-Mac-Shipping.dylib |
| Linux    | libEOSSDK-Linux-Shipping.so  |

Once your build is set up, you can initialise the library in your Java code
like so:

```java
import com.bearwaves.eos4j.EOS;

if (!EOS.loadLibraries()){
    throw new RuntimeException("Couldn't load EOS libraries");
}
```

From here on you can follow the EOS C SDK documentation and adapt it as you see fit.

## Documentation

eos4j is documented very minimally - this is a deliberate choice. Instead,
every effort has been made to have the structure of the library conform
as closely as possible to that of the EOS C SDK, meaning you should be
able to use the [official documentation](
https://dev.epicgames.com/docs/api-ref
) without issue.

Callbacks are implemented as interfaces and lambdas can be used.

If you find something which doesn't map neatly, is confusing, or would
benefit from additional documentation, please do open an issue.

## Completeness

The goal is for eos4j to implement the entirety of the EOS SDK, but it
isn't quite there yet. At the time of writing, the following are implemented.

### Platform interface

- Create and release
- Tick
- Get handles for implemented interfaces

### Logging interface

- Set callback
- Set level

### Auth interface

- Logging in
- Logging out
- Copying ID token
- Copying auth token

### Connect interface

- Logging in
- Creating a user
- Coping ID token
- Token expiry callback

### Stats interface

- Ingest stat
- Query stat
- Get stat count
- Copy stat by name
- Copy stat by index

### Achievements interface

- Unlock achievements

### Leaderboards interface

- Leaderboard definitions (query/count/copy)
- Leaderboard ranks (query/count/copy)
- Leaderboard user scores (query/count/copy)

### Ecom interface

- Catalog offers (query/count/copy)
- Entitlements (query/count/copy), entitlement token, redeem
- Query ownership, ownership token

If there's something missing that you need, please do open an issue. PRs
are very welcome.

## Acknowledgements

eos4j is _heavily_ inspired by the excellent
[steamworks4j](https://github.com/code-disaster/steamworks4j).
