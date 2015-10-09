
-- When  the value is 'null' replace with the given value
-- http://www.thelandbeyondspreadsheets.com/what-is-the-hive-sql-coalesce-function-what-does-it-do-and-why-on-earth-is-it-useful/
create table coal (
field1 string,
field2 string,
field3 string
)
row format delimited
fields terminated by ',';

load data local inpath 
'/home/mountain/workspace/gitrepos/HadoopAppRepository/hadooptests/src/main/java/a2_logfiles/a21_coalesce.txt' 
overwrite into table coal;

---------------------------------------------------------
select field1, field2, field3 from coal;

a1	a2		a3
	NULL	NULL
b1			NULL
	c2		NULL


---------------------------------------------------------

select field1, field2, coalesce(field3, 'nothing') from coal;

a1	a2		a3
	NULL	nothing
b1			nothing
	c2		nothing

---------------------------------------------------------

select field1, field2, field3 from coal where coalesce(field3, 'nothing') != 'nothing';

a1	a2	a3



