#!/usr/bin/python

## Copyright (c) 2014:
## Istituto Nazionale di Fisica Nucleare (INFN), Italy

## This program is free software; you can redistribute it and/or modify
## it under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 2 of the License, or
## (at your option) any later version.

## Licensed under the Apache License, Version 2.0 (the "License");
## you may not use this file except in compliance with the License.
## You may obtain a copy of the License at
## http://www.apache.org/licenses/LICENSE-2.0
 
## Unless required by applicable law or agreed to in writing, software
## distributed under the License is distributed on an "AS IS" BASIS,
## WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
## See the License for the specific language governing permissions and
## limitations under the License.

## Author: 	Giuseppe LA ROCCA, INFN-CATANIA (giuseppe.larocca@ct.infn.it)
## Requirement:	Python-2.4.3, SQlite-3.3.6
## Version:	1.0.0
## Date:	11/04/2014

import datetime, optparse
import logging, logging.handlers
import sqlite as lite
import csv

codeCodes = {
        'yellow':	'0;33',
        'green':        '0;32',
        'red':          '0;31',
        'normal':       '0'
}

def save_CSV(filename, data):
	    writer = open(filename, "a")
	    a = csv.writer(writer, delimiter=';')
            a.writerows(data)
	    writer.close

parser = optparse.OptionParser()

parser.add_option(
        '--csv', '--csv-file', dest="csv",
        help="The CSV filename",         
        metavar="csv")

parser.add_option(
        '--out', '--out-file', dest="out",
        help="The output CSV filename",
        metavar="out")

parser.add_option(
        '--avg', '--avg-option', dest="avg",
        metavar="avg")

parser.add_option(
        '--db', '--dbname', dest="database",
        help="The sqlite database",                                               
        metavar="database")

parser.add_option(
        '--std', '--stdout-file', dest="std",
        help="The standart output where redirect the cron",                                               
        metavar="filename")

options, args = parser.parse_args()

if ((options.database) == None and 
    (options.std) == None and 
    (options.out) == None and 
    (options.csv) == None and
    (options.avg) == None) :

   # stop the program and print an error message
   print """
Usage: trodan_CSVdataParsing.py [options]

options:
  -h, --help            show this help message and exit

  --db=database, --dbname=database
       	                The sqlite database ho host the SURLs

  --csv=filename, --csv-file=filename
                        The CSV filename to be processed

  --out=filename, --out-file=filename
                        The output CSV filename

  --avg=option, --avg-option=option
                        Possible options: 'diurnal', 'daily', 'monthly'

  --std=filename, --stdout-file=filename
       	                The standart output where redirect the cron"""

