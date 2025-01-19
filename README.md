# LanPet android app

<img src="https://github.com/user-attachments/assets/9549bded-4270-4c95-8fd0-bd662b24d616" width="200" alt="온보딩-2">
<img src="https://github.com/user-attachments/assets/79f6296b-4d13-476c-b3fb-988583acb051" width="200" alt="온보딩-1">
<img src="https://github.com/user-attachments/assets/99c4b41c-7cfa-422e-b32e-db03ec852c24" width="200" alt="온보딩">

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

# Make module graph

```
1. Install Graphviz
   - brew install graphviz
2. Run gradle task
   - ./gradlew projectDependencyGraph
```
