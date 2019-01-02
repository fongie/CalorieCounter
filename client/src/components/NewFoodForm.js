import React, { Component } from 'react';
import { FormGroup, FormControl, Button } from 'react-bootstrap';
import { server, api } from '../config.json';
import Auth from '../auth.js';

//TODO validate protein and calorie fields
class NewFoodForm extends Component {
    constructor(props) {
        super(props);

        this.handleChangeName = this.handleChangeName.bind(this);
        this.handleChangeCalories = this.handleChangeCalories.bind(this);
        this.handleChangeProtein = this.handleChangeProtein.bind(this);

        this.state = {
            nameValue: '',
            calorieValue: '',
            proteinValue: '',
        };

    }
    handleChangeName(e) {
        this.setState({ nameValue: e.target.value });
    }
    handleChangeCalories(e) {
        this.setState({ calorieValue: e.target.value });
    }
    handleChangeProtein(e) {
        this.setState({ proteinValue: e.target.value });
    }
    submitNewFood = () => {
        const url = server + api.food;
        let header = Auth.getHeaders();
        header.append("Content-Type", "application/json");
        const toPost = {
            name : this.state.nameValue,
            calories : this.state.calorieValue,
            protein : this.state.proteinValue,
        };

        const fetchOptions = {
            method: 'POST',
            body: JSON.stringify(toPost),
            headers: header
        };

        fetch(url, fetchOptions)
            .then(res => res.json())
            .then(res => this.props.adder(res))
            .then(res => console.log('Success', JSON.stringify(res)))
            .then(() => this.props.hider())
            .catch(error => console.error('Error:', error));
    }
    render() {
        return (
            <form>
                <FormGroup
                    controlId="newFoodForm"
                >
                    <FormControl
                        type="text"
                        value={this.state.nameValue}
                        placeholder="Name"
                        onChange={this.handleChangeName}
                    />
                    <FormControl
                        type="text"
                        value={this.state.calorieValue}
                        placeholder="Calories"
                        onChange={this.handleChangeCalories}
                    />
                    <FormControl
                        type="text"
                        value={this.state.proteinValue}
                        placeholder="Protein"
                        onChange={this.handleChangeProtein}
                    />
                    <Button onClick={ this.submitNewFood }>Create</Button>
                </FormGroup>
            </form>
        );
    }
}

export default NewFoodForm;
