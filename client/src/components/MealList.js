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
    deleteMeal = (id) => {

        const url = server + api.meal + "/" + id;
        let header = Auth.getHeaders();
        //header.append("Content-Type", "application/json");

        const fetchOptions = {
            method: 'DELETE',
            headers: header
        };
        this.props.remover(id); //remove on uncommenting fetch

        // fetch(url, fetchOptions)
        //     .then(res => res.json())
        //     .then(res => {
        //         if (res.error) {
        //             this.setState({
        //                 error: true,
        //                 errorMsg: res.message,
        //             });

        //             return;
        //         } else {
        //             this.setState({
        //                 error: false,
        //                 errorMsg: '',
        //             });
        //             this.props.remover(id);
        //         }
        //     })
        //     .catch(error => console.error('Error:', error));
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
                            <Button onClick={() => this.deleteMeal(obj.id)}>Delete</Button>
                        </ListGroupItem>
                    ) : null
                }
            </ListGroup>
        );
    }
}


export default MealList;
