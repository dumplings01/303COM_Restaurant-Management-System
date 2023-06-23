import React, { useState, useEffect } from 'react';
import axios from 'axios';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

import NavBar from '../NavBar';

function AdminCustomerLoyalty() {

    const [customerId, setCustomerId] = useState("");
    const [customerName, setCustomerName] = useState("");
    const [email, setEmail] = useState("");
    const [contactNumber, setContactNumber] = useState("");
    const [loyaltyPoints, setLoyaltyPoints] = useState("");

    const [paid, setPaid] = useState("");
    
    const [load, setLoad] = useState(true);

    useEffect(() => {
        var currentUserType = sessionStorage.getItem("currentUserType");
		var adminRoles = sessionStorage.getItem("adminRoles");
		const ownerAccess = adminRoles.includes('owner');
        const cashierAccess = adminRoles.includes('cashier');
        
        if(currentUserType == null || currentUserType === "customer"){
            window.location.assign("/");
            alert("UNAUTHORIZED!");
        } else if (!ownerAccess && !cashierAccess) {
            window.location.assign("/dashboard");
			alert("No access to manage loyalty program!");
		}
    }, []);

	function handleSubmit(e) {

        e.preventDefault();
        const getProfile = async () => {

            try {
                const response = await axios.get(`http://127.0.0.1:8080/customer/getCustomerProfileByContactNumber?
												contactNumber=${contactNumber}`);
                
                setCustomerId(response.data.customerId);
                setCustomerName(response.data.name);
                setEmail(response.data.email);
                setLoyaltyPoints(response.data.loyaltyPoints);

                setLoad(false);
                
            } catch (e) {
                console.log(e);
            }
        };
        getProfile();
    }

    function handleAddPoints(e){
        e.preventDefault();
        axios.put(`http://127.0.0.1:8080/customer/updateLoyaltyPoints?contactNumber=${contactNumber}&addedPoints=${paid}`,
                    {
                        loyaltyPoints
                    })
                    .then((res) => {
                        console.log(res);
                        if (res.status === 200) {
                            alert("Points updated successfully!");
                            window.location.assign("/customerLoyalty");
                            return res;
                        }
                    }).catch((error) => {
                        console.log(error);
                        alert("Failed to update points!");
                    })
    }

    function handleDeductPoints5(e){
        e.preventDefault();
        axios.put(`http://127.0.0.1:8080/customer/redeemLoyaltyPoints?contactNumber=${contactNumber}&redeemPoints=200`,
                    {
                        loyaltyPoints
                    })
                    .then((res) => {
                        console.log(res);
                        if (res.status === 200) {
                            alert("Points redeemed successfully!");
                            window.location.assign("/customerLoyalty");
                            return res;
                        }
                    }).catch((error) => {
                        console.log(error);
                        alert("Failed to redeem points!");
                    })
    }

    function handleDeductPoints10(e){
        e.preventDefault();
        axios.put(`http://127.0.0.1:8080/customer/redeemLoyaltyPoints?contactNumber=${contactNumber}&redeemPoints=350`,
                    {
                        loyaltyPoints
                    })
                    .then((res) => {
                        console.log(res);
                        if (res.status === 200) {
                            alert("Points redeemed successfully!");
                            window.location.assign("/customerLoyalty");
                            return res;
                        }
                    }).catch((error) => {
                        console.log(error);
                        alert("Failed to redeem points!");
                    })
    }

    return (
		<>
			<NavBar />
            <div className="w-75 h-75 mx-auto" style={{maxWidth: "900px"}}>
                <div className="d-flex justify-content-between p-4 px-2">
                    <a className="me-1 btn btn-secondary align-self-center" href="/dashboard" role="button">Back</a>
                    <Form onSubmit={handleSubmit} style={{
                                width: "60%",
                                display: "flex"
                              }}>
                        <Form.Group className="w-100">
                            <Form.Label>Contact number: </Form.Label>
                            <Form.Control type="text" name="contactNumber" value={contactNumber}  placeholder="Enter contact number"
                            onChange={(event) => setContactNumber(event.target.value)} pattern="(01\d-\d{7}$)" maxLength="11" 
                            required />
                        </Form.Group>
                            <Button variant="primary" type="submit" className="align-self-end d-flex justify-content-center"
                            style={{width: "5rem"}}>Search</Button>
                    </Form>
                </div>
                

            
                { load ? (
                        <h1 className="text-center pt-5">Search customer using contact number...</h1>
                ) : (
                    <>
                        <div>
                        <Form onSubmit={handleAddPoints}>
                            <Form.Group className="m-5 mt-4 mb-2">
                                <Form.Label>Paid (RM): </Form.Label>
                                <Form.Control type="number" name="paid" placeholder="Enter amount paid"
                                onChange={(event) => setPaid(event.target.value)} required />
                            </Form.Group>
                            <div className="text-center p-4">
                                <Button variant="primary" type="submit" className="me-1">Add to Points</Button>
                            </div>
                        </Form>
                        </div>
                        <div className="card m-5">
                            <div className="card-body">
                                <h5 className="card-title h3 text-center pb-4">{customerName}</h5>
                                <p className="card-text pb-3 d-flex flex-column">
                                    <span className="p-2"><b>CustomerId: </b>{customerId}</span>
                                    <span className="p-2"><b>Email: </b>{email}</span>
                                    <span className="p-2"><b>Contact number: </b>{contactNumber}</span>
                                    <span className="p-2"><b>Loyalty Points: </b>{loyaltyPoints}</span>
                                </p>
                                <div className="d-flex justify-content-evenly">
                                    {
                                        parseInt(loyaltyPoints) >= 200 ? (
                                            <Button variant="primary" type="button" className="me-1" onClick={handleDeductPoints5}>Redeem RM 5 Discount With 200 Points</Button>
                                        ) : (
                                            <Button variant="primary" type="button" className="me-1" disabled>Redeem RM 5 Discount With 200 Points</Button>
                                        )
                                    }
                                    {
                                        parseInt(loyaltyPoints) >= 350 ? (
                                            <Button variant="primary" type="button" className="ms-1" onClick={handleDeductPoints10}>Redeem RM 10 Discount With 350 Points</Button>
                                        ) : (
                                            <Button variant="primary" type="button" className="ms-1" disabled>Redeem RM 10 Discount With 350 Points</Button>
                                        )
                                    }
                                </div>
                            </div>
                        </div>
                    </>
                )}
            </div>
        </>
	)
}

export default AdminCustomerLoyalty;