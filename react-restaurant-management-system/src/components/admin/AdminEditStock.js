import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';

import NavBar from '../NavBar';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import BootstrapSwitchButton from 'bootstrap-switch-button-react'

function AdminEditStock() {

    let {stockId} = useParams();
    let navigate = useNavigate();

    const [name, setName] = useState("");
    const [stockType, setStockType] = useState("");
    const [stockQuantity, setStockQuantity] = useState("");
    const [stockWeight, setStockWeight] = useState("");
    const [unitOfMeasurement, setUnitOfMeasurement] = useState("");

	const [isChecked, setIsChecked] = useState(true);

    useEffect(() => {
        var currentUserType = sessionStorage.getItem("currentUserType");
        var adminRoles = sessionStorage.getItem("adminRoles");
        const adminAccess = adminRoles.includes('owner');
        const inventoryAccess = adminRoles.includes('manage inventory');
        
        if(currentUserType == null || currentUserType === "customer"){
            window.location.assign("/");
            alert("UNAUTHORIZED!");
            
        } else if (!adminAccess && !inventoryAccess) {
            window.location.assign("/dashboard");
            alert("No access to manage stocks!");
        }
    }, []);

    useEffect(() => {
        const getStockDetails = async () => {
            try {
                const response = await axios.get(`http://127.0.0.1:8080/stock/getSingleStock?stockId=${stockId}`);
                //console.log(response);
                setName(response.data.name);
                setStockType(response.data.stockType);
                setStockQuantity(response.data.stockQuantity);
                setStockWeight(response.data.stockWeight);
                setUnitOfMeasurement(response.data.unitOfMeasurement);
                if (response.data.stockQuantity===null || response.data.stockQuantity==="") {
                    setIsChecked(false);
                }
                
            } catch (e) {
                console.log(e);
            }
        };
        getStockDetails();
    },[stockId]);

    function handleToggle() {
		setStockQuantity("");
		setStockWeight("");
		setUnitOfMeasurement("");
		setIsChecked(!isChecked);
	}

    function handleSubmit(e, value){
        e.preventDefault();

        if ((stockQuantity==="" && stockWeight==="") || (stockQuantity===null && stockWeight==="")
            || (stockQuantity==="" && stockWeight===null) || (stockQuantity===null && stockWeight===null)) {
            alert("Must have at least one stock measurement value!")
        } else {
            axios.put(`http://127.0.0.1:8080/stock/updateStock?stockId=${stockId}`,
            {
                name,
                stockType,
                stockQuantity,
                stockWeight,
                unitOfMeasurement
            })
            .then((res) => {
                console.log(res);
                if (res.status === 200) {
                    alert("Stock details updated successfully!");
                    navigate("/readStockList");
                    return res;
                }
            }).catch((error) => {
                console.log(error);
                alert("Failed to update stock details!\n"+error.response.data);
            })
        }
    }

    //console.log(name+" "+stockType+" "+stockQuantity+" "+stockWeight+" "+unitOfMeasurement)
          
    return (
        <>
        <NavBar />
        <h2 className="text-center mt-4 mb-4">Update Stock Record</h2>
		<div className="m-auto mt-3 mb-3 col-6 border rounded border-dark">
            <Form onSubmit={handleSubmit}>
                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Name: </Form.Label>
                    <Form.Control type="text" value={name} onChange={(e) => {setName(e.target.value)}} placeholder="Enter name" required />
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label>Stock Type: </Form.Label>
                    <Form.Select value={stockType} onChange={(e) => {setStockType(e.target.value)}} required>
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
                            <Form.Control type="number" min="1" defaultname="stockQuantity" value={stockQuantity} required
                            onChange={(e) => {setStockQuantity(e.target.value)}} placeholder="Enter stock quantity" />
                        </Form.Group>
                    ) : (
                        <>
                        <Form.Group className="m-5 mt-4 mb-2">
                            <Form.Label>Stock Weight: </Form.Label>
                            <Form.Control type="number" min="0.01" name="stockWeight" step="0.01" value={stockWeight} required
                            onChange={(e) => {setStockWeight(e.target.value)}} placeholder="Enter stock weight per unit/total" />
                        </Form.Group>

                        <Form.Group className="m-5 mt-4 mb-2">
                            <Form.Label>Unit of Measurement: </Form.Label>
                            <Form.Control type="text" name="unitOfMeasurement" value={unitOfMeasurement} required 
                            onChange={(e) => {setUnitOfMeasurement(e.target.value)}} placeholder="Enter unit of measurement" />
                        </Form.Group>
                        </>
                    )
                }

                <div className="text-center p-4">
                    <a className="me-1 btn btn-secondary" href="/readStockList" role="button">Cancel</a>
                    <Button variant="primary" type="submit" className="me-1">Update</Button>
                </div>
            </Form>
        </div>
        </>
    )
}

export default AdminEditStock;