import React, { useEffect} from 'react';
import NavBar from '../NavBar';
import axios from 'axios';

function AdminDashboard() {

    useEffect(() => {
        var currentUserType = sessionStorage.getItem("currentUserType");
        
        if(currentUserType == null || currentUserType === "customer"){
            alert("Unauthorized!");
            window.location.assign("/");
        }

        const getRoles = async () => {
            try {
                const response = await axios.get(`http://127.0.0.1:8080/admin/getAdminRoleByEmail?email=${sessionStorage.getItem("currentUser")}`);
                
                sessionStorage.setItem("userId", response.data.adminId);
                sessionStorage.setItem("userName", response.data.name);
                sessionStorage.setItem("adminRoles", response.data.roles);
            } catch (e) {
                console.log(e);
            }
        };
        getRoles();
    }, []);

    return (
        <>
        <NavBar />
        <div className="row mt-4 mb-4 ms-4 me-4 pt-4 pb-4 ps-4 pe-4">
            <div className="border border-dark col text-center p-4 m-2 d-flex flex-column">
                <h4><b><label>Stocks</label></b></h4>
                
                <ul>
                    <li><a href="createNewStock" className="hover-text">Create New Stock Record</a></li>
                    <li><a href="readStockList" className="hover-text">Stock List</a></li>
                </ul>
            </div>
            <div className="border border-dark col text-center p-4 m-2 d-flex flex-column">
                <h4><b><label>Supplier Purchase History</label></b></h4>

                <ul>
                    <li><a href="createPurchaseHistory" className="hover-text" >Create New Purchase History</a></li>
                    <li><a href="readPurchaseHistory" className="hover-text" >Purchase History List</a></li>
                </ul>
            </div>
            <div className="border border-dark col text-center p-4 m-2 d-flex flex-column">
                <h4><b><label>Reservations</label></b></h4>

                <ul>
                    <li><a href="adminReservationList" className="hover-text" >Reservation List</a></li>
                </ul>
            </div>
        </div>


        <div className="row mt-4 mb-4 ms-4 me-4 pt-4 pb-4 ps-4 pe-4">
            <div className="border border-dark col text-center p-4 m-2 d-flex flex-column">
                <h4><b><label>Loyalty Program</label></b></h4>

                <ul>
                    <li><a href="customerLoyalty" className="hover-text" >Find Customer Profile</a></li>
                </ul>
                
            </div>

            <div className="border border-dark col text-center p-4 m-2 d-flex flex-column">
                <h4><b><label>Staff Profiles</label></b></h4>

                <ul>
                    <li><a href="createStaffProfile" className="hover-text" >Create Staff Account</a></li>
                    <li><a href="staffList" className="hover-text" >Staff List</a></li>
                </ul>
                
            </div>
            <div className="border border-dark col text-center p-4 m-2 d-flex flex-column">
                <h4><b><label>Reservation Slots</label></b></h4>

                <ul>
                    <li><a href="createReservationSlots" className="hover-text" >Create Reservation Slots</a></li>
                    <li><a href="reservationSlotsList" className="hover-text" >Slot List</a></li>
                </ul>
            </div>

        </div>
        </>
    )
}

export default AdminDashboard;