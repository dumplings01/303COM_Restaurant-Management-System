import React, { useEffect} from 'react';
import NavBar from '../NavBar';


function AdminDashboard() {

    useEffect(() => {
        var currentUserType = sessionStorage.getItem("currentUserType");
        
        if(currentUserType == null || currentUserType === "customer"){
            alert("Unauthorized!");
            window.location.assign("/");
        }
    }, []);

    return (
        <>
        <NavBar />
        <div className="row mt-4 mb-4 ms-4 me-4 pt-4 pb-4 ps-4 pe-4">
            <div className="border border-dark col text-center p-4 m-2 d-flex flex-column">
                <h4><b><label>Stocks</label></b></h4>
                
                <ul>
                    <li><a href="createNewStock" className="hover-text">Create New Record</a></li>
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
                    <li><a href="adminCreateNewReservation" className="hover-text" >Create New Reservation</a></li>
                    <li><a href="adminReservationList" className="hover-text" >Reservation List</a></li>
                </ul>
            </div>
        </div>


        <div className="row mt-4 mb-4 ms-4 me-4 pt-4 pb-4 ps-4 pe-4">
            <div className="border border-dark col text-center p-4 m-2 d-flex flex-column">
                <h4><b><label>Loyalty Program</label></b></h4>

                <ul>
                    <li><a href="findCustomerProfile" className="hover-text" >Find Customer Profile</a></li>
                </ul>
                
            </div>

            <div className="border border-dark col text-center p-4 m-2 d-flex flex-column">
                <h4><b><label>Staff Profiles</label></b></h4>

                <ul>
                    <li><a href="createStaffProfile" className="hover-text" >Create Staff Profile</a></li>
                    <li><a href="readStaffProfile" className="hover-text" >Staff Profile List</a></li>
                </ul>
                
            </div>
            <div className="border border-0 col text-center p-4 m-2 d-flex flex-column">
                
            </div>

        </div>
        </>
    )
}

export default AdminDashboard;