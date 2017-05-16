import unittest
import cPickle
import os.path as path
import mysql.connector

#Py unit testing for Final Project Assignment  
#Created in: Python 2.7
#Author: Christopher Enos
#Version: 1.0a (final)
class Test_test5(unittest.TestCase):
    
    #Place holder varaible 
    a_string = None

    #Unit test that tests database connectivity (Modified version of assignment four's)
    def test_connection(self):
         
         #Variable used to hold a strings required to connect to database
         connection = mysql.connector.connect(user = 'root', password = 'root', db='contact_book', host = 'localhost')
         
         #Variable of type cursor required to execute MySQL queries in python
         db_cursor = connection.cursor()        
         
         db_cursor.execute("SELECT VERSION()")
         
         #Variable that holds content of db_cursor
         results = db_cursor.fetchone()
         
         # Check if anything at all is returned
         return self.assertIsNotNone(results)

    #Unit test that tests functionality of File I/0
    def test_read_write(self):        
        
        #String varaible that will be serialized to a file
        a_string = "This is a test"

        #File variable holds parameters necessary to open a file
        test_file = open('some_file.pickle', 'wb')

        cPickle.dump(a_string, test_file)
        test_file.close()
        test_file = open('some_file.pickle', 'rb')
        
        #String variable that loads unserialized contents of file
        new_string = cPickle.load(test_file)
        
        #Assert intial string variable is equal to loaded string variable
        if a_string in new_string:
            self.assertEqual(a_string, new_string)
            print("Passed")
            test_file.close()
        else:
            self.assertNotEqual(a_string, new_string)
            print("Failed")
            test_file.close()

    #Unit test that tests to see if the file exists and is readable (Modified version of Assignment three's)    
    def test_file(self):
        self.assertTrue(path.isfile(".\some_file.pickle"))


#Auto-generated peice of code for test function and class recognition for interpreter     
if __name__ == '__main__':
    unittest.main()
