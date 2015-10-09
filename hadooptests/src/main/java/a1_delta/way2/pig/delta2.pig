
SET parquet.compression gzip; 

REGISTER tg/pigscripts-0.0.1-SNAPSHOT.jar;
prevDenormalized = LOAD './prev.txt' USING PigStorage('|') AS (field1:chararray, field2:chararray, field3:chararray);
currentDenormalized = LOAD './cur.txt' USING PigStorage('|') AS (field1:chararray, field2:chararray, field3:chararray);

prev = FOREACH prevDenormalized GENERATE *, com.delta.way2.CheckSum($2) as checksum;	
current = FOREACH currentDenormalized GENERATE *, com.delta.way2.CheckSum($2) as checksum;	


curPrev = JOIN current BY ($0, $1) FULL OUTER, prev BY ($0, $1);

SPLIT curPrev INTO
newRecord IF current::checksum IS NOT NULL AND prev::checksum is NULL,
noMatch IF current::checksum IS NULL AND prev::checksum IS NOT NULL,
compareRecord IF ( current::checksum IS NOT NULL and prev::checksum IS NOT NULL AND  prev::checksum != current::checksum);  


new = FOREACH newRecord GENERATE current::field1, current::field2, current::field3;

STORE new INTO './outputset' USING ParquetStorer;

mydata = LOAD './outputset/part-r-00000.gz.parquet' using ParquetLoader;
	
			