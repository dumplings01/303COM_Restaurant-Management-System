import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import DateTimePicker from 'react-datetime-picker/';
import 'react-datetime-picker/dist/DateTimePicker.css';
import 'react-calendar/dist/Calendar.css';
import 'react-clock/dist/Clock.css';
import NavBar from '../NavBar';

function AdminCreateReservationSlots() {

    let navigate = useNavigate();

    const [date, setDate] = useState("");
    const [times, setTimes] = useState("");

    const [lists, setLists] = useState("");
    const timeSlots = ["10:00AM", "11:00AM", "12:00PM", "1:00PM", "2:00PM", "3:00PM", 
                        "4:00PM", "5:00PM", "6:00PM", "7:00PM", "8:00PM"]

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
    }, []);

    useEffect(() => {
        function generateList() {
            const list = [];
            for (const time of times) {
                const listItem = {
                  date,
                  time,
                };
                list.push(listItem);
              }
            setLists(list);
        }
        generateList();
    }, [times, date]);

    function handleCheckboxChange(e) {
        const { value, checked } = e.target;
    
		if (checked) {
			setTimes((prevRoles) => [...prevRoles, value]);
		} else {
			setTimes((prevRoles) => prevRoles.filter((role) => role !== value));
		}
	};

    

    function handleSubmit(e) {
        e.preventDefault();

        axios.post(`http://127.0.0.1:8080/slots/createSlots`,
                    lists
                    )
                    .then((res) => {
                        console.log(res);
                        if (res.status === 200) {
                            alert("Reservation slots created successfully!");
                            navigate("/reservationSlotsList")
                            return res;
                        }
                    }).catch((error) => {
                        console.log(error);
                        alert("Failed to create reservation!");
                    })
                    
    }

    console.log(lists)

    return (
        <>
        <NavBar />
        <h2 className="text-center mt-4 mb-4">Create Reservation Slots</h2>
        <div className="m-auto mt-3 mb-3 col-6 border rounded border-dark">

            <Form onSubmit={handleSubmit}>
                <Form.Group className="m-5 mt-4 mb-2">
                    <Form.Label className="pe-4">Date: </Form.Label>
                    <DateTimePicker className="border p-3 w-100 rounded-2" required name="date" format="dd-MM-yyyy"
                    minDate={new Date()} onChange={setDate} value={date} />
                </Form.Group>

                <Form.Group className="m-5 mt-4 mb-2 d-flex flex-column">
                    <Form.Label>Time Slots: </Form.Label>
                        {timeSlots.map((slot, index) => (
                            <label key={index}>
                                <Form.Check
                                    inline
                                    type="checkbox"
                                    value={slot}
                                    checked={times.includes(slot)}
                                    onChange={handleCheckboxChange}
                                />
                                {slot}
                            </label>
                        ))}
                </Form.Group>

                <div className="text-center p-4">
                    <Button variant="primary" type="submit" className="me-1">Submit</Button>
                </div>
            </Form>
        </div>
        </>
    )
}

export default AdminCreateReservationSlots;