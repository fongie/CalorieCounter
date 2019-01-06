import React, { Component } from 'react';
import { FormGroup, FormControl, Button } from 'react-bootstrap';
import { server, api } from '../config.json';
import Auth from '../auth.js';
import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";

/**
 * A form component to add a new weight/date statistic.
 */
class NewWeightForm extends Component {
    constructor(props) {
        super(props);

        this.handleChangeWeight = this.handleChangeWeight.bind(this);
        this.handleChangeDate = this.handleChangeDate.bind(this);

        this.state = {
            weightValue: '',
            dateValue: new Date(),
            dateToSend: '',
            error: false,
            errorMsg: '',
        };
    }
    handleChangeWeight(e) {
        this.setState({ weightValue: e.target.value });
    }
    handleChangeDate(date) {
        let dateString = date.getFullYear() + "-" + date.getMonth() + "-" + date.getDate();
        this.setState({ 
            dateValue: date,
            dateToSend: dateString,
        });
    }
    submitNewWeight = () => {
        const url = server + api.userday;
        let header = Auth.getHeaders();
        header.append("Content-Type", "application/json");
        const toPost = {
            weight : this.state.weightValue,
            date : this.state.dateValue,
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
                    controlId="newWeightForm"
                >
                    <FormControl
                        type="text"
                        value={this.state.weightValue}
                        placeholder="Weight"
                        onChange={this.handleChangeWeight}
                    />
                    <DatePicker 
                        selected={this.state.dateValue}
                        onChange={this.handleChangeDate}
                        dateFormat="yyyy-MM-dd"
                        placeholderText="Date: Click or leave empty for today"
                    />
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
