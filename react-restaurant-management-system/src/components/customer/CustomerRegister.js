import React, { useState } from 'react';
import { Routes, Route, useNavigate } from 'react-router-dom';
import axios from 'axios';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';


import Login from '../admin/AdminLogin';

function CustomerRegister() {
    
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    let navigate = useNavigate();

    const navigateLogin = () => {
        navigate('/login');
    }

    function handleSubmit(e) {
        console.log(email + " " + password)
        e.preventDefault();

        const getRegister = async () => {

            try {
                const result = await axios.post("http://127.0.0.1:8080/admin/createNewAdmin",
                                                { 
                                                    userEmail: email,
                                                    password
                                                })
                                                .then((res) => {
                                                    // then shows result from axios call when data is obtained succesfully
                                                    console.log(res);
                                                    if (res.status === 200){
                                                        alert("Register successful!");
                                                        //navigate("/login");
                                                        return res;
                                                    }

                                                }).catch((error) => {
                                                    // catch shows the error from axios call when data is failed to obtain
                                                    console.log(error);
                                                    alert("Email or password is taken!");
                                                });

                if (result!=null){
                    navigate("/customerLogin");
                }
                
            } catch (e) {
                console.log(e);
            }
        };
        getRegister();

    }
    
    return (
        <>
        <h2 className="text-center mt-4 mb-4">Register</h2>
        <div className="m-auto mt-3 mb-3 col-6 border rounded border-dark">
            <Form onSubmit={handleSubmit}>
                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Email: </Form.Label>
                    <Form.Control type="email" name="email" placeholder="Enter email" required onChange={
                        (event) => {setEmail(event.target.value)}
                    } />
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Password: </Form.Label>
                    <Form.Control type="password" name="password" placeholder="Enter password" required onChange={
                        (event) => {setPassword(event.target.value)}
                    } />
                </Form.Group>

                <div className="text-center p-4">
                    <Button variant="primary" type="submit" className="me-1">Register</Button>
                </div>

                <div className="text-center pb-4">
                    <a href onClick={navigateLogin} className="hover-text">Existing User?</a>
                </div>

            </Form>

            <Routes>
                <Route path="/login" element={<Login />} />
            </Routes>
        </div>
        </>
    )
}

export default CustomerRegister;