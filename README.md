# LanPet android app
### 반려동물을 좋아하는 사람들의 모임

<table>
          <th>로그인</th>      <th>자동로그인</th>      <th>인증</th>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/e1da2c4f-d122-413b-a4cb-5e0306ff80d6" width="200" alt="온보딩"></td>
    <td><img src="https://github.com/user-attachments/assets/266ec0f2-f8d5-46cd-9218-1083f30cab20" width="200" alt="자동로그인"></td>
    <td><img src="https://github.com/user-attachments/assets/34f6c41b-120e-4ce9-b6b1-8209629fefa6" width="200" alt="인증해제"></td>
  </tr>
     <th>사랑방리스트</th>      <th>사랑방프로세스</th>      <th>프로필프로세스</th>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/06389714-c71f-4722-adcf-6189f860e8b4" width="200" alt="사랑방 리스트"></td>
    <td><img src="https://github.com/user-attachments/assets/d30d21ab-f20d-4944-a3ed-650b787302aa" width="200" alt="사랑방 프로세스"></td>
    <td><img src="https://github.com/user-attachments/assets/098be209-2ba7-4a21-a170-283f4c3fcdcf" width="200" alt="프로필"></td>
  </tr>
</table>





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
