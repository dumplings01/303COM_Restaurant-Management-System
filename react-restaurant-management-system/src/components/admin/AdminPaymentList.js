import React, { useState, useEffect } from 'react';
import axios from 'axios';

import NavBar from '../NavBar';

import { DataGrid } from '@mui/x-data-grid';
import Button from 'react-bootstrap/Button';

function AdminPaymentList() {
    const [payments, setPayments] = useState([]);

    const columns = [
        { field: 'paymentId', headerName: 'PaymentID', width: 300, maxWidth: 320, flex:1, editable: false },
        { field: 'cardNumber', headerName: 'Card Number', maxWidth: 320, flex:1, editable: false },
        { field: 'cardholderName', headerName: 'Cardholder Name', maxWidth: 240, flex:1, editable: false },
        { field: 'expiryDate', headerName: 'Expiry Date', maxWidth: 100, flex:1, editable: false },
        { field: 'securityCode', headerName: 'Security Code', maxWidth: 100, flex:1, editable: false },
        { field: 'billingAddress', headerName: 'Billing Address', maxWidth: 150, flex:1, editable: false },
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
                        variant="danger"
                        onClick={(event) => {
                            handleDelete(event, cellValues);
                        }}>Delete</Button>
                    </>
                );
            }
        }
    ];

    function handleDelete(e, value) {
        const name = value.row.cardholderName;
        const paymentId = value.row.paymentId;
		var answer = window.confirm("Delete payment details for cardholder name: "+name+"?\n"+
                    "ACTIONS CANNOT BE UNDONE!");
		if (answer) {
			axios.delete(`http://127.0.0.1:8080/payment/deletePayment?paymentId=${paymentId}`)
						.then((res) => {
							console.log(res);
							if (res.status === 200) {
								alert("Payment details deleted successfully!");
                                window.location.reload(false);
								return res;
							}
						}).catch((error) => {
							console.log(error);
							alert("Failed to delete payment details!");
							window.location.reload(false);
						})
					};
	}

    useEffect(() => {

        var currentUserType = sessionStorage.getItem("currentUserType");
		var adminRoles = sessionStorage.getItem("adminRoles");
		const adminAccess = adminRoles.includes('owner');
		const reservationAccess = adminRoles.includes('manage reservations');
        
        if(currentUserType == null || currentUserType === "customer"){
            window.location.assign("/");
            alert("UNAUTHORIZED!");
            
        } else if (!adminAccess && !reservationAccess) {
            window.location.assign("/dashboard");
			alert("No access to manage reservations!");
		}

        const getAllPayments = async () => {
            try {
                const response = await axios.get(`http://127.0.0.1:8080/payment/getAllPayments`);
                // console.log(response);
                setPayments(response.data);
            } catch (e) {
                console.log(e);
            }
        };
        getAllPayments();
    }, []);

    return (
        <>
        <NavBar />
        <div style={{ height: '100%', width: '90%', margin: "auto"}}>
            <div className="d-flex justify-content-between p-4 px-2">
                <a className="me-1 btn btn-secondary" href="/dashboard" role="button">Back</a>
            </div>
            <h2 className="text-center align-self-center mb-3">Reservation Deposit Payments List</h2>
            <DataGrid
                rows={payments.map((item, index) => ({
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
    );

}

export default AdminPaymentList;