else:
	now = datetime.datetime.now()
        today = now.ctime()

	LOG_FILENAME = options.std

	# Set up a specific logger with our desired output level
	logger = logging.getLogger('logger')
	logger.setLevel(logging.DEBUG)

	# Add the log message handler to the logger
	handler = logging.handlers.RotatingFileHandler(LOG_FILENAME, backupCount=50)
	logger.addHandler(handler)

	logger.debug ("\nStart processing at %s " % today)
	logger.debug ("Parsing the TRODAN CSV file [ %s ] " % options.csv)
	logger.debug ("\n[ Selected Options ]\n- Database = %s\n- Log =  %s\n- CSV = %s" % (options.database, options.std, options.csv))

        try:
		# Checks to see if table exists and makes a fresh table.
                con = lite.connect(options.database)
                cur = con.cursor()
                cur.execute("DROP TABLE IF EXISTS trodan_data;")
                cur.execute("""
                CREATE TABLE IF NOT EXISTS trodan_data(COL1 varchar[16], COL2 integer, COL3 double, COL4 double, COL5 double, COL6 double, COL7 double, COL8 double, COL9 double, COL10 double, COL11 integer, COL12 double, COL13 double);
                """)

                cur.execute("DROP TABLE IF EXISTS trodan_tmp;")
		cur.execute("""
                CREATE TABLE IF NOT EXISTS trodan_tmp(DATE varchar[16], TIME varchar[5], YEAR varchar[4], 
						      MONTH varchar[2], DAY varchar[2]);
                """)
		
		# Reading CSV file
		csvfile=open(options.csv,"rb")
		reader=csv.reader(csvfile, delimiter=';')
	
		logger.debug ("\nReading CSV file in progress ...")
		for row in reader:

			_row=row[0]
	                _row[:2]        # Month
        	        _row[3:5]       # Day
                	_row[:10]       # Date
	                _row[11:]       # Time
        	        _row[6:10]      # Year

			logger.debug ("%s %s %s %s %s %s %s %s %s %s %s %s %s" 
			% (row[0], row[1], row[2], row[3], row[4], row[5], row[6], 
                           row[7], row[8], row[9], row[10], row[11], row[12]))

			# Appends data from CSV file representing and handling of text
			cur.execute("""
			INSERT INTO trodan_data (COL1, COL2, COL3, COL4, COL5, COL6, 
                                                 COL7,COL8, COL9, COL10, COL11, COL12, COL13) 
			VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s');
			""" % (row[0],row[1],row[2],row[3],row[4],row[5],row[6],
			       row[7],row[8],row[9],row[10],row[11],row[12]))

			#logger.debug ("%s %s %s %s %s"          
                        #% (_row[:10], _row[11:], _row[6:10], _row[:2], _row[3:5]))

			cur.execute("""
                        INSERT INTO trodan_tmp (DATE, TIME, YEAR, DAY, MONTH) 
                        VALUES('%s', '%s', '%s','%s', '%s');
                        """ % (_row[:10], _row[11:], _row[6:10], _row[:2], _row[3:5]))
            
        	# Committing entries on the sqlite database     
		con.commit()

        except lite.Error, e:
                logger.error ("Error %s:" % e.args[0])
                sys.exit(1)

	logger.debug ("\nCSV file uploaded"+"\033["+codeCodes['green']+"m"+" [ OK ] "+"\033[0m")
	logger.debug ("\n[ START PROCESSING ] This operation may takes time. Please wait!")

	# Retrieving entries from the database
        cur.execute("SELECT DISTINCT COL1 FROM trodan_data")
	rows_affected=cur.rowcount

	if (options.avg == 'daily') :
		# AVG_TYPE => 'DAILY'
		logger.debug ("\nAverage Type ==> [ DAILY ]")
		#cur.execute("SELECT DISTINCT DATE FROM trodan_tmp")
		cur.execute("SELECT DISTINCT YEAR, MONTH, DAY FROM trodan_tmp")

		for row in cur.fetchall():
			_year = row[0]
			_month = row[1]
			_day = row[2]
			_date = _day + "/" + _month + "/" + _year
			term = '%' + _date + '%'

		  	cur.execute("""
			SELECT COL1, avg(COL2), avg(COL3), avg(COL4), avg(COL5), avg(COL6), avg(COL7), 
       		       	       avg(COL8), avg(COL9), avg(COL10), avg(COL11), avg(COL12), avg(COL13)
			FROM trodan_data WHERE COL1 LIKE '%s' 
			""" % term)

			for row in cur.fetchall():

				data = [
					[row[0], row[1], row[2], row[3], 
					 row[4], row[5], row[6], row[7], 
					 row[8], row[9], row[10], row[11], 
					 row[12]]
				]

				logger.debug(data)

				# Writing data to the CSV file
				save_CSV(options.out, data)

	if (options.avg == 'monthly') :
		# AVG_TYPE => 'MONTHLY'
                logger.debug ("\nAverage Type ==> [ MONTHLY ]")
                cur.execute("SELECT DISTINCT YEAR FROM trodan_tmp")

		for row in cur.fetchall():
			year = row[0]

                        cur.execute("SELECT DISTINCT MONTH FROM trodan_tmp")
		
			for row in cur.fetchall():
				month = row[0]
                                _where = '%' + month + '/' + year + '%'
	
				cur.execute("""
				SELECT COL1, avg(COL2), avg(COL3), avg(COL4), avg(COL5), avg(COL6), avg(COL7),
                                       avg(COL8), avg(COL9), avg(COL10), avg(COL11), avg(COL12), avg(COL13)
                                FROM trodan_data WHERE COL1 LIKE '%s'
                                """ % (_where))

				for row in cur.fetchall():

					if (row[0] != None and row[1] != None and row[3] != None and row[4] != None and
                                            row[5] != None and row[6] != None and row[7] != None and row[8] != None and
                                            row[9] != None and row[10] != None and row[11] != None and row[12] != None):

						data = [
        	                                	[row[0], row[1], row[2], row[3],
                	                                 row[4], row[5], row[6], row[7],
                        	                         row[8], row[9], row[10], row[11],
                                	                 row[12]]
                                        	        ]

	                                        logger.debug(data)

        	                                # Writing data to the CSV file
                	                        save_CSV(options.out, data)

	if (options.avg == 'diurnal') :
        	# AVG_TYPE => 'DIURNAL'
                logger.debug ("\nAverage Type ==> [ DIURNAL ]")
		cur.execute("SELECT DISTINCT YEAR, MONTH, DAY FROM trodan_tmp")

		for row in cur.fetchall():
		 	_year = row[0]
                        _month = row[1]
                        _day = row[2]
                        _date = _day + "/" + _month + "/" + _year
                        term = '%' + _date + '%'

			_where = '%' + _date + '%'

                        cur.execute("""
                        SELECT COL1, sum(COL2)/24, sum(COL3)/24, sum(COL4)/24, sum(COL5)/24, sum(COL6)/24, 
                                     sum(COL7)/24, sum(COL8)/24, sum(COL9)/24, sum(COL10)/24, sum(COL11)/24, 
                                     sum(COL12)/24, sum(COL13)/24
                        FROM trodan_data WHERE COL1 LIKE '%s'
                        """ % (_where))

			for row in cur.fetchall():
				
				if (row[0] != None and row[1] != None and row[3] != None and row[4] != None and
                                            row[5] != None and row[6] != None and row[7] != None and row[8] != None and
                                            row[9] != None and row[10] != None and row[11] != None and row[12] != None):

					 data = [
						[row[0], row[1], row[2], row[3],
                                                 row[4], row[5], row[6], row[7],
                                                 row[8], row[9], row[10], row[11],
                                                 row[12]]
                                         ]

                                         logger.debug(data)

                                         # Writing data to the CSV file
                                         save_CSV(options.out, data)

	con.close()
