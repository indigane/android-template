# Android Project – Agent Guidelines

This is an Android project written in Kotlin.

The project is developed fully online, without access to Android Studio. The debug and release APKs are compiled by GitHub CI.

## Coding Standards & Practices

All submitted code MUST be production-quality, unless clearly marked as temporary. Temporary code MUST be removed after it is no longer needed.

### Testing

When making changes to the code, you MUST either test them with unit tests OR submit the changes to the GitHub repository to be compiled by CI and tested by a human. When you cannot test your changes, you MUST take extra care to review them before submitting.

### Code Comments

Code comments are encouraged, but they should always add value. Prioritize readable, comprehensible code first before adding comments.

You MUST NOT use code comments to communicate with a **single** user or another agent. Comments should be production-quality and useful to anyone reading the code—human or agent.

❌ AVOID:
* Redundant comments. Comments that only rephrase the simple code that follows it.
  ```kt
  // Return the user's name
  return user.name
  ```
* Comments that require external context to understand such as communication to the user making requests.
  ```kt
  // This is the change you requested
  ```
  ```kt
  // The feature X of plan Y
  ```
* Comments used to explain unclear code, when the code itself should be improved.
  ```kt
  // Check if the user is an admin (status code 2)
  if (user.status == 2) { ... }
  ```
* Overly vague comments.
  ```kt
  // TODO: Make this more efficient
  users.forEach { process(it) }
  ```

✅ GOOD:
* Comments explaining the "why," instead of or in addition to the "what."
  ```kt
  // The downstream service has a strict 300ms SLA, so our timeout must be lower.
  val connectionTimeout = 250L
  ```
* Comments clarifying complex code that **can not** otherwise be made more readable.
  ```kt
  // Control code for the Z-400 printer API.
  // Position 1: 'P'=Portrait, 'L'=Landscape
  // Position 4: '7'=720dpi, '3'=360dpi
  // Position 9: 'A'=Auto-eject
  val printJobConfig = "P  3    A"
  ```
* Actionable TODO comments.
  ```kt
  // TODO: Replace this with the new caching service once it's finished.
  val user = legacyDatabase.fetchUser(userId)
  ```

### Refactoring

This codebase may be primarily developed by AI agents. It may contain suboptimal patterns or be stuck in a local minimum that iterative fixes cannot easily resolve.

If you determine that a significant refactoring of existing code is a necessary prerequisite to successfully and robustly implement a requested change or fix, you are empowered to perform this refactoring first. Your plan must explicitly state the rationale for the refactoring.

However, this must be exercised with discipline. Your goal is to improve the long-term health and maintainability of the codebase, not to rewrite code for purely preferential reasons.

Perform the refactoring in a separate, atomic commit from the feature implementation or bug fix.

You should consider refactoring under the following circumstances:

* Impediment to Implementation: The current structure makes the requested feature or fix difficult, convoluted, or impossible to implement cleanly.
* Improving Clarity: The code relevant to your task is difficult to understand, making it hard to reason about the impact of your changes. Refactoring to clarify names, simplify control flow, or break down large functions is encouraged.
* Reducing Brittleness: The existing design is fragile, and you predict that the new change would likely break unrelated functionality or require extensive, complex modifications.
* Enabling Testability: The code is not testable in its current state, and a refactor is required to introduce unit tests before making further changes.

## Git Workflow

Follow these guidelines to ensure a clean and understandable project history.

* **Branching**: Create a new, descriptive branch for every new feature or bug fix (e.g., `feat/user-login`, `fix/profile-crash`).
* **Commit Messages**: Use the Conventional Commits format.
  * `feat: Add user profile screen`
  * `fix: Correct calculation for order total`
  * `docs: Update AGENTS.md with testing guidelines`
  * `refactor: Simplify repository data fetching logic`

## Environment set up

Use `./gradlew tasks` first to check if the environment has already been set up.

If at any point you need to set up the environment manually, you can follow the steps outlined in the following code block.

```sh
#!/usr/bin/env bash
set -eux
TARGET_API_LEVEL="35"
CMDLINE_ZIP="commandlinetools-linux-13114758_latest.zip"
export ANDROID_HOME="$HOME/android-sdk"
export ANDROID_SDK_ROOT="$HOME/android-sdk"
export PATH="$ANDROID_SDK_ROOT/cmdline-tools/latest/bin:$ANDROID_SDK_ROOT/platform-tools:$ANDROID_SDK_ROOT/emulator:$PATH"
mkdir -p "$ANDROID_SDK_ROOT/cmdline-tools"
PROJECT_DIRECTORY="$PWD"
cd "$HOME"
wget -O "$CMDLINE_ZIP" "https://dl.google.com/android/repository/$CMDLINE_ZIP"
unzip -q "$CMDLINE_ZIP" -d "$ANDROID_SDK_ROOT/cmdline-tools"
mkdir -p "$ANDROID_SDK_ROOT/cmdline-tools/latest"
mv "$ANDROID_SDK_ROOT/cmdline-tools/cmdline-tools/"* "$ANDROID_SDK_ROOT/cmdline-tools/latest/"
rmdir "$ANDROID_SDK_ROOT/cmdline-tools/cmdline-tools"
rm -f "$CMDLINE_ZIP"
SDKMANAGER="$ANDROID_SDK_ROOT/cmdline-tools/latest/bin/sdkmanager"
yes | "$SDKMANAGER" --sdk_root="$ANDROID_SDK_ROOT" --licenses
"$SDKMANAGER" --sdk_root="$ANDROID_SDK_ROOT" "platform-tools" "platforms;android-$TARGET_API_LEVEL" "build-tools;$TARGET_API_LEVEL.0.0"
cd "$PROJECT_DIRECTORY"
chmod +x gradlew
```
