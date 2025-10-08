# NBA Players



Android app that lists NBA players from **balldontlie.io**. Tap a player for a detail screen, then jump to the team detail. Player headshots and team logos are fetched from **TheSportsDB**. Built with **MVVM**, **Paging 3**, **Retrofit/Moshi**, and **Material 3**.



## Features

- Infinite players list (+35 per page) with Paging

- Player detail (position, jersey, height/weight, college, country, draft)

- Team detail (city, abbreviation, conference, division)

- Remote images (headshots/logos)



## Tech

- Kotlin 1.9.24, Compose (Material 3), Navigation Compose

- Paging 3, Retrofit + Moshi, OkHttp logging

- Glide via Landscapist

- MVVM + simple repository + Service Locator



## Setup

Add API keys (preferred in `local.properties`, not committed):
 - BALLDONTLIE\_API\_KEY=your\_key
 - THESPORTSDB\_API\_KEY=your\_key

## Build \& Run

- Android Studio (JDK 17), AGP 8.6.x

- Debug: `./gradlew :app:assembleDebug`



