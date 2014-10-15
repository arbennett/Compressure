#!/usr/bin/python 
'''
A script to generate some text files for testing Compressure
'''
import numpy.random as rand

# How many different symbols we are going to allow and the amount we'll add to each run
max_symbols = 51
symbol_interval = 5

# How many characters the random message will be and the amount we'll add each run
max_length = 100001
length_interval = 50

# Initialize the length and number of possible symbols 
current_symbols =50
current_length = 100000


while current_symbols < max_symbols:
    while current_length < max_length:
        random_string=""  
        f = open('test_'+str(current_length)+'.txt', 'w')
        for i in range(current_length):
            random_string+=( chr( rand.randint( current_symbols )+48 ) )
        f.write(random_string)
        f.close()
        current_length+=length_interval
    current_length = 5      
    current_symbols+=symbol_interval

