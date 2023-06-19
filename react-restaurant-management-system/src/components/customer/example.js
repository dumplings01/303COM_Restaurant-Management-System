import React, { useState, useEffect } from 'react';

const ReservationForm = () => {
  const [selectedDate, setSelectedDate] = useState('');
  const [selectedTime, setSelectedTime] = useState('');
  const [availableDates, setAvailableDates] = useState([]);
  const [blockedDates, setBlockedDates] = useState([]);
  const [availableTimes, setAvailableTimes] = useState([]);
  const [blockedTimes, setBlockedTimes] = useState([]);

  useEffect(() => {
    // Simulated API calls to fetch available and blocked dates and times
    fetchAvailableDates();
    fetchBlockedDates();
    fetchAvailableTimes();
    fetchBlockedTimes();
  }, []);

  const fetchAvailableDates = () => {
    // Fetch available dates from API and update the state
    const dates = ['2023-06-20', '2023-06-21', '2023-06-22'];
    setAvailableDates(dates);
  };

  const fetchBlockedDates = () => {
    // Fetch blocked dates from API and update the state
    const dates = ['2023-06-23', '2023-06-24'];
    setBlockedDates(dates);
  };

  const fetchAvailableTimes = () => {
    // Fetch available times from API and update the state
    const times = ['10:00 AM', '12:00 PM', '2:00 PM'];
    setAvailableTimes(times);
  };

  const fetchBlockedTimes = () => {
    // Fetch blocked times from API and update the state
    const times = ['1:00 PM', '3:00 PM'];
    setBlockedTimes(times);
  };

  const handleDateChange = (event) => {
    setSelectedDate(event.target.value);
  };

  const handleTimeChange = (event) => {
    setSelectedTime(event.target.value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    // Process the reservation submission, e.g., send data to server or store locally
    console.log('Reservation submitted:', selectedDate, selectedTime);
    // Reset form values
    setSelectedDate('');
    setSelectedTime('');
  };

  // Filter available and blocked options based on selected date
  const filteredAvailableTimes = availableTimes.filter(
    (time) => !blockedTimes.includes(time)
  );

  const filteredAvailableDates = availableDates.filter(
    (date) => !blockedDates.includes(date)
  );

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label>Select Date:</label>
        <select value={selectedDate} onChange={handleDateChange}>
          <option value="">-- Select Date --</option>
          {filteredAvailableDates.map((date) => (
            <option key={date} value={date}>
              {date}
            </option>
          ))}
        </select>
      </div>
      <div>
        <label>Select Time:</label>
        <select value={selectedTime} onChange={handleTimeChange}>
          <option value="">-- Select Time --</option>
          {filteredAvailableTimes.map((time) => (
            <option key={time} value={time}>
              {time}
            </option>
          ))}
        </select>
      </div>
      <button type="submit">Submit Reservation</button>
    </form>
  );
};

export default ReservationForm;