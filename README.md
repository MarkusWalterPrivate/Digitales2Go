# Digitales2Go

### About
Digitales2Go is a Demo-Application that was designed and developed as a student project at Fraunhofer IAO, University Stuttgart.
The Application is for informing users about and allowing them to rate new articels about technologies, companies, trends and projects.
A group of 7 students worked for a semester in 2022 on this project, where I designed and developed most of the SpingBoot backend.

### Improvements
The code in this repository is from the end of the student project.
I kept on working on the project as part of a student Job (Hilfswissenschaftler), where additional features were added.
The sourcecode of that work cannot be disclosed though.

## Licence:
The Project has a custom licence agreement between Fraunhofer and the Authors.
For further questions, please contact markus-walter-steinbach@web.de





### Basic Architecture
- The Flutter Frontend gets data from the SpringBoot Backend which utalizes MariaDB for data persitancy
- Security is implemented with SpringSecurity
  - Bearer Tokens are recieved from the frontend 
  - JWT tokens are used for authentication
  - Authorization with 4 different roles:
    - Admin: can do everything
    - Creator: can create now articles
    - Moderator: can approve new articles so they become public
    - User: can read and rate articles


# Project Inititialization
## Backend 

### Prerequisites
- Install a JDK in at least version 17.
- Make sure that `JAVA_HOME` is set correctly to the root directory of your JDK. You can check with this command: `echo %JAVA_HOME%` (or `echo $JAVA_HOME` on Linux / Git Bash)
- Make sure that the JDK `bin` folder is added to your `PATH`.
- Install MariaDB with defauklt user 'root' and password 'root' and make sure Port 3306 is not occupied

### Usage 

- use `./gradlew bootRun` in bash to run the code locally
- you can then inspect the API via Swagger at localhost:8080/api/v2/swagger-ui/#/

## Frontend

### Prerequisites

Install [Flutter](https://docs.flutter.dev/get-started/install) and ensure that the root folder of its installation is added to your `PATH` and follow the instructions on the website

# Hint! 

Make sure that the command "flutter doctor" does not find any issues.

```Doctor summary (to see all details, run flutter doctor -v):
[√] Flutter (Channel stable, 2.10.5, on Microsoft Windows [Version 10.0.19043.1645], locale de-DE)
[√] Android toolchain - develop for Android devices (Android SDK version 33.0.0-rc3)
[√] Chrome - develop for the web
[√] Visual Studio - develop for Windows (Visual Studio Community 2019 16.3.9)
[√] Android Studio (version 2020.3)
[√] Connected device (3 available)
[√] HTTP Host Availability
```

• No issues found!

### Usage

```CMD
# run your flutter project
flutter run
```

