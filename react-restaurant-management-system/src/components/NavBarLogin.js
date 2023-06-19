import React from 'react';

import Container from 'react-bootstrap/Container';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import "bootstrap/dist/css/bootstrap.min.css";

function NavBarLogin () {

	return (
		<>
        <Navbar bg="dark" variant="dark">
			<Container>
			<Navbar.Brand href="/login" >Restaurant Management System</Navbar.Brand>
			<Nav>
				<Nav.Link href="#">Welcome!</Nav.Link>
			</Nav>
			</Container>
		</Navbar>
		
		</>
	);
}

export default NavBarLogin;