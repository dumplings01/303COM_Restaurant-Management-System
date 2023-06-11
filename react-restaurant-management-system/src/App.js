import './App.css';
import { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';


import NavBar from './components/NavBar';
import CustomerLogin from './components/customer/CustomerLogin';
import CustomerRegister from './components/customer/CustomerRegister';
import Reservation from './components/customer/CustomerReservation';
import Dashboard from './components/admin/AdminDashboard';
import Logout from './components/Logout';
import CustomerReservation from './components/customer/CustomerReservation';
import AdminReservation from './components/admin/AdminReservation';
import AdminReservationCard from './components/admin/AdminReservationCard';

function App() {
	const [userType, setUserType] = useState("");
	const [navTo, setNavTo] = useState();

	useEffect(() => {
		const currentUserType = sessionStorage.getItem("currentUserType");

		if(currentUserType === null){
			setUserType(currentUserType);
		}
    }, []);

	useEffect(() => {
		if (userType === "customer"){
			setNavTo(<Reservation />);
		} else if (userType === "admin") {
			setNavTo(<Dashboard />);
		} else {
			setNavTo(<CustomerLogin />);
		}
	}, [userType]);

	return (
		<>
		<Router>
			<NavBar />
			<Routes>
				<Route path='*' element={navTo} />
				
				<Route exact path='/customerRegister' element={<CustomerRegister />} />
				<Route exact path='/customerReservation' element={<CustomerReservation />} />
				<Route exact path='/adminReservation' element={<AdminReservation />} />
				<Route exact path='/logout' element={<Logout />} />
				
			</Routes>
		</Router>
		
		</>
	);
}

export default App;
