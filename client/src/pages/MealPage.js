import React, { Component } from 'react';
import MealDayList from '../components/MealDayList';
import Auth from '../auth.js';
import { server, api } from '../config.json';

class MealPage extends Component {
    constructor(props) {
        super(props);

        this.state = {
            mealdata: null,
            daydata: null,
            error: false,
            errorMsg: '',
            loading: true,
            groupedInfo: null,
            showNewFood: false,
        };
    }
    componentDidMount() {
        this.fetchMeals();
    }
    fetchMeals = () => {
        const mealurl = server + api.meal;
        const userdayurl = server + api.userday;
        this.setState({loading:true});

        const headers = Auth.getHeaders();
        fetch(userdayurl, {headers:headers})
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

                    console.log(res)
                this.setState({
                    daydata: res
                });
            })
            .then(() => fetch(mealurl, {headers:headers})
                .then(res => res.json())
                .then(res => {
                    const sortedInfo = this.state.daydata.map(
                        (day) => {
                            const date = day.date;
                            const meals = res
                                .filter((item) => item.userDay.date === date)

                            const calories = meals.reduce((total, thisMeal) => total + thisMeal.food.calories, 0);
                            const protein = meals.reduce((total, thisMeal) => total + thisMeal.food.protein, 0);
                            const weight = day.weight;

                            const info = {
                                date,
                                weight,
                                calories,
                                protein,
                                meals,
                            }
                            return info;
                        }
                    );
                    console.log(sortedInfo);
                    this.setState({
                        mealdata: res,
                        error: false,
                        loading: false,
                        groupedInfo: sortedInfo,
                    });
                })
                .catch(error => {
                    console.log('Error: ',JSON.stringify(error));
                    Auth.unSetCredentials();
                    this.setState({error: true, loading: false});
                })
            )
            .catch(error => {
                console.log('Error: ',JSON.stringify(error));
                Auth.unSetCredentials();
                this.setState({error: true, loading: false});
            });
    }
    render() {
        return (
            <div>
                { this.state.loading ? <p>LOADING..</p> : <MealDayList data = {this.state.groupedInfo} /> }
            </div>
        );
    }
}

export default MealPage;
