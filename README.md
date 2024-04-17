This is a Kotlin Multiplatform project targeting Android, iOS, Web, Desktop.

### Project Structure

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
* `/domain` is for shared code that is not UI-specific.

### Running the project
To use Gemini API you need to provide the API key. You can get the API key from [here](https://makersuite.google.com/app/apikey).
Then in `local.properties` add the following line to it:
```API_KEY=YOUR_API_KEY```

### UI

The UI is built using Jetpack Compose. The main UI code can be found in the `ui/chat` directory.

You can open the web application by running the `:composeApp:wasmJsBrowserDevelopmentRun` Gradle task.

## Screenshot and Video
<a href="files/recording.mp4"><img src="files/screenshot.png" height="500"></a>
