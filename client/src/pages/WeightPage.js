import React, { Component } from 'react';
import { Button } from 'react-bootstrap';
import WeightList from '../components/WeightList';
import NewWeightForm from '../components/NewWeightForm';
import Auth from '../auth.js';
import { server, api } from '../config.json';

class WeightPage extends Component {
    constructor(props) {
        super(props);

        this.state = {
            data: null,
            error: false,
            errorMsg: '',
            loading: true,
            showNewWeight: false,
            showInsertDate: false,
        };
    }
    componentDidMount() {
        this.fetchWeights();
    }
    fetchWeights = () => {
        const url = server + api.userday;
        this.setState({loading:true});

        const headers = Auth.getHeaders();
        fetch(url, {headers:headers})
            .then(res => res.json())
            .then(res => {
                console.log(res);
                if (res.error) {
                    if (res.status === 401) {
                        console.log("NOT AUTHED");
                        this.setState({
                            error:true,
                            errorMsg: res.error,
                            loading:false
                        });
                        Auth.unSetCredentials();
                        return;
                    }
                }
                //console.log(res);
                this.setState({
                    data: res,
                    error: false,
                    loading: false,
                });
            })
            .catch(error => {
                console.log('Error: ',JSON.stringify(error));
                Auth.unSetCredentials();
                this.setState({error: true, loading: false});
            });
    }
    toggleNew = () => {
        if (this.state.showNewWeight) {
            this.setState({showNewWeight:false});
        } else {
            this.setState({showNewWeight:true});
        }
    }
    addListItem = (item) => {
        let newdata = this.state.data;
        newdata.push(item);
        this.setState({
            data : newdata
        });
    }
    render() {
        return (
            <div>
                <Button onClick = { this.toggleNew }>
                    {
                        this.state.showNewWeight ? 'Hide' : 'New'
                    }
                </Button>
                {
                    this.state.showNewWeight
                        ?
                        <NewWeightForm
                            adder = { this.addListItem }
                            hider = { this.toggleNew }
                        />
                        :
                        null
                }
                <p>Date : Weight</p>
                <WeightList data = {this.state.data} />
            </div>
        );
    }
}

export default WeightPage;
