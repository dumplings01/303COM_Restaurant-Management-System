import React, { useState } from 'react';
import axios from 'axios';

import NavBar from '../NavBar';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

function AdminCreateStock() {

    const [name, setName] = useState([]);
    const [stockType, setStockType] = useState([]);
    const [stockQuantity, setStockQuantity] = useState([]);
    const [stockWeight, setStockWeight] = useState([]);
    const [unitOfMeasurement, setUnitOfMeasurement] = useState([]);

    function handleSubmit(e) {

        e.preventDefault();

        axios.post(`http://127.0.0.1:8080/stock/recordStock`,
                    {
                        name,
                        stockType,
                        stockQuantity,
                        stockWeight,
                        unitOfMeasurement
                    },
                    { headers: { "x-key": '123', } })
                    .then((res) => {
                        console.log(res);
                        if (res.status === 200) {
                            alert("Stock recorded successfully!");
                            window.location.assign("/readStockList")
                            return res;
                        }
                    }).catch((error) => {
                        console.log(error);
                        alert("Failed to create stock record!\n\nError: "+error.response.data);
                    })
                }
    

    return (
        <>
        <NavBar />
        <div className="d-flex justify-content-between mt-4 mb-4 ms-4 me-4">
        <Form onSubmit={handleSubmit}>
            <Form.Group className="m-5 mt-4 mb-2">
                <Form.Label>Name: </Form.Label>
                <Form.Control type="text" onChange={setName} placeholder="Enter name" required />
            </Form.Group>

            <Form.Group className="m-5 mt-4 mb-2">
                <Form.Label>Stock Type: </Form.Label>
                <Form.Control type="text" onChange={setStockType} placeholder="Enter stock type" required />
            </Form.Group>

            <Form.Group className="m-5 mt-4 mb-2">
                <Form.Label>Stock Quantity: </Form.Label>
                <Form.Control type="text" onChange={setStockQuantity} placeholder="Enter stock quantity" required />
            </Form.Group>

            <Form.Group className="m-5 mt-4 mb-2">
                <Form.Label>Stock Weight: </Form.Label>
                <Form.Control type="text" onChange={setStockWeight} placeholder="Enter stock weight" required />
            </Form.Group>

            <Form.Group className="m-5 mt-4 mb-2">
                <Form.Label>Unit of Measurement: </Form.Label>
                <Form.Control type="text" onChange={setUnitOfMeasurement} placeholder="Enter unit of measurement" required />
            </Form.Group>
            
            <div className="text-center p-4">
                <Button variant="primary" type="submit" className="me-1">Submit</Button>
            </div>
            </Form>
        </div>
        </>
    )
}

export default AdminCreateStock;