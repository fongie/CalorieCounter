# Caloriecounter

This is a distributed application that lets users keep track of their calorie and protein intake, and keep statistics on meals eaten, weight per day, etc.

## Backend
The backend provides a REST API, which means that you could build many types of clients for it, such as web, mobile, etc. It is built in Java Spring with a mySQL database.
It provides the following API:
* POST /login
* GET /loggedin
* POST /foods
* GET /foods
* PUT /foods/:id
* POST /userdays (contains date and weight stats, will take pull requests on a better name..)
* GET /userdays
* PUT /userdays/:id
* POST /meals
* GET /meals
* PUT /meals/:id
* DELETE /meals/:id

Words with a colon (:) prepended are parameters. 
Note that registration is made by browsing directly to the backend's ip/registration. Read why in the documentation in the LoginController class.

## Client
The provided client is a React app. It could have been served by the same Spring server running the API, but since the task was to create a distributed application, I build the client separately. Just serve the React client with any simple web server built in any language or with create-react-app, and it will be able to communicate with the backend server.
Note that not all available API calls in the backend have an implementation in the client. It is intended that clients can choose which parts of the API they wish to implement.
