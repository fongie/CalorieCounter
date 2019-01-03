import React, { Component } from 'react';
import { ListGroup, ListGroupItem, Button } from 'react-bootstrap';
import Auth from '../auth.js';
import { server, api } from '../config.json';

class MealList extends Component {
    constructor(props) {
        super(props);

        this.state = {
            error: false,
        };
    }
    deleteMeal = (obj) => {

        const url = server + api.meal + "/" + obj.id;
        let header = Auth.getHeaders();

        const fetchOptions = {
            method: 'DELETE',
            headers: header
        };

        fetch(url, fetchOptions)
            .then(res => this.props.remover(obj))
            .catch(error => console.error('Error:', error));
    }
    render() {
        return (
            <ListGroup>
                {
                    this.props.data != null ? this.props.data.map(
                        obj =>
                        <ListGroupItem
                            key={obj.id}
                        >
                            {obj.food.name} : {obj.food.calories} : {obj.food.protein}
                            <Button onClick={() => this.deleteMeal(obj)}>Delete</Button>
                        </ListGroupItem>
                    ) : null
                }
            </ListGroup>
        );
    }
}


export default MealList;
