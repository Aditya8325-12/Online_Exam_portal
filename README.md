# Online_Exam_portal

## Overview

The Online Exam Portal is a comprehensive exam management system designed for both students and teachers. The project is divided into two main parts: a mobile application for students and an admin section for teachers. The system includes features for managing user records, creating and administering tests, and tracking student performance.

## Features

### Student Mobile Application

- **Login & Registration**: Secure login and registration system for students.
- **Student Records**: Manage and view student information and test results.
- **Chapter-wise Test**: Students can take tests based on chapters, with the next test unlocking upon achieving a specific score (e.g., 9 out of 10).
- **Weekly Test**: Tests are scheduled weekly with a specific time limit. Each test can be attempted only once.

### Teacher/Admin Section

- **Test Creation**: Teachers can create and configure different types of tests.
- **User Management**: Admins can manage student and teacher accounts and monitor performance.

## Technologies Used

- **Backend**: PHP
- **Mobile Application**: Android Studio (Java)
- **Database**: MySQL

## Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/your-username/online-exam-portal.git
    ```

2. Navigate to the project directory:
    ```bash
    cd online-exam-portal
    ```

3. Set up the backend:
    - Configure your PHP environment.
    - Import the MySQL database schema from `database.sql`.

4. Set up the mobile application:
    - Open the project in Android Studio.
    - Build and run the app on an emulator or physical device.

5. Configure API endpoints and other settings as needed.

6. admin section /php file
   - go to Exam file 
   - php code file : PHP/Exam
   - download the this php file.

8. php file 
  - step 1: set the Exam file in the local database  
  - step 2:  create the database name as exam_gate 
  - step 3: export the sql file in the database 

8. android application 
 - step 1 : open the android apllication in the android studio 
 - step 2 : go to the values/ string file 
 - step 3 : in string file update youre local ip address in the server string 

## Usage

- **Students**: Log in to the mobile application to access your tests and records.
- **Teachers/Admins**: Use the admin section to create tests, manage users, and view performance metrics.


## Contact

For any questions or issues, please contact [adityadhutraj@gmail.com](mailto:adityadhutraj@gmail.com).

