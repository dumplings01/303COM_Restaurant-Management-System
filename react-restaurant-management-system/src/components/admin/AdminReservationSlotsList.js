function AdminReservationSlotsList() {
    let navigate = useNavigate();

    const [slot, setSlot] = useState([]);

    const columns = [
        { field: 'slotId', headerName: 'Slot ID', width: 300, maxWidth: 320, flex:1, editable: false },
        { field: 'date', headerName: 'Date', maxWidth: 320, flex:1, editable: false },
        { field: 'time', headerName: 'Time', maxWidth: 240, flex:1, editable: false },
        { field: 'status', headerName: 'Status', maxWidth: 100, flex:1, editable: false },
        { field: 'reservationId', headerName: 'Reservation ID', maxWidth: 100, flex:1, editable: false },
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
}
export default AdminReservationSlotsList;