## Description

Booking Date is a Spring Boot-based reservation management application designed to streamline the process of making
reservations for various organizations. Whether it's reserving airplane seats, hotel rooms, event tickets, or any other
resource, "Booking Date" provides a centralized platform for users to easily make and manage their reservations. The
application offers a user-friendly interface, robust backend functionality, and efficient reservation management
capabilities, ensuring a seamless experience for both administrators and end-users.

## Tech Stack

<ul>
<li>
Spring-boot
</li>
<li>
Sql-server Data-base</li>
<li>Mockito for testing</li>
<li>JWT Authentication, Along with Spring-security</li>
<li>Lombok</li>
<li>JUnit for unit testing</li>
<li>H2 data-base for testing purposes</li>
</ul>

## Run It

<ul>
<li>
Make Sure of installing Sql server
</li>
<li>Create a database and configure application.yaml </li>
</ul>

### Features
<ol>
<li>Login - Register</li>
<li>
Roles
    <ul>
<li> Vendor</li>
<li> User</li>
</ul>
</li>
<li>User can access a room using access code</li>
<li>User choose a seat if it is empty</li>
<li>User un-choose a seat if he took it</li>
<li>User Search for his seats</li>
<li>Vendor Create Rooms</li>
<li>Vendor Create Seats for each room</li>
<li>Vendor choose a seat if it is empty for any user inside the room</li>
<li>Vendor un-choose a seat if he took it for any user inside the room</li>
<li>Search for all members both-roles</li>
<li>Search for all Rooms both-roles</li>
</ol>