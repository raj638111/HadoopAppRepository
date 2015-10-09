
	-	Is a continuation of the Pig script available in the same package
	-	Hive runs  with Hadoop(running in Local/Standalone mode)
	
	-	Create external table(to able to read Parquet + gzip)
	-	Access the external table


CREATE EXTERNAL TABLE test (
 field1 string,
 field2 string,
 field3 string) 
STORED AS PARQUET
LOCATION '/home/mountain/workspace/gitrepos/HadoopAppRepository/hivescripts/outputset';

CREATE TABLE   records_internal (field1 STRING, field2 STRING, field3 STRING)
STORED AS PARQUET
	
	CREATE EXTERNAL TABLE   records_external (field1 STRING, field2 STRING, field3 STRING)
                                ROW FORMAT DELIMITED
                                FIELDS TERMINATED BY '\t'
                                LOCATION './outputset';
        
	