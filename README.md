Introduction:
-------------

This project is a sample for reading and writing data into/from cassandra using alpakka connector.


Description:
------------------------------------

To understand the Alpakka Cassandra connector we have created a sample project having different modules to separate out the data generation and processing part of the pipeline.

The sample project is divided into two major parts:

GeneratorApp
ProcessorApp
The Generator app is aimed at populating data into Cassandra table using Alpakka and the Processor app is developed to consume data from Cassandra table.


CREATE CASSANDRA KEYSPACE AND TABLES:
------------------------------------

cqlsh> create KEYSPACE alpakka_sample with replication = {'class':'SimpleStrategy','replication_factor':1}

cqlsh> create table alpakka_sample.student(id bigint PRIMARY KEY, name text);



RUN DATA GENERATOR NODE:
-----------------------

sbt "project generator" run


RUN DATA PROCESSOR NODE:
------------------------

sbt "project processor" run
