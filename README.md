CRUD App with Reactive Repository
Overview

This app demonstrates a reactive CRUD architecture using Kotlin, Jetpack Compose, and a Mock API.
It allows users to create, read, update, and delete posts with real-time UI updates.

API Chosen: MockAPI

- MockAPI was selected because it provides a simple RESTful API for prototyping without setting up a backend.
- It supports all standard CRUD operations (GET, POST, PUT, DELETE).
- Ideal for demonstrating reactive UI updates and repository patterns.

Features

- List all posts
- Create new posts
- Edit existing posts
- Delete posts
- Reactive UI with automatic updates
- Loading and error handling in the ViewModel
- Optimistic updates for faster UI response

App Architecture

UI (Jetpack Compose)
        ↑
ViewModel (Manages UI State)
        ↑
Repository (Single Source of Truth, Reactive StateFlow)
        ↑
Network (Retrofit + Mock API)

Key Points:

- Repository maintains a StateFlow of posts (single source of truth).
- ViewModel handles loading and error states.
- UI is fully reactive; updates occur automatically when repository data changes.
- Supports optimistic updates for edit/delete operations.

Libraries and Frameworks Used
- Kotlin – Primary language
- Jetpack Compose – UI toolkit
- Kotlin Coroutines & Flow – Reactive state management
- Retrofit – Networking
- Koin – Dependency injection
- MockAPI – RESTful backend for prototyping

