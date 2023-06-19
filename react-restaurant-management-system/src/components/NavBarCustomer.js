import React from 'react';

import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';

import "bootstrap/dist/css/bootstrap.min.css";

function NavBarCustomer () {

	return (
		<>
        <Navbar bg="dark" variant="dark">
			<Container>
			<Navbar.Brand href="/customerProfile">Restaurant Management System</Navbar.Brand>
			<Nav className="me-auto">
				<Nav.Link href="/customerProfile">My Profile</Nav.Link>
				<Nav.Link href="/customerCreateReservation">Create Reservation</Nav.Link>
				<Nav.Link href="/customerViewReservation">View Reservation</Nav.Link>
			</Nav>
			<Nav>
				<Nav.Link href="/logout">Logout</Nav.Link>
			</Nav>
			</Container>
		</Navbar>
		</>
	);
}

export default NavBarCustomer;