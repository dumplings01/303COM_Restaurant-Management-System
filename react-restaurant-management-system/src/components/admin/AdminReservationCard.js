// import axios from 'axios';
// import {useNavigate} from 'react-router-dom';

// import Button from 'react-bootstrap/Button';
// import Card from 'react-bootstrap/Card';

// // {} means props
// function ProfileCard ({profile}) {

//     const {programmerId, name, username, password, contactNumber, email, skills, joinDate,
//         status, createdBy, createdAt, updatedAt} = profile;

// 	function handleDelete(e) {
// 		var answer = window.confirm("Delete profile of "+username+"?");
// 		if (answer) {
// 			axios.delete(`http://127.0.0.1:8080/programmer/deleteProgrammer?programmerId=${programmerId}`,
// 						{ headers: { "x-key": '123', } })
// 						.then((res) => {
// 							console.log(res);
// 							if (res.status === 200) {
// 								alert("Programmer profile deleted successfully!");
// 								return res;
// 							}
// 						}).catch((error) => {
// 							console.log(error);
// 							alert("Failed to delete programmer profile!");
							
// 						})
// 					};
// 					window.location.assign("/getProfiles");
// 		}

// 	const navigate = useNavigate();
	
// 	function handleUpdate() {
// 		navigate(`/updateProfile/${programmerId}`);
// 	};

//     return (
// 		<>
//         <Card className="m-5">
// 		<Card.Header as="h5">{name}</Card.Header>
// 		<Card.Body>
// 			<Card.Title as="h6">Programmer ID: {programmerId}</Card.Title>
// 			<Card.Text>
// 				<>
// 				<br></br>
// 					<span><b>Username: </b>{username}</span>
// 					<span><b>Password: </b>{password}</span>
// 					<span><b>Contact number: </b>{contactNumber}</span>
// 					<span><b>Email: </b>{email}</span>
// 					<br></br>
// 					<span><b>Skills:</b></span>
// 				</>
// 			</Card.Text>
// 			<ol>
// 			{skills.map((skill, index) => {
// 				return (
// 					<li key={index}>{skill}</li>
// 				)
// 			})}
// 			</ol>
// 			<Card.Text>
// 				<>
// 				<br></br>
//                     <span><b>Join date: </b>{joinDate}</span>
//                     <span><b>Status: </b>{status}</span>
// 					<span><b>Created by: </b>{createdBy}</span>
// 					<span><b>Created at: </b>{createdAt}</span>
// 					<span><b>Updated at: </b>{updatedAt}</span>
// 				</>
// 			</Card.Text>
// 			<div className="d-flex justify-content-between">
// 				<Button variant="primary" className="w-50 me-2 ms-2" onClick={() => handleUpdate(programmerId)}>Update</Button>
// 				<Button variant="danger" className="w-50" onClick={handleDelete}>Delete</Button>
// 			</div>
// 		</Card.Body>
// 		</Card>
// 		</>
//     );
// }

// export default ProfileCard;