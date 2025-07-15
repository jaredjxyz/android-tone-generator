# Android 852Hz Pure Tone Generator

A simple Android application that generates a pure 852Hz tone with convenient access through home screen widget and quick settings tile.

## Features

- **Pure 852Hz Tone Generation**: Generates a clean 852Hz sine wave tone
- **Home Screen Widget**: Toggle tone on/off directly from your home screen
- **Quick Settings Tile**: Easy access from Android's quick settings panel
- **Simple Interface**: Clean, minimal design with play/stop functionality

## Screenshots

The app provides three ways to control the tone:
1. Main app interface with play/stop button
2. Home screen widget for quick access
3. Quick settings tile for system-level control

## Installation

### Prerequisites
- Android device running API level 21 (Android 5.0) or higher
- Android Studio for building from source

### Building from Source

1. Clone the repository:
```bash
git clone https://github.com/jaredjxyz/android-tone-generator.git
cd android-tone-generator
```

2. Open the project in Android Studio

3. Build and run the app:
   - Connect your Android device or start an emulator
   - Click "Run" in Android Studio or use `./gradlew installDebug`

## Usage

### Main App
1. Open the "Tone Generator" app
2. Tap the play button to start the 852Hz tone
3. Tap the stop button to stop the tone

### Home Screen Widget
1. Long press on your home screen
2. Select "Widgets" 
3. Find "Tone Generator" widget and add it to your home screen
4. Tap the widget to toggle the tone on/off

### Quick Settings Tile
1. Pull down the notification panel and expand quick settings
2. Tap the edit button (pencil icon)
3. Drag the "852Hz Tone" tile to your active tiles
4. Tap the tile to toggle the tone

## Technical Details

- **Target SDK**: Android 13 (API level 33)
- **Minimum SDK**: Android 5.0 (API level 21)
- **Language**: Kotlin
- **Architecture**: Single-activity app with service-based tone generation
- **Audio**: Uses Android AudioTrack for precise tone generation

## Project Structure

```
app/src/main/
├── AndroidManifest.xml          # App configuration and permissions
├── kotlin/com/example/tonega/
│   ├── MainActivity.kt          # Main app interface
│   ├── ToneManager.kt          # Core tone generation logic
│   ├── ToneTileService.kt      # Quick settings tile implementation
│   └── ToneWidgetProvider.kt   # Home screen widget provider
└── res/
    ├── drawable/               # Icons and graphics
    ├── layout/                 # UI layouts
    ├── values/                 # Strings, colors, themes
    └── xml/                    # Widget configuration
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is open source and available under the [MIT License](LICENSE).

## Why 852Hz?

852Hz is one of the frequencies in the Solfeggio scale, often associated with intuition and spiritual awakening in sound therapy practices. This app provides a simple way to generate this specific frequency for meditation, sound therapy, or testing purposes.