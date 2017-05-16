from address_book_final import AddressBook 
from datetime import datetime
import cPickle

#Child class of parent AddressBook that logs information 
#about user interactions with program during runtime
#Coded in: Python 2.7
#Author: Christopher Enos
#Verison: 1.0a
class LogFile(AddressBook):

    #Constructor to intialize super class and child class variables
    def __init__(self):
        super(LogFile, self).__init__()


    #Save function uses cPickle to serialize input from a parameter specifiec file
    def save(self):
        
        #File variable holds parameters necessary to open a file
        file = open('log_file.pickle', 'wb')

        cPickle.dump(file_items, file)
        file.close()

    #Load function uses cPickle to unserialize and display contents in a parameter specified file
    def load(self):
        global file_items
        try:
           
           #File variable holds parameters necessary to open a file
           file = open('log_file.pickle', 'rb')
           
           #File variable holds parameters to load a file
           file_items = cPickle.load(file)
        
        except IOError or EOFError:
            
            #Dictionary variable is container for information loaded from file
            file_items = {}
    
    #Function that stores dictionary of string into file via serialization
    def add_log(self, string):
        
        #String variable to act as parameter for action being performed to be logged during call
        action = string

        #Reference to datetime object variable containg string value for time it's executed
        timestamp = datetime.now()

        #String variable that holds formated version of datetime.now()
        log_time = timestamp.strftime('%Y/%m/%d %H:%M:%S')

        #Dictionary value that logs the time stamp as it's key and the string action as it's value
        file_items[log_time] = [action]

        self.save()

    #Function that displays all logs in file
    def display_log(self):
        
        #String variable holds return value from overidden to_string method
        string = self.to_string()
        
        if  file_items != {}:
            for key, value in file_items.items():
                print(string%(key, value[0]))
        else:
            print("\nNo Logs Found")
    
    #Function that formats print function strings
    #Returns string variable
    def to_string(self):
        
        #String variable used for formating method output from file
        self.output_string = ("\n| Time Stamp: %s  | Action Performed: %s |")
        return self.output_string
