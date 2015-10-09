
-- Used to concat fields with a delimiter

create table concatws (
field1 string,
field2 string,
field3 string
)
row format delimited
fields terminated by ',';

	load data local inpath 
	'/home/mountain/workspace/gitrepos/HadoopAppRepository/hadooptests/src/main/java/a2_logfiles/a25_concat.txt' 
	overwrite into table concatws;

-----------------------------------------------
select 
field1,
concat_ws('|', field2, field3)
from 
concatws;

a1	a2|a3
b1	b2|b3

-----------------------------------------------

select 
field1,
concat_ws('\005', field2, field3)
from 
concatws;

a1	a2a3
b1	b2b3


