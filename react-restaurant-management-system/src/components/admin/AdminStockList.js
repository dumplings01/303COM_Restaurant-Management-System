import React, { useEffect, useState } from 'react';
import {useNavigate} from 'react-router-dom';
import axios from 'axios';

import { DataGrid } from '@mui/x-data-grid';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';

import NavBar from '../NavBar';

function AdminStockList() {

    let navigate = useNavigate();

    const [stock, setStock] = useState([]);
    
    const columns = [
        { field: 'stockId', headerName: 'Stock ID', width: 300, maxWidth: 320, flex:1, editable: false },
        { field: 'name', headerName: 'Name', maxWidth: 320, flex:1, editable: false },
        { field: 'stockType', headerName: 'Type', maxWidth: 240, flex:1, editable: false },
        { field: 'stockQuantity', headerName: 'Quantity', maxWidth: 100, flex:1, editable: false },
        { field: 'stockWeight', headerName: 'Weight', maxWidth: 100, flex:1, editable: false },
        { field: 'unitOfMeasurement', headerName: 'Unit of Measurement', maxWidth: 150, flex:1, editable: false },
        { field: 'updatedAt', headerName: 'Updated At', maxWidth: 280, flex:1, editable: false },
        {
            field: "actions",
            headerName: "Actions",
            minWidth: 160,
            maxWidth: 170,
            flex: 1,
            sortable: false,
            renderCell: (cellValues) => {
                return (
                    <>
                    <Button
                        variant="primary"
                        className="mx-1"
                        onClick={(event) => {
                            handleEdit(event, cellValues);
                        }}>Edit</Button>
                    <Button
                        variant="danger"
                        onClick={(event) => {
                            handleDelete(event, cellValues);
                        }}>Delete</Button>
                    </>
                );
            }
        }
    ];

    function handleEdit(e, value){
        const stockId = value.row.stockId;
        navigate(`/editStock/${stockId}`);
    }

    function handleDelete(e, value) {
        const name = value.row.name;
        const stockId = value.row.stockId;
		var answer = window.confirm("Delete stock record for "+name+"?");
		if (answer) {
			axios.delete(`http://127.0.0.1:8080/stock/deleteStock?stockId=${stockId}`)
						.then((res) => {
							console.log(res);
							if (res.status === 200) {
								alert("Stock record deleted successfully!");
                                window.location.reload(false);
								return res;
							}
						}).catch((error) => {
							console.log(error);
							alert("Failed to delete stock record!");
							window.location.reload(false);
						})
					};
	}

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
            
        const getStocks = async () => {
            try {
                const response = await axios.get(`http://127.0.0.1:8080/stock/getAllStocks`);
                setStock(response.data);
            } catch (e) {
                console.log(e);
            }
        };
        getStocks();
    }, []);

    const getSelectedValue = (stock) => {
        return stock.stockQuantity || stock.stockWeight;
    };

    const renderStocks = () => {
        const lowStocks = stock.filter(stock => {
            const selectedValue = getSelectedValue(stock);
            return selectedValue < stock.lowStockAlertAt;
        });
 
        if (lowStocks.length === 0) {
            return (
                <Card className="w-75 mx-auto">
                    <Card.Body>
                        <Card.Text>
                            <p>No low stock alerts</p>
                        </Card.Text>
                    </Card.Body>
                </Card>
            )
            
        }

        return lowStocks.map(stock => (
            <div key={stock.id}>
                <Card className="mb-2 w-75 mx-auto">
                    <Card.Body>
                        <Card.Text>
                            <p>Stock Name: {stock.name}</p>
                            <p>Stock Quantity/Weight: {getSelectedValue(stock)}</p>
                            <p>Low Stock Alert At: {stock.lowStockAlertAt}</p>
                        </Card.Text>
                    </Card.Body>
                </Card>
                
            </div>
        ));
    };

    return (
        <>
            <NavBar />
            <div style={{ height: '100%', width: '90%', margin: "auto"}}>
                <div className="d-flex justify-content-between p-4 px-2">
					<a className="me-1 btn btn-secondary align-self-center" href="/dashboard" role="button">Back</a>
                    <h2 className="text-center align-self-center">Stock List</h2>
                    <a className="me-1 btn btn-success align-self-center" href="/createNewStock" role="button">Create New Stock Record</a>
				</div>
                <div className="d-flex row p-4 px-2 mb-3">
                    <h2 className="text-center align-self-center">Low in Stock</h2>
                    {renderStocks()}
				</div>
                <DataGrid
                    rows={stock.map((item, index) => ({
                        id: index + 1,
                        ...item
                    }))}
                    columns={columns}
                    initialState={{
                    pagination: {
                        paginationModel: { page: 0, pageSize: 10 },
                    },
                    }}
                    pageSizeOptions={[5,10,20]}
                />
            </div>
        </>
    )
    
}

export default AdminStockList;