import React, { Component } from 'react';
import { ControlLabel, FormGroup, FormControl, Button } from 'react-bootstrap';
import { server, api } from '../config.json';
import Auth from '../auth.js';

class NewWeightForm extends Component {
    constructor(props) {
        super(props);

        this.handleChangeFood = this.handleChangeFood.bind(this);

        this.state = {
            food: null,
            error: false,
        };
    }
    handleChangeFood(e) {
        this.setState({ food: e.target.value });
    }
    submitNewWeight = () => {
        const url = server + api.meal;
        let header = Auth.getHeaders();
        header.append("Content-Type", "application/json");
        const toPost = {
            food : this.state.food,
            date : this.props.date,
        };

        const fetchOptions = {
            method: 'POST',
            body: JSON.stringify(toPost),
            headers: header
        };

        fetch(url, fetchOptions)
            .then(res => res.json())
            .then(res => {
                if (res.error) {
                    this.setState({
                        error: true,
                        errorMsg: res.message,
                    });

                    return;
                } else {
                    this.setState({
                        error: false,
                        errorMsg: '',
                    });
                    this.props.adder(res);
                    this.props.hider();
                }
            })
            .catch(error => console.error('Error:', error));
    }
    render() {
        return (
            <form>
                <FormGroup
                    controlId="newMealForm"
                >
                    <ControlLabel>Select Food</ControlLabel>
                    <FormControl
                        componentClass="select"
                        type="text"
                        onChange={this.handleChangeFood}
                    >
                        <option value="select">select</option>
                        {
                            this.props.foods != null 
                                ?
                                this.props.foods.map((food) => <option key={food.id} value={food.id}>{food.name}</option>)
                                : 
                                null
                        }
                    </FormControl>
                    <Button onClick={ this.submitNewWeight }>Send</Button>
                </FormGroup>
                {
                    this.state.error ? <p> { this.state.errorMsg } </p> : null
                }
            </form>
        );
    }
}

export default NewWeightForm;
