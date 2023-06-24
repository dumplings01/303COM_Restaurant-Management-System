import React, { useEffect, useState } from 'react';
import {useNavigate} from 'react-router-dom';
import axios from 'axios';

import { DataGrid } from '@mui/x-data-grid';
import Button from 'react-bootstrap/Button';

import NavBar from '../NavBar';

function AdminPurchaseHistoryList() {

	let navigate = useNavigate();

    const [purchaseHistory, setPurchaseHistory] = useState([]);

	const columns = [
        { field: 'supplierName', headerName: 'Supplier Name', maxWidth: 320, flex:1, editable: false },
		{ field: 'stockName', headerName: 'Stock Name', maxWidth: 320, flex:1, editable: false },
        { field: 'stockType', headerName: 'Type', maxWidth: 240, flex:1, editable: false },
        { field: 'stockQuantity', headerName: 'Quantity', maxWidth: 80, flex:1, editable: false },
        { field: 'stockWeight', headerName: 'Weight', maxWidth: 70, flex:1, editable: false },
        { field: 'unitOfMeasurement', headerName: 'Unit of Measurement', maxWidth: 60, flex:1, editable: false },
		{ field: 'costOfStock', headerName: 'Cost of Stock', maxWidth: 80, flex:1, editable: false },
        { field: 'estimatedDeliveryDate', headerName: 'Estimated Delivery Date', maxWidth: 320, flex:1, editable: false },
		{ field: 'dateDelivered', headerName: 'Date Delivered', maxWidth: 320, flex:1, editable: false },
		{ field: 'stockCondition', headerName: 'Stock Condition', maxWidth: 320, flex:1, editable: false },
		{ field: 'deliveryFeedback', headerName: 'Delivery Feedback', maxWidth: 320, flex:1, editable: false },
		{ field: 'status', headerName: 'Status', maxWidth: 320, flex:1, editable: false },
		{ field: 'createdAt', headerName: 'Created At', maxWidth: 280, flex:1, editable: false },
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
        const supplierId = value.row.supplierId;
        navigate(`/editPurchaseHistory/${supplierId}`);
    }

    function handleDelete(e, value) {
        const supplierName = value.row.supplierName;
        const supplierId = value.row.supplierId;
		var answer = window.confirm("Delete supplier purchase history for "+supplierName+"?");
		if (answer) {
			axios.delete(`http://127.0.0.1:8080/supplier/deleteSupplierHistory?supplierId=${supplierId}`)
						.then((res) => {
							console.log(res);
							if (res.status === 200) {
								alert("Supplier purchase history deleted successfully!");
                                window.location.reload(false);
								return res;
							}
						}).catch((error) => {
							console.log(error);
							alert("Failed to delete purchase history!");
							window.location.reload(false);
						})
					};
	}

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

        const getPurchaseHistory = async () => {
            try {
                const response = await axios.get(`http://127.0.0.1:8080/supplier/getAllSupplierHistory`);
                setPurchaseHistory(response.data);
            } catch (e) {
                console.log(e);
            }
        };
        getPurchaseHistory();
    }, []);

	return (
		<>
			<NavBar />
			<div style={{ height: '100%', width: '90%', margin: "auto"}}>
                <div className="d-flex justify-content-between p-4 px-2">
					<a className="me-1 btn btn-secondary align-self-center" href="/dashboard" role="button">Back</a>
                    <h2 className="text-center align-self-center">Suppliers Purchase History List</h2>
                    <a className="me-1 btn btn-success align-self-center" href="/createPurchaseHistory" role="button">Create New Purchase History</a>
				</div>
                <DataGrid
                    rows={purchaseHistory.map((item, index) => ({
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

export default AdminPurchaseHistoryList;