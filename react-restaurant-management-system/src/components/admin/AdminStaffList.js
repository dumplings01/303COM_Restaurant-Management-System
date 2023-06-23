import React, { useEffect, useState } from 'react';
import {useNavigate} from 'react-router-dom';
import axios from 'axios';

import { DataGrid } from '@mui/x-data-grid';
import Button from 'react-bootstrap/Button';

import NavBar from '../NavBar';

function AdminStaffList() {

    let navigate = useNavigate();

    const [admin, setAdmin] = useState([]);

    const columns = [
        { field: 'adminId', headerName: 'Admin ID', maxWidth: 300, flex:1, editable: false },
        { field: 'name', headerName: 'Name', maxWidth: 300, flex:1, editable: false },
        { field: 'email', headerName: 'Email', maxWidth: 260, flex:1, editable: false },
        {
            field: 'roles',
            headerName: 'Roles',
            sortable: false,
            minWidth: 280,
            flex:1 ,
            editable: false
        },
        {
            field: "actions",
            headerName: "Actions",
            minWidth: 170,
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
        const adminId = value.row.adminId;
        navigate(`/editStaff/${adminId}`);
    }

    function handleDelete(e, value) {
        const name = value.row.name;
        const adminId = value.row.adminId;
		var answer = window.confirm("Delete admin account for "+name+"?");
		if (answer) {
			axios.delete(`http://127.0.0.1:8080/admin/deleteAdminProfile?adminId=${adminId}`)
						.then((res) => {
							console.log(res);
							if (res.status === 200) {
								alert("Admin account deleted successfully!");
                                window.location.reload(false);
								return res;
							}
						}).catch((error) => {
							console.log(error);
							alert("Failed to delete account!");
							window.location.reload(false);
						})
					};
		}

    useEffect(() => {
        var currentUserType = sessionStorage.getItem("currentUserType");
		var adminRoles = sessionStorage.getItem("adminRoles");
		const access = adminRoles.includes('owner');
        
        if(currentUserType == null || currentUserType === "customer"){
            window.location.assign("/");
            alert("UNAUTHORIZED!");
            
        } else if (!access) {
            window.location.assign("/dashboard");
			alert("No access to manage staff list!");
		}
            
        const getStaffs = async () => {
            try {
                const response = await axios.get(`http://127.0.0.1:8080/admin/getAllStaffs`);
                setAdmin(response.data);
            } catch (e) {
                console.log(e);
            }
        };
        getStaffs();

    }, []);
        
    return (
        <>
            <NavBar />
            <div style={{ height: '100%', width: '90%', margin: "auto"}}>
                <div className="d-flex justify-content-between p-4 px-2">
					<a className="me-1 btn btn-secondary align-self-center" href="/dashboard" role="button">Back</a>
                    <h2 className="text-center align-self-center">Staff List</h2>
                    <a className="me-1 btn btn-success align-self-center" href="/createStaffProfile" role="button">Create New Staff Account</a>
				</div>
                <DataGrid
                    rows={admin.map((item, index) => ({
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

export default AdminStaffList;