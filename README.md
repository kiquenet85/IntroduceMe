# IntroduceMe

An app which will enable you to introduce your experience into a new job.

# Motivations
As android evolves every day this is just a way of understanding more *Architecture components + coroutines + interactors.*

A simple app using material which should looks good, use modern approach and provide an easy way to create your own CV to be presented as introduction in your interview.

We will use this dependencies on app:

# Dependencies:

        //APP
        SUPPORT_LIBRARY_VERSION = "28.0.3"
        ANDROID_X_VERSION = "1.0.2"
        ANDROID_X_LEGACY_SUPPORT_VERSION = "1.0.0"
        ANDROID_X_LIFECYCLE_VERSION = "2.2.0-alpha01"
        ANDROID_X_FRAGMENT_VERSION = "1.1.0-alpha09"

        //KOTLIN
        KOTLIN_VERSION = '1.3.31'

        //Networking
        RETROFIT_VERSION = '2.5.0'
        GSON_VERSION = '2.8.5'

        //BACKGROUND
        COROUTINES_VERSION = '1.1.1'
        COROUTINES_ADAPTER_VERSION = '0.9.2'
        WORK_VERSION = '2.1.0-alpha02'

        //ROOM
        ROOM_VERSION = '2.1.0-beta01'

        //Utils
        DAGGER_VERSION = '2.22.1'
        PICASSO_VERSION = '2.71828'
        MATERIAL_VERSION = '1.0.0'
        CONSTRAINT_LAYOUT = '1.1.3'

        //TEST
        JUNIT_VERSION = '4.12'
        MOCKITOK_VERSION = '1.9.3'
        ESPRESSO_VERSION = '3.1.1'
        ROBO_ELECTRIC_VERSION = '4.2'
        RUNNER_VERSION = '1.1.1'
        TEST_CORE = '1.0.0'

# The current behavior of the app is just showing some mocking data.

The Flow is basically relying on DB information, but at the same time getting info from a service and updating accordingly when some important information of the user has an update.

# About Architecture:

- Layers:
    - a. DATA
    - b. Use cases (interactors)
    - c. View Models can use one or more interactors.
    - d. Controllers use ViewModels.

- Object Dependencies
    - Dagger to provide different instantiation of objects.
    - Using two scopes:
        - 1. Activity. (Just showing how to manage custom scope)
        - 2. AppContext.

- Background operations are using Coroutines.

- Network
    We rely on coroutines adapter and Retrofit to make call to services.

- Some Jetpack components are being used.
    LiveData, Room.

# Current simple behavior.

![](IntroduceMe.gif)
