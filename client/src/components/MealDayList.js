import React, { Component } from 'react';
import MealList from '../components/MealList';
import NewMealForm from '../components/NewMealForm';
import { Modal, ListGroup, ListGroupItem, Button } from 'react-bootstrap';
import Auth from '../auth.js';
import { server, api } from '../config.json';

/**
 * A list of days where meal data is contained. Component allows you to click a day and receive a modal with specific info on meals, and to add a new meal.
 */
class MealDayList extends Component {
    constructor(props) {
        super(props);

        this.state = {
            showModal : false,
            currentDay: null,
            showNewMeal: false,
            foods: null,
            loadingFoods: false,
            error: false,
        };
    }
    openModal = (obj) => {
        this.setState({
            showModal : true,
            currentDay: obj
        });
    }
    handleClose = () => {
        this.setState({ showModal : false });
    }
    fetchFoods = () => {
        const url = server + api.food;
        this.setState({loadingFoods:true});

        const headers = Auth.getHeaders();
        fetch(url, {headers:headers})
            .then(res => res.json())
            .then(res => {
                if (res.error) {
                    if (res.status === 401) {
                        console.log("NOT AUTHED");
                        this.setState({
                            error:true,
                            errorMsg: res.error,
                            loadingFoods:false
                        });
                        Auth.unSetCredentials();
                        return;
                    }
                }
                this.setState({
                    foods: res,
                    error: false,
                    loadingFoods: false,
                });
            })
            .catch(error => {
                console.log('Error: ',JSON.stringify(error));
                this.setState({error: true, loadingFoods: false});
                Auth.unSetCredentials();
            });
    }
    toggleNew = () => {
        if (!this.state.foods) { //only fetch once to avoid massive api calls
            this.fetchFoods();
        }
        if (this.state.showNewMeal) {
            this.setState({showNewMeal:false});
        } else {
            this.setState({showNewMeal:true});
        }
    }
    addListItem = (item) => {
        let newdata = this.state.currentDay;
        newdata.calories += item.food.calories;
        newdata.protein += item.food.protein;
        newdata.meals.push(item);
        this.setState({
            currentDay: newdata
        });
    }
    removeListItem = (obj) => {
        let newdata = this.state.currentDay;
        newdata.calories -= obj.food.calories;
        newdata.protein -= obj.food.protein;
        let newlist = newdata.meals
            .filter((item) => item.id !== obj.id);
        newdata.meals = newlist;
        this.setState({
            currentDay : newdata
        });
    }
    render() {
        return (
            <div>
                {
                    this.state.currentDay != null
                        ?
                        (
                            <Modal show={this.state.showModal} onHide={this.handleClose}>
                                <Modal.Header closeButton>
                                    <Modal.Title>{this.state.currentDay.date}</Modal.Title>
                                </Modal.Header>
                                <Modal.Body>
                                    <h4>Meals</h4>
                                    <p>Food : Calories : Protein</p>
                                    {
                                        this.state.currentDay.meals.length > 0
                                            ?
                                            <MealList remover = {this.removeListItem} data = {this.state.currentDay.meals} />
                                            :
                                            <p>No meals added for this day yet</p>
                                    }
                                    <Button onClick = { this.toggleNew }>
                                        {
                                            this.state.showNewMeal ? 'Hide' : 'New'
                                        }
                                    </Button>
                                    {
                                        this.state.showNewMeal
                                            ?
                                            <NewMealForm
                                                date = { this.state.currentDay.date }
                                                foods = { this.state.foods }
                                                adder = { this.addListItem }
                                                hider = { this.toggleNew }
                                            />
                                            :
                                            null
                                    }
                                </Modal.Body>
                            </Modal>
                        ) : null
                }
                <ListGroup>
                    {
                        this.props.data != null ? this.props.data.map(
                            (obj, i) =>
                            <Button 
                                onClick = {() => this.openModal(obj)}
                                key={i}
                            >
                                <ListGroupItem
                                    key={i}
                                    header={
                                        obj.date + 
                                            " : " + 
                                            (obj.weight || "none") + 
                                            " : " + 
                                            (obj.calories ? obj.calories : "none") + 
                                            " : " + 
                                            (obj.protein ? obj.protein : "none")
                                    }
                                >
                                    date : weight : calories : protein
                                </ListGroupItem>
                            </Button>
                        ) : null
                    }
                </ListGroup>
            </div>
        );
    }
}

export default MealDayList;
