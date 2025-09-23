# Android-Test-App
Welcome to the test application!

This application is designed to test multiple items. That's a developer or even a technical lead, should be able to complete.
You may not be able to complete all of the steps, but that is also a part of the test itself. Some of these items will take more time than offered. So please talk through your process and let us know what you wanted to do at the end of it if you didn't have enough time to complete the work.

## Overview
This is a aeroplane application. It has a lot of aeroplanes, and it shows them all within the list. 
This application is used by our frontline staff to know what type of plane they're flying on, and what amenities is that plane has. 

## Installation

### Java

This project is currently using Java 17. You can use your preferred method of installing Java or Android Studio built in Java SDK or one of the options below. 

<details>
	<summary>Java using SDKMAN</summary>

#### Install SDKMAN

Launch a new terminal and type in:

    $ curl -s "https://get.sdkman.io" | bash

Follow the on-screen instructions to wrap up the installation. Afterward, open a new terminal or run the following in the same shell:

    $ source "$HOME/.sdkman/bin/sdkman-init.sh"

Lastly, run the following snippet to confirm the installation's success:

    $ sdk version

You should see output containing the latest script and native versions:

    SDKMAN!
	script: 5.18.2
	native: 0.4.6

#### Installing Java

Find the latest stable Java version compatible with the project Java version specified above:

    $ sdk list java

You will see something like the following output:

    ...
    Temurin    |     | 22           | tem     |            | 22-tem
               |     | 22.0.1       | tem     |            | 22.0.1-tem
               |     | 21.0.3       | tem     |            | 21.0.3-tem
               |     | 21.0.2       | tem     |            | 21.0.2-tem
               |     | 21.0.1       | tem     |            | 21.0.1-tem
               |     | 17.0.11      | tem     |            | 17.0.11-tem
    ...

Install latest compatible version:

    $ sdk install java 17.0.11-tem
    
    Downloading: java 17.0.11-tem
    
    In progress...
    
    ######################################################################## 100.0%
    
    Installing: java 17.0.11-tem
    Done installing!

Now you will be prompted if you want this version to be set as **default**

    Do you want java 17.0.11-tem to be set as default?(Y/n)

Answering _yes_ (or _hitting enter_) will ensure that all subsequent shells opened will have this version of the SDK in use by default.

    Setting java 17.0.11-tem as default.

Refer to [SDKMAN Usage](https://sdkman.io/usage) to learn about install specific version, switching between versions and removing versions.

</details>

#### Android Studio IDE setup

1. Install the latest [Android Studio](https://developer.android.com/studio)
>[!IMPORTANT]
> Minimum required version is: Koala Feature Drop - 2024.1.2

2. Configure Android SDK by creating an `ANDROID_HOME` environment variable `export ANDROID_HOME=~/Library/Android/Sdk/` (This location is for MacOS)

## Architecture
It is very important that we make sure that we are developing to the architecture within this diagram. 
```mermaid
graph TD
    %% --- Style Definitions ---
    classDef presentation fill:#e3f2fd,stroke:#42a5f5,stroke-width:2px,color:#212121
    classDef domain fill:#e8f5e9,stroke:#66bb6a,stroke-width:2px,color:#212121
    classDef data fill:#fff3e0,stroke:#ffa726,stroke-width:2px,color:#212121
    classDef dependency fill:#f5f5f5,stroke:#9e9e9e,stroke-width:2px,color:#212121

    %% --- Main Architecture Layers ---
    subgraph "Presentation Layer (UI)"
        direction TB
        ComposableScreen["<b>Composable Screen / UI</b><br/><i>- Renders UI based on state</i><br/><i>- Hoists user events to ViewModel</i>"]
        ViewModel["<b>ViewModel</b><br/><i>- Holds and exposes UI state (e.g., StateFlow)</i><br/><i>- Survives configuration changes</i><br/><i>- Has no reference to Composables</i>"]

        ComposableScreen -- "Hoisted Events (e.g., onClick)" --> ViewModel
        ViewModel -- "UI State (e.g., StateFlow)" --> ComposableScreen
    end

    subgraph "Domain Layer (Business Logic)"
        direction TB
        UseCase["<b>Use Case / Interactor</b><br/><i>- Contains a single piece of business logic</i><br/><i>- Is a plain Kotlin/Java class</i><br/><i>- Reusable across ViewModels</i>"]
        RepoInterface["<b>Repository Interface (Contract)</b><br/><i>- Defines the contract for the data layer</i><br/><i>- Belongs to the Domain to enforce dependency inversion</i>"]
        DomainModel["<b>Domain Model</b><br/><i>- Represents core business objects</i><br/><i>- Plain Kotlin/Java data class</i>"]

        UseCase --> RepoInterface
        UseCase -- "Returns" --> DomainModel
    end

    subgraph "Data Layer (Data Sources)"
        direction TB
        RepoImpl["<b>Repository Implementation</b><br/><i>- Implements the Repository Interface</i><br/><i>- Single source of truth for data</i><br/><i>- Decides whether to fetch from local or remote source</i>"]
        RemoteDS["<b>Remote Data Source</b><br/><i>- Network calls (e.g., Retrofit API)</i><br/><i>- Returns DTOs (Data Transfer Objects)</i>"]
        LocalDS["<b>Local Data Source</b><br/><i>- Database queries (e.g., Room DAO)</i><br/><i>- Returns DB Entities</i>"]
        DataMapper["<b>Data Mapper</b><br/><i>- Converts DTOs and Entities into<br/>clean Domain Models</i>"]
    end

    %% --- Layer Interactions ---
    ViewModel -- "Executes" --> UseCase
    RepoImpl -.->|implements| RepoInterface
    RemoteDS -- "DTOs" --> DataMapper
    LocalDS -- "Entities" --> DataMapper
    DataMapper -- "Domain Models" --> RepoImpl
    RepoImpl --> RemoteDS
    RepoImpl --> LocalDS


    %% --- Class Styling ---
    class Activity,ViewModel presentation
    class UseCase,RepoInterface,DomainModel domain
    class RepoImpl,RemoteDS,LocalDS,DataMapper data

    %% --- Dependency Rule Visualization ---
    subgraph "Dependency Rule"
      direction LR
      P(Presentation) --> D(Domain)
      DA(Data) --> D
    end
    class P presentation
    class D domain
    class DA data

```

## Branch management 
It is also very important that we maintain our branch management as easy as possible. 
```mermaid
---
title: Android App Git Branch Model
---
gitGraph
   commit
   commit
   branch featureA
   commit
   commit
   checkout main
   merge featureA
   branch release
   commit
   commit
   branch bugA
   commit
   commit
   checkout release
   merge bugA
   commit tag:"v1.1.0"
   checkout main
   merge release
   
```

