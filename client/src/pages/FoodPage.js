import React, { Component } from 'react';
import { server, api } from '../config.json';
import FoodList from '../components/FoodList';
import NewFoodForm from '../components/NewFoodForm';
import { Button } from 'react-bootstrap';
import Auth from '../auth.js';

/**
 * Page that concerns food (dishes). Shows all foods that a user has created, and lets him add new ones.
 */
class FoodPage extends Component {
    constructor(props) {
        super(props);

        this.state = {
            data: null,
            error: false,
            errorMsg: '',
            loading: true,
            showNewFood: false,
        };
    }
    componentDidMount() {
        this.fetchFoods();
    }
    fetchFoods = () => {
        const url = server + api.food;
        this.setState({loading:true});

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
                this.setState({error: true, loading: false});
                Auth.unSetCredentials();
            });
    }
    toggleNew = () => {
        if (this.state.showNewFood) {
            this.setState({showNewFood:false});
        } else {
            this.setState({showNewFood:true});
        }
    }
    addListItem = (item) => {
        let newdata = this.state.data;
        newdata.push(item);
        this.setState({
            data: newdata
        });
    }
    render() {
        if (this.state.loading) {
            return <p>LOADING..</p>;
        } else if (this.state.error) {
            return <p>Failed to load from server: { this.state.errorMsg }</p>;
        } else {
            return (
                <div>
                    <p>Food : Calories : Protein</p>
                    <FoodList data = {this.state.data} />
                    <Button onClick = { this.toggleNew }>
                        {
                            this.state.showNewFood ? 'Hide' : 'New'
                        }
                    </Button>
                    {
                        this.state.showNewFood
                            ?
                            <NewFoodForm
                                adder = { this.addListItem }
                                hider = { this.toggleNew }
                            />
                            :
                            null
                    }
                </div>
            );
        }
    }
}

export default FoodPage;
