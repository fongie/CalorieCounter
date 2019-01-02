import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import FoodPage from './pages/FoodPage';
import MealPage from './pages/MealPage';
import WeightPage from './pages/WeightPage';
import Home from './pages/Home';

ReactDOM.render(
    <Router>
    <App>
        <Route exact={true} path="/" component={Home} />
        <Route path="/foods" component={FoodPage} />
        <Route path="/meals" component={MealPage} />
        <Route path="/weight" component={WeightPage} />
    </App>
    </Router>
    ,
    document.getElementById('root')
);

//localStorage.setItem("loggedin", "false");

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();
