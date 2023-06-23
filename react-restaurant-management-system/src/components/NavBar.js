import React from 'react';

import Container from 'react-bootstrap/Container';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import "bootstrap/dist/css/bootstrap.min.css";

function NavBar () {

	return (
		<>
        <Navbar bg="dark" variant="dark">
			<Container>
			<Navbar.Brand href="/dashboard" >Restaurant Management System</Navbar.Brand>
			<Nav className="me-auto">
				<Nav.Link href="/dashboard">Dashboard</Nav.Link>
			</Nav>
			<Nav>
				<Nav.Link href="/logout">Logout</Nav.Link>
			</Nav>
			</Container>
		</Navbar>
		
		</>
	);
}

export default NavBar;