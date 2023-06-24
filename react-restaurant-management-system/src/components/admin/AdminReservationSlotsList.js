import React, { useEffect, useState } from 'react';
import axios from 'axios';

import { DataGrid } from '@mui/x-data-grid';
import Button from 'react-bootstrap/Button';

import NavBar from '../NavBar';

function AdminReservationSlotsList() {

    const [slot, setSlot] = useState([]);

    const columns = [
        { field: 'slotId', headerName: 'Slot ID', maxWidth: 320, flex:1, editable: false },
        { field: 'date', headerName: 'Date', flex:1, editable: false },
        { field: 'time', headerName: 'Time', flex:1, editable: false },
        { field: 'status', headerName: 'Status', flex:1, editable: false },
        { field: 'reservationId', headerName: 'Reservation ID', flex:1, editable: false },
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
        const date = value.row.date;
        const time = value.row.time;
        const slotId = value.row.slotId;
		var answer = window.confirm("Delete slot for "+date+" "+time+"?");
		if (answer) {
			axios.delete(`http://127.0.0.1:8080/slots/deleteSlot?slotId=${slotId}`)
						.then((res) => {
							console.log(res);
							if (res.status === 200) {
								alert("Slot deleted successfully!");
                                window.location.reload(false);
								return res;
							}
						}).catch((error) => {
							console.log(error);
							alert("Failed to delete slot!");
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
            
        const getSlots = async () => {
            try {
                const response = await axios.get(`http://127.0.0.1:8080/slots/getAllSlots`);
                setSlot(response.data);
            } catch (e) {
                console.log(e);
            }
        };
        getSlots();
    }, []);

    return (
        <>
            <NavBar />
            <div style={{ height: '100%', width: '90%', margin: "auto"}}>
                <div className="d-flex justify-content-between p-4 px-2">
					<a className="me-1 btn btn-secondary align-self-center" href="/dashboard" role="button">Back</a>
                    <h2 className="text-center align-self-center">Reservation Slots List</h2>
                    <a className="me-1 btn btn-success align-self-center" href="/createReservationSlots" role="button">Create New Slots</a>
				</div>
                <DataGrid
                    rows={slot.map((item, index) => ({
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
export default AdminReservationSlotsList;