import './App.css';
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import Login from './components/Login';
import Logout from './components/Logout';
import Register from './components/Register';

import CustomerProfile from './components/customer/CustomerProfile';
import CustomerEditProfile from './components/customer/CustomerEditProfile';
import CustomerCreateReservation from './components/customer/CustomerCreateReservation';
import CustomerViewReservation from './components/customer/CustomerViewReservation';
import CustomerEditReservation from './components/customer/CustomerEditReservation';

import AdminDashboard from './components/admin/AdminDashboard';
import AdminCreateStock from './components/admin/AdminCreateStock';
import AdminStockList from './components/admin/AdminStockList';
import AdminEditStock from './components/admin/AdminEditStock';
import AdminCreatePurchaseHistory from './components/admin/AdminCreatePurchaseHistory';
import AdminPurchaseHistoryList from './components/admin/AdminPurchaseHistoryList';
import AdminEditPurchaseHistory from './components/admin/AdminEditPurchaseHistory';
import AdminReservationList from './components/admin/AdminReservationList';
import AdminUpdateReservation from './components/admin/AdminUpdateReservation';
import AdminCustomerLoyalty from './components/admin/AdminCustomerLoyalty';
import AdminCreateStaffProfile from './components/admin/AdminCreateStaffProfile';
import AdminStaffList from './components/admin/AdminStaffList';
import AdminEditStaff from './components/admin/AdminEditStaff';
import AdminCreateReservationSlots from './components/admin/AdminCreateReservationSlots';

import NotFound from './components/NotFound';

function App() {

	return (
		<>
		<Router>
			<Routes>
				<Route path='/' element={<Login />} />
				<Route path='*' element={<NotFound />} />
				<Route exact path='/login' element={<Login />} />
				<Route exact path='/logout' element={<Logout />} />
				<Route exact path='/register' element={<Register />} />

				<Route exact path='/customerProfile' element={<CustomerProfile />} />
				<Route exact path='/customerEditProfile' element={<CustomerEditProfile />} />
				<Route exact path='/customerCreateReservation' element={<CustomerCreateReservation />} />
				<Route exact path='/customerViewReservation' element={<CustomerViewReservation />} />
				<Route exact path='/customerEditReservation/:reservationId' element={<CustomerEditReservation />} />

				<Route exact path='/dashboard' element={<AdminDashboard />} />
				<Route exact path='/createNewStock' element={<AdminCreateStock />} />
				<Route exact path='/readStockList' element={<AdminStockList />} />
				<Route exact path='/editStock/:stockId' element={<AdminEditStock />} />
				<Route exact path='/createPurchaseHistory' element={<AdminCreatePurchaseHistory />} />
				<Route exact path='/readPurchaseHistory' element={<AdminPurchaseHistoryList />} />
				<Route exact path='/editPurchaseHistory' element={<AdminEditPurchaseHistory />} />
				<Route exact path='/adminReservationList' element={<AdminReservationList />} />
				<Route exact path='/updateReservation/:reservationId' element={<AdminUpdateReservation />} />
				<Route exact path='/customerLoyalty' element={<AdminCustomerLoyalty />} />
				<Route exact path='/createStaffProfile' element={<AdminCreateStaffProfile />} />
				<Route exact path='/staffList' element={<AdminStaffList />} />
				<Route exact path='/editStaff/:adminId' element={<AdminEditStaff />} />
				<Route exact path='/createReservationSlots' element={<AdminCreateReservationSlots />} />
				

			</Routes>
		</Router>
		
		</>
	);
}

export default App;
