import sys
import mysql.connector
from address_book_final import AddressBook
from __builtin__ import super

#Class that contains all the functions necessary for MySQL CRUD manipulation 
#Coded in: Python v2.7
#Author: Christopher Enos
#Version: 1.0b
class DatabaseBook(AddressBook):
    
    #Constructor to intialize super class and child class variables
    def __init__(self):
        super(DatabaseBook, self).__init__()

        #Variable used to hold a strings required to connect to database
        self.connection = mysql.connector.connect(user = 'root', password = 'root', db='contact_book', host = 'localhost')
        
        #Variable of type cursor required to execute MySQL queries in python
        self.db_cursor = self.connection.cursor()

    #Function that queries user for contact info and saves it to a MySQL database
    #Overides super AddressBook function add_contact
    def add_contact(self):
        
        #Tuple variable that holds return value for add_contact of abstract super class AddressBook
        contact_data = super(DatabaseBook, self).add_contact()
        
        #String variable that holds MySQL query for inserting a row into db
        sql_add_contact = ("INSERT INTO contact_book.contacts(first_name, last_name, phone_number, email) VALUES(%s, %s, %s, %s)")
        
        self.db_cursor.execute(sql_add_contact, contact_data)
        self.connection.commit()

        print("\nContact Added Successfully")
       
    #Function to search MySQL database for user specified contact
    #Overides super AddressBook function search_contacts
    def search_contacts(self):
    
        #String variable holds return value from overidden to_string method
        string = self.to_string()
        
        #String variable that holds return value for search_contacts of abstract super class AddressBook
        key = super(DatabaseBook, self).search_contacts()
        
        #String variable for MySQL query to display a row from db if arguments are satisfied
        sql_search_contacts = ("SELECT * FROM contact_book.contacts WHERE first_name = '%s'") % key
        
        self.db_cursor.execute(sql_search_contacts)
        
        #List variable holds what db_cursor recieved upon execution
        data = self.db_cursor.fetchall()

        if self.db_cursor.rowcount != None:
            for column in data:
                print(string%(column[1],column[2], column[3], column[4]))
        else:
            print("\nNo Contacts Found")
       
    #Function that deletes user specified contact from MySQL database
    def delete_contact(self):
        
        #String variable that holds return value for search_contacts of abstract super class AddressBook
        key = super(DatabaseBook, self).search_contacts()
        
        #String variable that holds MySQL query for deleting a row from db if arguments are satisfied
        sql_delete_contact = ("DELETE FROM contact_book.contacts WHERE first_name = '%s'") % key
        
        self.db_cursor.execute(sql_delete_contact)
        
        if self.db_cursor.rowcount != None:
            self.connection.commit()
            print("Successfully Deleted " + key)
        else:
            print("\nContact " + key + " was not found")     

    #Function that displays all contacts in MySQL database
    def display_contacts(self):
        
        #String variable holds return value from overidden to_string method
        string = self.to_string()

        #String variable that holds MySQL query to list all rows in db
        sql_display_contacts = ("SELECT * FROM contact_book.contacts")
        
        self.db_cursor.execute(sql_display_contacts)
        
        #List variable holds what db_cursor recieved upon execution
        data = self.db_cursor.fetchall()

        if self.db_cursor.rowcount != None:
            for column in data:
                print(string%(column[1],column[2], column[3], column[4]))
        else:
            print("\nNo Contacts Found")
    
    #Function that ensures proper database handling on exit
    def close_down(self):
        self.db_cursor.close()
        self.connection.close()
        sys.exit(0)

    #Function that formats print function strings
    #Returns string variable
    def to_string(self):
        
        #String variable used for formating method output from db
        self.output_string = "\n|Contact Name: %s %s | Phone Number : %s | Email: %s |"
        return self.output_string
