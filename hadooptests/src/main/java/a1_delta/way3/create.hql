
-- Staging Table

create external table stgtable (field1 string,field2 string,field3 string)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '|'
LOCATION '/home/mountain/workspace/gitrepos/HadoopAppRepository/hadooptests/src/main/java/delta/way3/stgtable';


------------------------------ Main Table (No longer needed)

--create external table maintable (field1 string,field2 string,field3 string)
--ROW FORMAT DELIMITED
--FIELDS TERMINATED BY '|'
--LOCATION '/home/mountain/workspace/gitrepos/HadoopAppRepository/hadooptests/src/main/java/delta/way3/maintable';

--LOAD DATA INPATH '/home/mountain/workspace/gitrepos/HadoopAppRepository/hadooptests/src/main/java/delta/way3/output'
--INTO TABLE maintable;

------ Delta Table (Which only contain delta partition data)
create external table deltatable (field1 string,field2 string,field3 string)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '|'
LOCATION '/home/mountain/workspace/gitrepos/HadoopAppRepository/hadooptests/src/main/java/delta/way3/deltatable';


-- Main Table (Partitioned)

create external table ptntable (field1 string, field3 string)
PARTITIONED BY (field2 string)
STORED AS PARQUET
LOCATION '/home/mountain/workspace/gitrepos/HadoopAppRepository/hadooptests/src/main/java/delta/way3/ptntable';

set parquet.compression;
set hive.exec.dynamic.partition;
set hive.exec.dynamic.partition.mode;

set parquet.compression=SNAPPY;
set hive.exec.dynamic.partition=true;
set hive.exec.dynamic.partition.mode=nonstrict;

insert overwrite table ptntable partition(field2)
select 
field1,
field3,
field2
from deltatable;



insert overwrite table ptntable partition(field2)
select 
field1,
field3,
field2
from stgtable where field2='d2';

insert overwrite table ptntable partition(field2)
select 
field1,
field3,
field2
from stgtable where field2='d1';


insert overwrite table ptntable partition(field2)
select 
field1,
field3,
field2
from stgtable

ROW FORMAT DELIMITED
FIELDS TERMINATED BY '|'
LOCATION '/home/mountain/workspace/gitrepos/HadoopAppRepository/hadooptests/src/main/java/delta/way3/ptntable'

LOAD DATA INPATH '/home/mountain/workspace/gitrepos/HadoopAppRepository/hadooptests/src/main/java/delta/way3/output'
INTO TABLE maintable;

