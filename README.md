This project is a sample for reading and writing data into/from cassandra using alpakka connector.


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
