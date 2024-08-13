## Clean Architecture

- **Clean Architecture** is a software design philosophy that separates the elements of a software system into different levels of abstraction.
  source: [https://resocoder.com/2019/08/27/flutter-tdd-clean-architecture-course-1-explanation-project-structure/]
  video: [https://youtu.be/KjE2IDphA_U?si=6M1CWlyH2GpRlsQR]

## Project structure

- The app will be divided in multiple `features`

- Features required for multiple features are placed into the `core` folder

- Each feature contains 3 subfolders:

  - `data`: API or local storage

    - `datasources`
    - `models`
    - `implementations`: domain repositories implementations

  - `domain`: business logic (Use cases [or functions], entities)

    - `entities`
    - `repositories`
    - `usecases`

  - `presentation`: UI (Widgets, State managements [BLoC, ChangeNotifier])

    - `bloc`: state management
    - `pages`
    - `widgets`

    ** Note **: The domain layer should be platform independant (dependency inversion concept), repositories abstractions are a must
