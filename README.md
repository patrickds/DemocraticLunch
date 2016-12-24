# DemocraticLunch

## Code Challenge

This is the result of a code challenge started 20/12/2016 and ended 23/12/2016, taking 4 days to complete.

## Language and Platform

This project was developed for android targeting API 11 as a minimum and using Kotlin as the language.

## Setup

The setup should be very straight forward. Just clone it, open with android studio and build.
Everything should work fine.

## Solution

Basically the app downloads data from GoogleWebServices and presents to the user. When the user votes in a restaurant, this restaurant is cached in a Realm database to know whether the user already voted or not in it, and the vote is pushed to firebase. All users receive the change and it updates the UI.
A service scheduled to run everyday at 12:30 p.m. gather the voting data from firebase and determines the restaurant elected. 
After that a notification is shown.

I really focused writing good code and making a good and testable structure so I had no time for UI :(

## Architecture

The approach taken was based on the [Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html) 
presenter by [Robert C. Martin](http://blog.cleancoder.com/) with MVP pattern for presentation.

This approach is a bit more verbose than usual but it makes the maintenance tasks very obvious and less error-prone.
The main difference is the introduction of use cases, which are the very user's actions, between the presentation and the domain models thus encapsulating
domain logic.

### Code Style and Highlights

I really enjoy writing readable code and finding ways to make Android's api more idiomatic, for example using kotlin extension functions which allows these kind of niceties:

Before
```java
val view = LayoutInflater.from(parent.context).inflate(R.layout.nearby_restaurants_list_item, parent, false)
```
After
````java
val view = parent.inflate(R.layout.nearby_restaurants_list_item)
````

Before
```java
Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
```
After
```java
view.showLongSnackBar(message)
```

Besides this, I like seeking new technologies and I'm a fan of functional programming. That said, the project heavily relies on RxJava
that gives a very good realiability and ease of development, Retrofit for easy RESTClients and it is also using dependency injection through Dagger2 for good modularity.

## What could've been done better

* The domain logic could be a little bit richier in some parts and not relying so much in the repository queries.

* There is some logic realying in the UI being disabled and I don't like it. At least the logic is contained entirely in the presenters so it is highly testable, but I still don't like it.

* I think would be better to have an WebAPI to centralize the operations, like ending a voting, notifying the users, and keeping a better control of the dates since the phone's dates aren't reliable.

* There is a new patter called MVI (Model View Intent) that could help making the UI more functional but I just read it briefly and don't know much about it.

## TODOS

* #### Awesome UI

* #### Place details (Ratings, photos, distance, travel time, maps)

* #### More Tests

* #### Settings

* ####  Charts ?

## Future and Review

This branch will remain untouched untill the code is reviewed but I really enjoyed doing this challenge and so I will finish it in another branch.
Perhaps in the day this is reviewed the other branch will be finished already, so feel free to check out there how it went.

#### Review

I would be very very happy if the reviewer told me his critics through issues or email (patrickds@outlook.com) so I can improve my work :)
