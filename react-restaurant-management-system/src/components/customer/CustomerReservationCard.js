import axios from 'axios';
import {useNavigate} from 'react-router-dom';

import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';

// {} means props
function CustomerReservationCard ({reservation}) {

	let navigate = useNavigate();

    const {reservationId, customerId, customerName, customerContact, reservationDate,
            numberOfPeople, customerRemarks, createdAt, status} = reservation;

	function handleCancel(e) {
		var answer = window.confirm("Cancel reservation for "+reservationDate+"?");
		if (answer) {
			axios.delete(`http://127.0.0.1:8080/reservation/cancelReservation?reservationId=${reservationId}`)
						.then((res) => {
							console.log(res);
							if (res.status === 200) {
								alert("Reservation cancelled successfully!");
								return res;
							}
						}).catch((error) => {
							console.log(error);
							alert("Failed to cancel reservation!");
							
						})
					};
					navigate("/customerViewReservation");
		}
	
	function handleUpdate() {
		navigate(`/customerEditReservation/${reservationId}`);
	};

    return (
		<>
        <Card className="m-5">
		<Card.Header as="h5">{reservationDate}</Card.Header>
		<Card.Body>
			<Card.Text>
				<>
				<br></br>
					<span><b>Customer ID: </b>{customerId}</span><br></br>
					<span><b>Reserved by: </b>{customerName}</span><br></br>
					<span><b>Contact number: </b>{customerContact}</span><br></br>
					<span><b>Number of people: </b>{numberOfPeople}</span><br></br>
					<span><b>Remarks: </b>{customerRemarks}</span><br></br>
                    <span><b>Reservation created at: </b>{createdAt}</span><br></br>
                    <span><b>Status: </b>{status}</span><br></br>
				</>
			</Card.Text>
			<div className="d-flex justify-content-between">
				<Button variant="primary" className="w-50 me-2 ms-2" onClick={() => handleUpdate(reservationId)}>Edit Reservation Details</Button>
				<Button variant="danger" className="w-50" onClick={handleCancel}>Cancel Reservation</Button>
			</div>
		</Card.Body>
		</Card>
		</>
    );
}

export default CustomerReservationCard;