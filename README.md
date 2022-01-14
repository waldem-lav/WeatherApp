
# Weather App

A simple project based on [Google's Recommended Architecture for Android Apps](https://developer.android.com/jetpack/guide).\
Built with MVI pattern + Coroutines + Hilt + Room + Android Architecture Components.\
The data is fetched from [OpenWeatherMap API](https://openweathermap.org/api).

<img alt='Sample' src="https://raw.githubusercontent.com/waldem-lav/WeatherApp/master/art/sample.gif" width="200" height="430"></br>

## Building

Add the following line to local.properties:

```
API_KEY={your_api_key}
```
## Libraries and tools used in the project

 - [Android Architecture Components](https://developer.android.com/topic/architecture?authuser=1) - A collection of libraries that help you design robust, testable, and maintainable apps.
 - [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - For managing background threads with simplified code and reducing needs for callbacks
 - [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - For [dependency injection](https://developer.android.com/training/dependency-injection)
 - [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
 - [Room](https://developer.android.com/training/data-storage/room) - The Room persistence library provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite.
 - [Moshi](https://github.com/square/moshi) - A modern JSON library for Android and Java.
## License

```
MIT License

Copyright (c) 2022 Uladzislau Laurukovich

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
