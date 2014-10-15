#!/usr/bin/python 
'''
A script for plotting and other data analysis for the Compressure project
'''
import csv
import matplotlib.pyplot as plt



random_reader = csv.DictReader(open("50_symbols_random.csv"), delimiter=',')
hiawatha_reader = csv.DictReader(open("Hiawatha.csv"), delimiter=',')

random_data_x, random_data_y = [], []
hiawatha_data_x, hiawatha_data_y = [], []

for line in random_reader:
    random_data_x.append(float(line["In"]))                  
    random_data_y.append(float(line["Out"])/float(line["In"])) 

for line in hiawatha_reader:
    hiawatha_data_x.append(float(line["In"]))
    hiawatha_data_y.append(float(line["Out"])/float(line["In"]))
    
plt.plot(hiawatha_data_x,hiawatha_data_y, 'r')
plt.plot(random_data_x,random_data_y, 'b')
plt.draw()
plt.show()