# Hands On

## Goal
Practice and get familiar with SQL syntax!

### Note
The [`DVD Rental`](https://neon.com/postgresql/postgresql-getting-started/postgresql-sample-database) database will be used for this exercise


To load it into your pgAdmin app:

1- First, after connecting to the server, extract the .zip file, you should have a `.tar` file now.

2- Create your database

3- Right-click on the database itself

4- Press `Restore`

5- In 'Filename', Choose the path where your `.tar` file is stored

5- Press `Restore`, you will then find the tables inside Database -> Schemas -> Tables 


# Tasks
## Part 1:
A bookstore wants to migrate their records and information online and hired you as their Database Admin!
1) Create a table called Books with the following attributes:

    **book_id** → auto-increment integer, primary key

    **title** → not null

    **author** → not null

    **description** 

    **price** → decimal number with 2 decimal places, must be non-negative

    **publish_date** → date, not null

    **in_stock** → boolean with default value TRUE

2) Oops! The bookstore made some last minute changes:
- Add a column called **publisher**

- Drop the column **description**

- Rename the column **in_stock** to **available**

## Part 2:
Using the `DVD Rental` Database:
1) Show each customer and their **total** payments

2) Find all actors in film with ID 12 whose last name **ends with the letter “e”**


3) Show films that are **between 90 and 120** minutes long, ordered by length **(shortest first)**

## Submission:
Write your commands separated by new lines into a text file in this format `YourName.txt` and submit it in the usual manner in your committee, good luck!

