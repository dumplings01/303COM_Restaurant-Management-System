import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import NavBarLogin from './NavBarLogin';

function Login() {
    
    const [email, setEmail ] = useState("");
    const [password, setPassword] = useState("");

    let navigate = useNavigate();

    function handleSubmit(e) {
        //console.log(email + " " + password)
        e.preventDefault();

        const getLogin = async () => {

            try {
                const result = await axios.post("http://127.0.0.1:8080/user/login",
                                                { 
                                                    email,
                                                    password 
                                                })
                                                .then((res) => {
                                                    // then shows result from axios call when data is obtained succesfully
                                                    console.log(res);
                                                    if (res.status === 200){
                                                        alert("Login successful!");
                                                        return res;
                                                        // then store result (email) to session storage
                                                    }
                                                    
                                                }).catch((error) => {
                                                    // catch shows the error from axios call when data is failed to obtain
                                                    alert("Email or password is incorrect!");
                                                });

                // check if user is logged in by storing logged in user data                                
                if (result!=null){

                    let currentUserType = sessionStorage.getItem("currentUserType");
                    if (currentUserType == null){
                        currentUserType = result.data.split(",").shift();
                    }
                    sessionStorage.setItem("currentUser", email);
                    sessionStorage.setItem("currentUserType", currentUserType);

                    //console.log(currentUserType)
                    
                    if (currentUserType==="customer"){
                        navigate("/customerProfile");
                    } else if (currentUserType==="admin") {
                        navigate("/dashboard");
                    }

                }

            } catch (e) {
                console.log(e);
            }
        };
        getLogin();
    }

    return (
        <>
        <NavBarLogin />
        <h2 className="text-center mt-4 mb-4">Login</h2>
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
                    <Form.Control type="password" name="password" minLength="8" placeholder="Enter password" required onChange={
                        (event) => {setPassword(event.target.value)}
                    } />
                </Form.Group>

                <div className="text-center p-4">
                    <Button variant="primary" type="submit" className="me-1">Login</Button>
                </div>

                <div className="d-flex flex-column text-center pb-4">
                    <a href="register" className="hover-text">Register New User?</a>
                </div>

            </Form>

        </div>
        </>
    )
}

export default Login;