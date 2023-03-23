# Digitales2Go

Digitales2Go is a Demo-Application that was designed and developed as a student project at Fraunhofer IAO, University Stuttgart.
The Application is for informing users about and allowing them to rate new Technologies and Business models.
A group of 7 students, worked for a semester in 2022 on this project, where I designed and developed most of the SpingBoot backend.

# Project Inititialization

## Backend 

> This is the DigiTales2Go project for the Backend with Spring boot

### Prerequisites
- Install a JDK in at least version 17.
- Make sure that `JAVA_HOME` is set correctly to the root directory of your JDK. You can check with this command: `echo %JAVA_HOME%` (or `echo $JAVA_HOME` on Linux / Git Bash)
- Make sure that the JDK `bin` folder is added to your `PATH`.
- Install MariaDB and make sure Port 3306 is not occupied

### Usage 

- use in bash

``` bash
#for development: build and run in live-reload mode
./gradlew bootRun
```

### Documentation

- General references: https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle
- Application properties: https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html

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
# Code Styleguide

https://google.github.io/styleguide/javaguide.html
