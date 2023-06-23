import React, { useState, useEffect } from 'react';
import axios from 'axios';

import NavBar from '../NavBar';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import DateTimePicker from 'react-datetime-picker/';
import BootstrapSwitchButton from 'bootstrap-switch-button-react'

function AdminCreatePurchaseHistory() {

	const [supplierName, setSupplierName] = useState("");
    const [stockName, setStockName] = useState("");
    const [stockType, setStockType] = useState("Condiments");
    const [stockQuantity, setStockQuantity] = useState("");
    const [stockWeight, setStockWeight] = useState("");
    const [unitOfMeasurement, setUnitOfMeasurement] = useState("");
    const [estimatedDeliveryTime, setEstimatedDeliveryTime] = useState("");
	const [costOfStock, setCostOfStock] = useState("");

	const [isChecked, setIsChecked] = useState(true);

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

    function handleSubmit(e) {
        e.preventDefault();

		axios.post(`http://127.0.0.1:8080/supplier/createSupplierHistory`,
			{
				supplierName,
				stockName,
				stockType,
				stockQuantity,
				stockWeight,
				unitOfMeasurement,
				estimatedDeliveryTime,
				costOfStock
			},
			{ headers: { "x-key": '123', } })
			.then((res) => {
				console.log(res);
				if (res.status === 200) {
					alert("Stock recorded successfully!");
					window.location.assign("/readPurchaseHistory")
					return res;
				}
			}).catch((error) => {
				console.log(error);
				alert("Failed to create stock record!\n"+error.response.data);
			})
    }

    return (
		<>
			<NavBar />
			<h2 className="text-center mt-4 mb-4">Create Purchase History</h2>
			<div className="m-auto mt-3 mb-3 col-6 border rounded border-dark">
				<Form onSubmit={handleSubmit}>
					<Form.Group className="m-5 mt-4 mb-2">
						<Form.Label>Supplier Name: </Form.Label>
						<Form.Control type="text" onChange={(e) => {setSupplierName(e.target.value)}} placeholder="Enter supplier name" required />
					</Form.Group>

					<Form.Group className="m-5 mt-4 mb-2">
						<Form.Label>Stock Name: </Form.Label>
						<Form.Control type="text" onChange={(e) => {setStockName(e.target.value)}} placeholder="Enter stock name" required />
					</Form.Group>

					<Form.Group className="m-5 mt-4 mb-2">
						<Form.Label>Stock Type: </Form.Label>
						<Form.Select defaultValue="Condiments" onChange={(e) => {setStockType(e.target.value)}} required>
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
						offlabel='Weight' />
					</Form.Group>
					
					{
						isChecked ? (
							<Form.Group className="m-5 mt-4 mb-2">
								<Form.Label>Stock Quantity: </Form.Label>
								<Form.Control type="number" min="1" defaultname="stockQuantity" required
								onChange={(e) => {setStockQuantity(e.target.value)}} placeholder="Enter stock quantity" />
							</Form.Group>
						) : (
							<>
							<Form.Group className="m-5 mt-4 mb-2">
								<Form.Label>Stock Weight: </Form.Label>
								<Form.Control type="number" min="0.01" name="stockWeight" step="0.01" required
								onChange={(e) => {setStockWeight(e.target.value)}} placeholder="Enter stock weight per unit/total" />
							</Form.Group>

							<Form.Group className="m-5 mt-4 mb-2">
								<Form.Label>Unit of Measurement: </Form.Label>
								<Form.Control type="text" name="unitOfMeasurement" required 
								onChange={(e) => {setUnitOfMeasurement(e.target.value)}} placeholder="Enter unit of measurement" />
							</Form.Group>
							</>
						)
					}

					<Form.Group className="m-5 mt-4 mb-2">
						<Form.Label>Estimated Delivery Date: </Form.Label>
						<DateTimePicker className="border p-3 w-100 rounded-2" required name="estimatedDeliveryTime" format="dd-MM-yyyy"
						minDate={new Date()} onChange={setEstimatedDeliveryTime} value={estimatedDeliveryTime} />
					</Form.Group>

					<Form.Group className="m-5 mt-4 mb-2">
						<Form.Label>Cost of Stock (Total): </Form.Label>
						<Form.Control type="number" min="0.01" required name="costOfStock" step="0.01"
						onChange={(e) => {setCostOfStock(e.target.value)}} placeholder="Enter cost of stock" />
					</Form.Group>

					<div className="text-center p-4">
					<a className="me-1 btn btn-secondary" href="/readPurchaseHistory" role="button">Cancel</a>
						<Button variant="primary" type="submit" className="me-1">Create</Button>
					</div>
				</Form>
			</div>
		</>
		)
}

export default AdminCreatePurchaseHistory;