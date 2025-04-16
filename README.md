LittleSteps Daycare Project

Table of Contents
1. Introduction  
2. Project Overview  
3. Technologies Used  
4. Features  
5. Database Structure  
6. Setup Instructions  
7. Usage  
8. Contributing  
9. Contact  

Introduction  
Welcome to the LittleSteps Daycare project! This project aims to streamline daycare management by providing an easy-to-use platform for attendance tracking, billing, event management, and communication between parents and staff. The LittleSteps Daycare system is designed to be intuitive, efficient, and user-friendly, catering to the needs of parents, caretakers, and staff.

Project Overview  
The LittleSteps Daycare project offers a comprehensive web application built using Java and FXML for the front-end, a MySQL database for data storage, and a structured back-end to handle interactions between the front-end and database. The application allows daycare staff to manage children’s records, billing processes, events, and health records efficiently.

Technologies Used  
- Java: The primary programming language for back-end development.  
- FXML: User interface markup language used for building the front end.  
- MySQL: The relational database management system used to store and manage persistent data.  
- Gradle: For project management and build automation.  
- JavaFX: Framework for building rich desktop applications.  

Features  
- Attendance Tracking: Record and manage attendance for each child during the day.  
- Billing Management: Generate invoices, track payments, and manage due dates.  
- Event Management: Schedule, create, and manage events pertinent to the daycare.  
- Child Records: Maintain detailed records for each child, including health records and parental information.  
- Notifications: Send out communication to parents regarding important updates and messages.  
- User Roles: Manage different user roles (e.g., Admin, Staff, Parents) to restrict and allow access to various features and data.  

Database Structure  
The project is backed by a robust database structure consisting of the following tables:  
1. attendance: Tracks attendance records.  
2. billing: Handles billing information for daycare services.  
3. child_parent: Manages relationships between children and their parents.  
4. children: Contains records for children enrolled in the daycare.  
5. events: Stores details about daycare events.  
6. healthrecords: Maintains health-related records for children.  
7. invoice: Manages invoicing information.  
8. notifications: Facilitates notification management.  
9. parents: Stores details about parents/guardians of the children.  
10. staff: Manages information about staff members.  
11. tenants: Contains data for different tenants using the daycare service.  
12. users: Handles user authentication and roles.  

Each table includes various fields tailored to their functions, as described in the project context.

Setup Instructions  
1. Prerequisites:  
   - Java Development Kit (JDK) installed (version 8 or newer).  
   - Gradle installed for building the project.  
   - MySQL server installed and configured.  

2. Clone the repository:  
