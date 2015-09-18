

	Target = Datafile1 - Datafile2
	cp ~/pg/conf/log4j.properties .
	 pig -4 log4j.properties -x local
---------------------------------------------------------------------
	
	SET parquet.compression gzip; 
	 curRecords = LOAD './file1.txt' using PigStorage('|') as (field1, field2, field3);
	 cur = foreach curRecords generate TRIM(field1), TRIM(field2), TRIM(field3);
	 
	 prevRecords = LOAD './file2.txt' using PigStorage('|') as (field1, field2, field3);
	 prev = foreach prevRecords generate TRIM(field1), TRIM(field2), TRIM(field3);
	 
	 cgrp = COGROUP cur BY $0 OUTER, prev BY $0 OUTER;
	 
	 filtered = FILTER cgrp by COUNT(prev) == 0;
	 
	extracted = FOREACH filtered GENERATE FLATTEN($1); 
	STORE extracted INTO './outputset' USING ParquetStorer;

---------------------------------------------------------------------

	myload = LOAD './outputset/part-r-00000.gz.parquet' using ParquetLoader
		
---------------------------------------------------------------------
	 
	 cgrp = COGROUP cur BY $0 INNER, prev BY $0 INNER;
	  sz = FOREACH cgrp GENERATE SIZE($2);
	 cnt = FOREACH cgrp GENERATE group, COUNT(cur);
	 filtered = FILTER cgrp by COUNT(prev) == 0;
	 extracted = FOREACH filtered GENERATE $1.$0;
	store A into '/test-warehouse/tinytable' USING parquet.pig.ParquetStorer;
		STORE extracted INTO './outputset'	USING PigStorage(':');
	STORE extracted INTO '/outputset' USING parquet.pig.ParquetStorer;
	
	parData = LOAD './outputset/part-r-00000.parquet' using ParquetLoader as (field1, field2, field3);
	SET parquet.compression gzip;
	SET parquet.compression snappy;
	
	****	Use stop on Failure;