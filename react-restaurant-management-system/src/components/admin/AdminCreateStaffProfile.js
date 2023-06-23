import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

import NavBar from '../NavBar';

function AdminCreateStaffProfile() {

	const [name, setName] = useState("");
	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");
	const [roles, setRoles] = useState([]);

	const roleList = ["owner", "manage inventory", "manage supplier purchases", "manage reservations", "cashier"];

	let navigate = useNavigate();

	useEffect(() => {
        var currentUserType = sessionStorage.getItem("currentUserType");
		var adminRoles = sessionStorage.getItem("adminRoles");
		const access = adminRoles.includes('owner');
        
        if(currentUserType == null || currentUserType === "customer"){
            window.location.assign("/");
            alert("UNAUTHORIZED!");
        } else if (!access) {
            window.location.assign("/dashboard");
			alert("No access to manage staff list!");
		}
    }, []);

	function handleCheckboxChange(e) {
        const { value, checked } = e.target;
    
		if (checked) {
			setRoles((prevRoles) => [...prevRoles, value]);
		} else {
			setRoles((prevRoles) => prevRoles.filter((role) => role !== value));
		}
	};

	function handleSubmit(e) {
        
        e.preventDefault();

		if (roles.length === 0){
            alert("Roles must not be empty!")
		} else {
			axios.post("http://127.0.0.1:8080/admin/createNewAdmin",
					{ 
						name,
						email,
						password,
						roles
					})
					.then((res) => {
						// then shows result from axios call when data is obtained succesfully
						console.log(res);
						if (res.status === 200){
							alert(name+"'s staff account created successfully!");
							navigate("/dashboard");
							return res;
						}

					}).catch((error) => {
						// catch shows the error from axios call when data is failed to obtain
						console.log(error);
						alert("Email or password is taken!");
					});
		}
        
    }

    return (
		<>
			<NavBar />
			<h2 className="text-center mt-4 mb-4">Create Staff Account</h2>
			<div className="m-auto mt-3 mb-3 col-6 border rounded border-dark">
				<Form onSubmit={handleSubmit}>
					<Form.Group className="m-5 mt-4 mb-2">
						<Form.Label>Name: </Form.Label>
						<Form.Control type="text" name="name" placeholder="Enter name" required onChange={
							(event) => {setName(event.target.value)}
						} />
					</Form.Group>

					<Form.Group className="m-5 mt-4 mb-2">
						<Form.Label>Email: </Form.Label>
						<Form.Control type="email" name="email" placeholder="Enter email" required onChange={
							(event) => {setEmail(event.target.value)}
						} />
					</Form.Group>

					<Form.Group className="m-5 mt-4 mb-2 d-flex flex-column">
						<Form.Label>Roles: </Form.Label>
							{roleList.map((role, index) => (
								<label key={index}>
									<Form.Check
										inline
										type="checkbox"
										value={role}
										checked={roles.includes(role)}
										onChange={handleCheckboxChange}
									/>
									{role}
								</label>
							))}
					</Form.Group>

					<Form.Group className="m-5 mt-4 mb-2">
						<Form.Label>Password: </Form.Label>
						<Form.Control type="password" name="password" placeholder="Enter password" minLength="8" required onChange={
							(event) => {setPassword(event.target.value)}
						} />
					</Form.Group>

					<div className="text-center p-4">
						<a className="me-1 btn btn-secondary" href="/staffList" role="button">Cancel</a>
						<Button variant="primary" type="submit" className="me-1">Create Staff Profile</Button>
					</div>
				</Form>
        	</div>
		</>
		)
}

export default AdminCreateStaffProfile;