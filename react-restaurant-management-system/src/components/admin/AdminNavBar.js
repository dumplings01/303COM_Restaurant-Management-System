import React, { useState, useEffect } from 'react';
import { Routes, Route } from 'react-router-dom';

import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';

import "bootstrap/dist/css/bootstrap.min.css";

import Login from './AdminLogin';
import AdminStocks from './AdminStocks';
import AdminSuppliers from './AdminSuppliers';
import AdminReservation from './AdminReservation';
import AdminCustomerList from './AdminCustomerList';
import Logout from '../Logout';

function AdminNavBar () {

	const [user, setUser] = useState("");

	useEffect(() => {
        var currentUser = sessionStorage.getItem("currentUser");
        if(currentUser == null){
            setUser(currentUser);
		}
    }, []);

	return (
		<>
        <Navbar bg="dark" variant="dark">
			<Container>
			<Navbar.Brand href="/">Restaurant Management System</Navbar.Brand>
			<Nav className="me-auto">
				<Nav.Link href="/stocks">Stocks</Nav.Link>
				<Nav.Link href="/suppliers">Suppliers</Nav.Link>
				<Nav.Link href="/reservation">Reservation</Nav.Link>
				<Nav.Link href="/customers">Customers</Nav.Link>
			</Nav>
			<Nav>
				<Nav.Link href="/logout">Logout</Nav.Link>
			</Nav>
			</Container>
		</Navbar>
		<Routes>
			<Route path='/' element={<Login />} />
			<Route path='/stocks' element={<AdminStocks />} />
			<Route path='/suppliers' element={<AdminSuppliers />} />
			<Route path='/reservation' element={<AdminReservation />} />
			<Route path='/customers' element={<AdminCustomerList />} />
			<Route path='/logout' element={<Logout />} />
		</Routes>
		</>
	);
}

export default AdminNavBar;