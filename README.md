
# Yassir - Android coding challenge

A Simple Movies app 


## API Reference

#### Get movies list

```http
  GET /https://api.themoviedb.org/3/discover/movie?api_key={apiKey}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `apiKey` | `string` | **Required**. Your API key |

#### Get a movie details by id

```http
  GET /https://api.themoviedb.org/3/movie/{id}?api_key={apiKey}
```

| Parameter | Type     | Description                   |
| :-------- | :------- |:------------------------------|
| `id`      | `string` | **Required**. Movie Id        |
|`apiKey`  | `string` | **Required**. Your API key    |


## Tech Stack

**Architecture:** MVI, MVVM, Clean Architecture, Modularization by Feature

**Dependency Injection:** Dagger Hilt

**Threading:** Kotlin Coroutines

**Navigation:** Jetpack Navigation Component, Deep Links

**Unit Testing** Junit, Mockito

