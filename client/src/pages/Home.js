import React, { Component } from 'react';
import LoginForm from '../components/LoginForm';

class Home extends Component {
    constructor(props) {
        super(props);

        this.state = {
        };
    }
    componentDidMount() {
    }
    render() {
        return (
            <div>
                <p>Home</p>
                <LoginForm />
            </div>
        );
    }
}

export default Home;
