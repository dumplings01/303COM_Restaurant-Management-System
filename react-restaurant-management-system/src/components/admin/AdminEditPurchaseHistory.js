import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';

import NavBar from '../NavBar';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import DateTimePicker from 'react-datetime-picker/';
import BootstrapSwitchButton from 'bootstrap-switch-button-react'

function AdminEditPurchaseHistory() {

    let {supplierId} = useParams();
    let navigate = useNavigate();

    const [supplierName, setSupplierName] = useState("");
    const [stockName, setStockName] = useState("");
    const [stockType, setStockType] = useState("");
    const [stockQuantity, setStockQuantity] = useState("");
    const [stockWeight, setStockWeight] = useState("");
    const [unitOfMeasurement, setUnitOfMeasurement] = useState("");
    const [estimatedDeliveryDate, setEstimatedDeliveryDate] = useState("");
    const [dateDelivered, setDateDelivered] = useState("");
    const [stockCondition, setStockCondition] = useState("Poor");
    const [deliveryFeedback, setDeliveryFeedback] = useState("");
	const [costOfStock, setCostOfStock] = useState("");
	const [status, setStatus] = useState("");

	const [isChecked, setIsChecked] = useState(true);
    // const [defaultType, setDefaultType] = useState(false);

    // function selectDefault() {

    // }

    useEffect(() => {
        var currentUserType = sessionStorage.getItem("currentUserType");
		var adminRoles = sessionStorage.getItem("adminRoles");
		const adminAccess = adminRoles.includes('owner');
		const supplierAccess = adminRoles.includes('manage supplier purchases');
        
        if(currentUserType == null || currentUserType === "customer"){
            window.location.assign("/");
            alert("UNAUTHORIZED!");
            
        } else if (!adminAccess && !supplierAccess) {
            window.location.assign("/dashboard");
			alert("No access to manage supplier purchases!");
		}
    }, []);

	function handleToggle() {
		setStockQuantity("");
		setStockWeight("");
		setUnitOfMeasurement("");
		setIsChecked(!isChecked);
	}

    useEffect(() => {
        const getSupplierHistory = async () => {
            try {
                const response = await axios.get(`http://127.0.0.1:8080/supplier/getSingleSupplierHistory?supplierId=${supplierId}`);
                //console.log(response);
                setSupplierName(response.data.supplierName);
                setStockName(response.data.stockName);
                setStockType(response.data.stockType);
                setStockQuantity(response.data.stockQuantity);
                setStockWeight(response.data.stockWeight);
                setUnitOfMeasurement(response.data.unitOfMeasurement);
                setEstimatedDeliveryDate(response.data.estimatedDeliveryDate);
                setCostOfStock(response.data.costOfStock);
                setStatus(response.data.status);
                if (response.data.stockQuantity===null || response.data.stockQuantity==="") {
                    setIsChecked(false);
                }
                
            } catch (e) {
                console.log(e);
            }
        };
        getSupplierHistory();
    },[supplierId]);

    function handleSubmit(e, value){
        e.preventDefault();

        axios.put(`http://127.0.0.1:8080/supplier/updateSupplierHistory?supplierId=${supplierId}`,
        {
            supplierName,
            stockName,
            stockType,
            stockQuantity,
            stockWeight,
            unitOfMeasurement,
            estimatedDeliveryDate,
            dateDelivered,
            stockCondition,
            deliveryFeedback,
            costOfStock,
            status
        })
        .then((res) => {
            console.log(res);
            if (res.status === 200) {
                alert("Supplier purchase history updated successfully!");
                navigate("/readPurchaseHistory");
                return res;
            }
        }).catch((error) => {
            console.log(error);
            alert("Failed to update purchase history!\n"+error.response.data);
        })
    }

	return (
		<>
			<NavBar />
            <h2 className="text-center mt-4 mb-4">Edit Purchase History</h2>
            <div className="m-auto mt-3 mb-3 col-6 border rounded border-dark">
                <Form onSubmit={handleSubmit}>
                    <Form.Group className="m-5 mt-4 mb-2">
                        <Form.Label>Supplier Name: </Form.Label>
                        <Form.Control type="text" value={supplierName} disabled required />
                    </Form.Group>

                    <Form.Group className="m-5 mt-4 mb-2">
                        <Form.Label>Stock Name: </Form.Label>
                        <Form.Control type="text" value={stockName} disabled required />
                    </Form.Group>

                    <Form.Group className="m-5 mt-4 mb-2">
                        <Form.Label>Stock Type: </Form.Label>
                        <Form.Select defaultValue={stockType} onChange={(e) => {setStockType(e.target.value)}} required>
                            <option value="Condiments">Condiments</option>
                            <option value="Eggs, Milk and Milk Products">Eggs, Milk and Milk Products</option>
                            <option value="Fats and Oils">Fats and Oils</option>
                            <option value="Fruits">Fruits</option>
                            <option value="Grain, Nuts and Baking products">Grain, Nuts and Baking products</option>
                            <option value="Herbs and Spices">Herbs and Spices</option>
                            <option value="Meat, Sausages and Fish">Meat, Sausages and Fish</option>
                            <option value="Pasta, Rice and Pulses">Pasta, Rice and Pulses</option>
                            <option value="Vegetables">Vegetables</option>
                            <option value="Others">Others</option>
                        </Form.Select>
                    </Form.Group>

                    <Form.Group className="d-flex row m-5 mt-4 mb-2">
                        <Form.Label>Method of Measurement: </Form.Label>
                        <BootstrapSwitchButton checked={isChecked} onChange={handleToggle} onlabel='Quantity'
                        offlabel='Weight' disabled />
                    </Form.Group>
                    
                    {
                        isChecked ? (
                            <Form.Group className="m-5 mt-4 mb-2">
                                <Form.Label>Stock Quantity: </Form.Label>
                                <Form.Control type="number" min="1" defaultname="stockQuantity" value={stockQuantity} required
                                 disabled />
                            </Form.Group>
                        ) : (
                            <>
                            <Form.Group className="m-5 mt-4 mb-2">
                                <Form.Label>Stock Weight: </Form.Label>
                                <Form.Control type="number" min="0.01" name="stockWeight" step="0.01" value={stockWeight} required
                                 disabled />
                            </Form.Group>

                            <Form.Group className="m-5 mt-4 mb-2">
                                <Form.Label>Unit of Measurement: </Form.Label>
                                <Form.Control type="text" name="unitOfMeasurement" value={unitOfMeasurement} required disabled />
                            </Form.Group>
                            </>
                        )
                    }

                    <Form.Group className="m-5 mt-4 mb-2">
                        <Form.Label>Estimated Delivery Date: </Form.Label>
                        <DateTimePicker className="border p-3 w-100 rounded-2" disabled name="estimatedDeliveryDate" format="dd-MM-yyyy"
                         value={estimatedDeliveryDate} />
                    </Form.Group>
                
                    <Form.Group className="m-5 mt-4 mb-2">
                        <Form.Label>Date Delivered: </Form.Label>
                        <DateTimePicker className="border p-3 w-100 rounded-2" required name="dateDelivered" format="dd-MM-yyyy"
                         onChange={setDateDelivered} value={dateDelivered} />
                    </Form.Group>

                    <Form.Group className="m-5 mt-4 mb-2">
                        <Form.Label>Stock Condition: </Form.Label>
                        <Form.Select defaultValue="Poor" onChange={(e) => {setStockCondition(e.target.value)}} required>
                            <option value="Poor">Poor</option>
                            <option value="Average">Average</option>
                            <option value="Excellent">Excellent</option>
                        </Form.Select>
                    </Form.Group>

                    <Form.Group className="m-5 mt-4 mb-2">
                        <Form.Label>Delivery Feedback: </Form.Label>
                        <Form.Control type="text" name="deliveryFeedback" value={deliveryFeedback} 
                        onChange={(e) => {setDeliveryFeedback(e.target.value)}} placeholder="Enter delivery feedback" />
                    </Form.Group>

                    <Form.Group className="m-5 mt-4 mb-2">
                        <Form.Label>Cost of Stock (Total) (RM): </Form.Label>
                        <Form.Control type="number" min="0.01" required name="costOfStock" step="0.01" value={costOfStock}
                        onChange={(e) => {setCostOfStock(e.target.value)}} placeholder="Enter cost of stock" />
                    </Form.Group>

                    <Form.Group className="m-5 mt-4 mb-2">
                        <Form.Label>Status: </Form.Label>
                        <Form.Select defaultValue={status} onChange={(e) => {setStatus(e.target.value)}} required>
                            <option value="Order Placed">Order Placed</option>
                            <option value="Order Received">Order Received</option>
                        </Form.Select>
                    </Form.Group>

                    <div className="text-center p-4">
						<a className="me-1 btn btn-secondary" href="/readPurchaseHistory" role="button">Cancel</a>
                        <Button variant="primary" type="submit" className="me-1">Update</Button>
                    </div>
                </Form>
            </div>
		</>
		)
}

export default AdminEditPurchaseHistory;