# LanPet android app

# Tech Stack
1. Architecture
    - Multi module (https://developer.android.com/topic/modularization?hl=ko)
    - Clean architecture
    - MVVM pattern
2. Android
    - Kotlin
    - Jetpack Compose
    - Hilt
    - Coroutines
    - Retrofit2
    - Design system
    - Version catalogs
    - Room
    - Design system
    - Ktlint with Compose rule
3. Testing
    - Junit5
    - mockk
    - Turbine (https://github.com/cashapp/turbine)
    - Robolectric (https://developer.android.com/training/testing/local-tests/robolectric?hl=ko)

# Project structure   
Multi module based clean architecture
```
Root project 'LanPetApp'
├── Project ':app'
├── Project ':core'
│   ├── Project ':core:auth'
│   ├── Project ':core:common'
│   ├── Project ':core:designsystem'
│   ├── Project ':core:di'
│   ├── Project ':core:manager'
│   ├── Project ':core:navigation'
│   └── Project ':core:testing'
├── Project ':data'
│   ├── Project ':data:dto'
│   ├── Project ':data:repository'
│   └── Project ':data:service'
├── Project ':domain'
│   ├── Project ':domain:model'
│   ├── Project ':domain:repository'
│   └── Project ':domain:usecase'
└── Project ':feature'
    ├── Project ':feature:auth'
    ├── Project ':feature:free'
    ├── Project ':feature:landing'
    ├── Project ':feature:myposts'
    ├── Project ':feature:myprofile'
    ├── Project ':feature:profile'
    ├── Project ':feature:settings'
    └── Project ':feature:wiki'

Included builds:
└── Project ':build-logic'
```
