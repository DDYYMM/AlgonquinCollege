from database_final import DatabaseBook 
from log_file_final import LogFile

#Class that handles the flow and execution of program based on user input
#Coded in: Python v2.7
#Author: Christopher Enos
#Version: 1.0g
class Executor(object):
    
    #Instance variable referencing to object of class DatabaseBook
    database_book = DatabaseBook()
    
    #Instance variable referencing to object of class LogFile
    log_file = LogFile()
    log_file.load()

    #Function that displays the main menu for script
    def main_menu():
        print("""
    Welcome To Ultra Address Book 5000 Super Series 12!
            Please Choose From The Following:

        1. Add a Contact
        2. Search For a Contact
        3. Delete a Contact
        4. Display List of Contacts
        5. Display Log File

                   To Exit Type 'EOF'

            """)


    main_menu() 
    
    #Variable gets input from user
    answer = raw_input("\nSelect an option from the above(1-5): ")
  
    #Looping and desicion structure of program based on the users input for
    #variable answer
    while True:
    
        #If answer is equal to '1' script will add contact
        if answer == "1":
            
            database_book.add_contact()
            log_file.add_log(" Added Contacts")
            main_menu()
            answer = raw_input("\nSelect an option from the above(1-5): ")
        
        #If answer is equal to '2' execute search_contacts function
        elif answer == "2":
            
            database_book.search_contacts()
            log_file.add_log(" Searched Contacts")
            main_menu()
            answer = raw_input("\nSelect an option from the above(1-5): ")
 
        #If answer is equal to '3' execute delete_contact function
        elif answer == "3":
            
            database_book.delete_contact()
            log_file.add_log(" Deleted Contact")
            main_menu()
            answer = raw_input("\nSelect an option from the above(1-5): ")

        #If answer is equal to '4' execute display_contacts function
        elif answer == "4":
      
            database_book.display_contacts()
            log_file.add_log(" Displayed Contacts")
            main_menu()
            answer = raw_input("\nSelect an option from the above(1-5): ")
        
        #If answer is equal to '5' execute display_log method
        elif answer == "5":

            log_file.display_log()
            log_file.add_log(" Displayed Log")
            main_menu()
            answer = raw_input("\nSelect an option from the above(1-5): ")
        
        #If answer is equal to the exit flag then exit the program
        elif answer == "EOF":

            database_book.close_down()
        
        #If answer is an invalid identifier error message is displayed
        #and user is brought back to main menu and query again        
        elif answer != "1" or "2" or "3" or "4" or "5":
            
            print("Invalid Option Selected! Please select a valid option(1-5)!")
            main_menu()
            answer = raw_input("\nSelect an option from the above(1-5): ")
