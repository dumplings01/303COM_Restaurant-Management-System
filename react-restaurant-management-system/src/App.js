import './App.css';
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import Login from './components/Login';
import Logout from './components/Logout';
import Register from './components/Register';

import CustomerCreateReservation from './components/customer/CustomerCreateReservation';
import CustomerEditProfile from './components/customer/CustomerEditProfile';
import CustomerProfile from './components/customer/CustomerProfile';
import CustomerViewReservation from './components/customer/CustomerViewReservation';
import CustomerEditReservation from './components/customer/CustomerEditReservation';
import AdminDashboard from './components/admin/AdminDashboard';
import AdminCreateStock from './components/admin/AdminCreateStock';
import AdminStockList from './components/admin/AdminStockList';
import AdminCreatePurchaseHistory from './components/admin/AdminCreatePurchaseHistory';
import AdminPurchaseHistoryList from './components/admin/AdminPurchaseHistoryList';
import AdminCreateNewReservation from './components/admin/AdminCreateNewReservation';
import AdminReservation from './components/admin/AdminReservation';
import AdminCustomerProfileList from './components/admin/AdminCustomerProfileList';
import AdminCreateStaffProfile from './components/admin/AdminCreateStaffProfile';
import AdminStaffList from './components/admin/AdminStaffList';

import ReservationForm from './components/customer/example.js';

function App() {

	return (
		<>
		<Router>
			<Routes>
				<Route path='/' element={<Login />} />
				<Route path='*' element={<Login />} />
				<Route exact path='/login' element={<Login />} />
				<Route exact path='/logout' element={<Logout />} />
				<Route exact path='/register' element={<Register />} />
				<Route exact path='/customerCreateReservation' element={<CustomerCreateReservation />} />
				<Route exact path='/customerEditProfile/:email' element={<CustomerEditProfile />} />
				<Route exact path='/customerProfile' element={<CustomerProfile />} />
				<Route exact path='/customerViewReservation' element={<CustomerViewReservation />} />
				<Route exact path='/customerEditReservation' element={<CustomerEditReservation />} />
				<Route exact path='/adminDashboard' element={<AdminDashboard />} />
				<Route exact path='/createNewStock' element={<AdminCreateStock />} />
				<Route exact path='/readStockList' element={<AdminStockList />} />
				<Route exact path='/createPurchaseHistory' element={<AdminCreatePurchaseHistory />} />
				<Route exact path='/readPurchaseHistory' element={<AdminPurchaseHistoryList />} />
				<Route exact path='/adminCreateNewReservation' element={<AdminCreateNewReservation />} />
				<Route exact path='/adminReservationList' element={<AdminReservation />} />
				<Route exact path='/findCustomerProfile' element={<AdminCustomerProfileList />} />
				<Route exact path='/createStaffProfile' element={<AdminCreateStaffProfile />} />
				<Route exact path='/readStaffList' element={<AdminStaffList />} />

				<Route exact path='/example' element={<ReservationForm />} />
			</Routes>
		</Router>
		
		</>
	);
}

export default App;
