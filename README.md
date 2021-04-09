## Welcome to Edible!

Edible is an app that allows you to get your favourite food in no time!

### Key Features
* View currently open restaurants
* Save the ones you see as your favourites, accessible through a dedicated favourites section
* Search for your favourite restaurants

This app is a continuous work in progress with more improvements on the way so keep an eye our for some updates. 

#### Technical stuff
* In this app we used the MVVM architecture with a very lose relation to MVI. 
* We made use of coroutines throughout the app as this is now the easier async pattern for use on androi.
* For Testing, we used robolectric, although as noted in our Dao tests, there is a bug with runBlockTesting and threading (links in the file) that doesn't allow us to test for more that one item in the database. 

For support, please drop us a line at system@kernelpanic.co.za!
