```markdown
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
   ```
   git clone https://github.com/Joy-K-Wambui/littlesteps-daycare.git  
   cd littlesteps-daycare  
   ```

3. Set up the database:  
   - Create a new MySQL database named `littlesteps`.  
   - Import the provided SQL scripts located in the `/db` folder to initialize the database structure.  

4. Update Database Configuration:  
   - Modify the `config.properties` file in the `/src/main/resources` directory to include your MySQL credentials.  

5. Build the project:  
   ```
   gradle build  
   ```

6. Run the application:  
   ```
   gradle run  
   ```

Usage  
- After successful execution, the application will open in a new window.  
- Users can log in using their credentials.  
- Once logged in, users will have access based on their assigned roles (Admin, Staff, Parent).  
- Navigate through the menus to access different features such as Attendance, Billing, Events, and Notifications.  

Contributing  
We welcome contributions to the LittleSteps Daycare project! If you would like to contribute, please follow these steps:  
1. Fork the repository.  
2. Create a new branch (`git checkout -b feature/YourFeatureName`).  
3. Make your changes and commit (`git commit -m 'Add your feature'`).  
4. Push to the branch (`git push origin feature/YourFeatureName`).  
5. Open a pull request.  

Contact  
For any inquiries or further information, please reach out to:  
- Email: www.renkambuwa@gmail.com

Thank you for your interest in the LittleSteps Daycare project! I hope you find it useful and contribute to its development.
```

