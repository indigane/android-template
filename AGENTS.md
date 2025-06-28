### Environment set up and compiling

```sh
#!/usr/bin/env bash
set -eux
TARGET_API_LEVEL="35"
CMDLINE_ZIP="commandlinetools-linux-13114758_latest.zip"
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
./gradlew assembleDebug --stacktrace
```
