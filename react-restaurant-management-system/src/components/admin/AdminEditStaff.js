import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

import NavBar from '../NavBar';

function AdminEditStaff(){

    let {adminId} = useParams();
    let navigate = useNavigate();

    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [roles, setRoles] = useState([]);

    const roleList = ["owner", "manage inventory", "manage supplier purchases", "manage reservations", "cashier"];

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

    useEffect(() => {

        const getProfile = async () => {

            try {
                const response = await axios.get(`http://127.0.0.1:8080/admin/getAdminProfile?adminId=${adminId}`);
                console.log(response);
                setName(response.data.name);
                setEmail(response.data.email);
                setRoles(response.data.roles);
                
            } catch (e) {
                console.log(e);
            }
        };
        getProfile();
    },[adminId]);

    function handleCheckboxChange(e) {
        const { value, checked } = e.target;
    
		if (checked) {
			setRoles((prevRoles) => [...prevRoles, value]);
		} else {
			setRoles((prevRoles) => prevRoles.filter((role) => role !== value));
		}
	};
    console.log(roles)

    function handleSubmit(e, value){
        e.preventDefault();

        if (roles.length === 0){
            alert("Roles must not be empty!")
        } else {
            axios.put(`http://127.0.0.1:8080/admin/updateAdminProfile?adminId=${adminId}`,
            {
                name,
                roles
            })
            .then((res) => {
                console.log(res);
                if (res.status === 200) {
                    alert("Staff details updated successfully!");
                    navigate("/staffList");
                    return res;
                }
            }).catch((error) => {
                console.log(error);
                alert("Failed to update staff details!");
                window.location.reload(false);
            })
        }
    }

    return (
        <>
            <NavBar />
            <h2 className="text-center mt-4 mb-4">Edit Staff Account</h2>
			<div className="m-auto mt-3 mb-3 col-6 border rounded border-dark">
				<Form onSubmit={handleSubmit}>
					<Form.Group className="m-5 mt-4 mb-2">
						<Form.Label>Name: </Form.Label>
						<Form.Control type="text" name="name" placeholder="Enter name" value={name} required onChange={
							(event) => {setName(event.target.value)}
						} />
					</Form.Group>

					<Form.Group className="m-5 mt-4 mb-2">
						<Form.Label>Email: </Form.Label>
						<Form.Control type="email" name="email" value={email} disabled />
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
                    
					<div className="text-center p-4">
						<a className="me-1 btn btn-secondary" href="/staffList" role="button">Cancel</a>
                        <Button variant="primary" type="submit" className="me-1">Update</Button>
					</div>
				</Form>
        	</div>
        </>
    )
}

export default AdminEditStaff;