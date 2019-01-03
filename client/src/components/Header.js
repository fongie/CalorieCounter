import React from 'react';
import { Navbar, Nav, NavItem, PageHeader } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const Header = () => {
    return (
        <PageHeader>
            <Navbar>
                <Navbar.Header>
                    <Navbar.Brand>
                        <Link to="/">Calorie Counter</Link>
                    </Navbar.Brand>
                </Navbar.Header>
                <Nav>
                    <NavItem href="/foods">
                        Foods
                    </NavItem>
                    <NavItem href="/meals">
                        Meals
                    </NavItem>
                    <NavItem href="/weight">
                        Weight
                    </NavItem>
                </Nav>
            </Navbar>
        </PageHeader>
    );
}

export default Header;
