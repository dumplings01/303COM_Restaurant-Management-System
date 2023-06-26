import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

import NavBar from '../NavBar';

import { DataGrid } from '@mui/x-data-grid';
import Button from 'react-bootstrap/Button';

function AdminReservationList() {

    let navigate = useNavigate();
    const [reservations, setReservations] = useState([]);

    const columns = [
        { field: 'reservationId', headerName: 'Reservation ID', width: 300, maxWidth: 320, flex:1, editable: false },
        { field: 'customerName', headerName: 'Customer Name', maxWidth: 320, flex:1, editable: false },
        { field: 'customerContact', headerName: 'Contact Number', maxWidth: 240, flex:1, editable: false },
        { field: 'reservationDate', headerName: 'Reservation Date', maxWidth: 100, flex:1, editable: false },
        { field: 'numberOfPeople', headerName: 'Number of People', maxWidth: 100, flex:1, editable: false },
        { field: 'customerRemarks', headerName: 'Customer Remarks', maxWidth: 150, flex:1, editable: false },
        { field: 'createdAt', headerName: 'Created At', maxWidth: 280, flex:1, editable: false },
        {
            field: 'status',
            headerName: 'Status',
            maxWidth: 280,
            flex:1,
            editable: false
        },
        { field: 'paymentId', headerName: 'Payment ID', maxWidth: 280, flex:1, editable: false },
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
        const reservationId = value.row.reservationId;
        navigate(`/updateReservation/${reservationId}`);
    }

    function handleDelete(e, value) {
        const name = value.row.customerName;
        const reservationId = value.row.reservationId;
		var answer = window.confirm("Delete reservation record for customer "+name+"?\n\n"+
                    "ONLY DELETE RESERVATION WHEN CANCELLED OR COMPLETED!");
		if (answer) {
			axios.delete(`http://127.0.0.1:8080/reservation/cancelReservation?reservationId=${reservationId}`)
						.then((res) => {
							console.log(res);
							if (res.status === 200) {
								alert("Reservation record deleted successfully!");
                                window.location.reload(false);
								return res;
							}
						}).catch((error) => {
							console.log(error);
							alert("Failed to delete reservation record!");
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

        const getAllReservation = async () => {
            try {
                const response = await axios.get(`http://127.0.0.1:8080/reservation/getAllReservation`);
                // console.log(response);
                setReservations(response.data);
            } catch (e) {
                console.log(e);
            }
        };
        getAllReservation();
    }, []);

    return (
        <>
        <NavBar />
        <div style={{ height: '100%', width: '90%', margin: "auto"}}>
            <div className="d-flex justify-content-between p-4 px-2">
                <a className="me-1 btn btn-secondary" href="/dashboard" role="button">Back</a>
            </div>
            <h2 className="text-center align-self-center mb-3">Reservations List</h2>
            <DataGrid
                rows={reservations.map((item, index) => ({
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

export default AdminReservationList;