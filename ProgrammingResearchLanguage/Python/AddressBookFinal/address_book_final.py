from abc import ABCMeta, abstractmethod

#An abstract class that holds all program input validation and key functions
#Coded in: Python 2.7
#Author: Christopher Enos
#Version: 1.0g
class AddressBook(object): 
    
    #Binds module to a Abstract Base Class property
    __metaclass__ =  ABCMeta

    #Constructor function instatiates necessary variables for object creation
    def __init__(self):
        self.output_string = " "

    #Function that queries and validates user for contact info
    #Returns Tuple of strings
    def add_contact(self):
        
        #String variable that queries user for contact's first name
        first_name = raw_input("\nPlease Enter First Name of the Contact: ").lower()
            
        while first_name.isalpha() == False or len(first_name) <=0 or len(first_name) >20:
            print("\nInvalid Input! Names can only contain letters!")
            first_name = raw_input("\nPlease Enter First Name of the Contact: ").lower()
        
        #String variable that queries user for contact's last name
        last_name = raw_input("\nPlease Enter Last Name of the Contact: ").lower()
        
        while last_name.isalpha() == False or len(last_name) <=0 or len(last_name) >20:
            print("\nInvalid Input! Names can only contain letters!")
            last_name = raw_input("\nPlease Enter Last Name of the Contact: ").lower()
        
        #String variable that queries user for contact's phone number        
        phone_number = raw_input("\nEnter the Contacts Phone Number: ")
        
        while len(phone_number) <= 0 and len(phone_number) > 12:
            print("\nInvalid Input! Phone numbers must be written as XXX-XXX-XXXX")
            phone_number = raw_input("\nEnter the Contacts Phone Number: ")

        #String vairable that strips user input of special character '-' and whitespace 
        phone_number = phone_number.replace("-", "")
        phone_number = phone_number.replace(" ", "")

        #String variable that queries user for email
        email = raw_input("\nEnter the Contacts Email Address: ").lower()

        while len(email) <=0 or len(email) > 40:
            print("\nInvalid Input! Please insert an email 1-40 characters in length!") 
            email = raw_input("\nEnter the Contacts Email Address: ").lower()
       
        #Tuple of string holds user string input from function
        contact_data = (first_name, last_name, phone_number, email)
        
        return contact_data

    #Function to validate user input for searching contacts
    #Returns string variable
    def search_contacts(self):
        
        #String variable that queries user for contact's first name
        key_search = raw_input("\nEnter the Name of the Contact You Wish to Search for: ").lower()

        while key_search.isalpha == False or len(key_search) == 0:
            print("\nInvalid input! Name must be all letters!")
            key_search = raw_input("\nEnter the Name of the Contact You Wish to Search for: ").lower()
        
        return key_search

    #Function that formats print function strings
    #@abstractmethod: Ensures method is implemented in children classes
    #Returns string variable
    @abstractmethod
    def to_string(self):
        
        #String variable used for formating method output from file
        self.output_string = "%s, %s"
        return self.output_string       
