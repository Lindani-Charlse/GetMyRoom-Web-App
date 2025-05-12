document.addEventListener("DOMContentLoaded", () => {
    const logoutButton = document.getElementById("logoutButton");

    // Logout button functionality (Redirect to a login page or perform logout)
    logoutButton.addEventListener("click", () => {
        alert("Logging out...");
        window.location.href = "main.html"; // Assuming you have a login page
    });
});

// Mock data to simulate students and rooms with additional fields
let students = [
    { 
        studentNumber: 'S12345', 
        surname: 'Doe', 
        fullName: 'John Doe', 
        gender: 'Male', 
        contactDetails: '555-1234', 
        password: 'password123', 
        room: 'Room 101' 
    },
    { 
        studentNumber: 'S12346', 
        surname: 'Smith', 
        fullName: 'Jane Smith', 
        gender: 'Female', 
        contactDetails: '555-5678', 
        password: 'password456', 
        room: 'Room 102' 
    },
    { 
        studentNumber: 'S12347', 
        surname: 'Brown', 
        fullName: 'Tom Brown', 
        gender: 'Male', 
        contactDetails: '555-8765', 
        password: 'password789', 
        room: 'Room 103' 
    }
];

let rooms = [
    { name: 'Room 101', status: 'Occupied', student: students[0] },
    { name: 'Room 102', status: 'Occupied', student: students[1] },
    { name: 'Room 103', status: 'Available', student: null },
    { name: 'Room 104', status: 'Available', student: null }
];

// Function to calculate the dashboard stats
function updateDashboardStats() {
    const totalStudents = students.length;
    const totalRooms = rooms.length;
    const availableRooms = rooms.filter(room => room.status === 'Available').length;
    const allocatedStudents = students.filter(student => student.room !== '').length;

    document.getElementById('totalStudents').textContent = totalStudents;
    document.getElementById('totalRooms').textContent = totalRooms;
    document.getElementById('availableRooms').textContent = availableRooms;
    document.getElementById('allocatedStudents').textContent = allocatedStudents;
}

// Function to render the students table
function renderStudentsTable() {
    const studentsTableBody = document.querySelector('#studentsTable tbody');
    studentsTableBody.innerHTML = '';

    students.forEach((student, index) => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${student.fullName}</td>
            <td>${student.studentNumber}</td> <!-- Display student number instead of room -->
            <td>
                <button onclick="editStudent(${index})">Edit</button>
                <button onclick="removeStudent(${index})">Remove</button>
                <button onclick="viewStudent(${index})">View</button>
            </td>
        `;
        studentsTableBody.appendChild(row);
    });
}

// Function to render the rooms table without Edit/Remove buttons
function renderRoomsTable() {
    const roomsTableBody = document.querySelector('#roomsTable tbody');
    roomsTableBody.innerHTML = '';

    rooms.forEach((room, index) => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${room.name}</td>
            <td>${room.status}</td>
            <td>
                <button onclick="viewRoom(${index})">View</button> <!-- Only View button -->
            </td>
        `;
        roomsTableBody.appendChild(row);
    });
}

// Function to view student details
function viewStudent(index) {
    const student = students[index];
    alert(`
        Student Number: ${student.studentNumber}
        Surname: ${student.surname}
        Full Name: ${student.fullName}
        Gender: ${student.gender}
        Contact Details: ${student.contactDetails}
        Password: ${student.password}
    `);
}

// Function to view room details
function viewRoom(index) {
    const room = rooms[index];
    if (room.student) {
        const student = room.student;
        alert(`
            Student Number: ${student.studentNumber}
            Room Number: ${room.name}
            Surname: ${student.surname}
            Full Name: ${student.fullName}
            Gender: ${student.gender}
        `);
    } else {
        alert(`Room ${room.name} is available.`);
    }
}

// Function to handle the logout button
document.getElementById('logoutButton').addEventListener('click', () => {
    alert('Logged out!');
    // You could add logic for actually logging out here, e.g., redirecting to a login page.
    window.location.href = '/login'; // Example redirect to login page
});

// Function to add a new student (mock action)
function addStudent() {
    const studentName = prompt('Enter student name:');
    const allocatedRoom = prompt('Enter room for the student:');
    students.push({ name: studentName, room: allocatedRoom });
    updateDashboardStats();
    renderStudentsTable();
}

// Function to edit a student's details
function editStudent(index) {
    const newName = prompt('Enter new name for student:', students[index].name);
    const newRoom = prompt('Enter new room for student:', students[index].room);
    students[index].name = newName;
    students[index].room = newRoom;
    updateDashboardStats();
    renderStudentsTable();
}

// Function to remove a student
function removeStudent(index) {
    students.splice(index, 1);
    updateDashboardStats();
    renderStudentsTable();
}

// Function to add a new room (mock action)
function addRoom() {
    const roomName = prompt('Enter room name:');
    const roomStatus = prompt('Enter room status (Available/Occupied):');
    rooms.push({ name: roomName, status: roomStatus });
    updateDashboardStats();
    renderRoomsTable();
}

// Function to edit room details
function editRoom(index) {
    const newName = prompt('Enter new name for room:', rooms[index].name);
    const newStatus = prompt('Enter new status for room:', rooms[index].status);
    rooms[index].name = newName;
    rooms[index].status = newStatus;
    updateDashboardStats();
    renderRoomsTable();
}

// Function to remove a room
function removeRoom(index) {
    rooms.splice(index, 1);
    updateDashboardStats();
    renderRoomsTable();
}

// Initial setup: render data and update stats
window.onload = function () {
    updateDashboardStats();
    renderStudentsTable();
    renderRoomsTable();
};
