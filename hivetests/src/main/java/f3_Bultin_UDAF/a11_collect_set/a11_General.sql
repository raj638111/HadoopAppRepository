CREATE TABLE collectset (
acctId string,  
tstamp string, 
rec string) 
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ',';

LOAD DATA LOCAL INPATH  './a12_input.txt'
OVERWRITE INTO TABLE CollectSet;

select * from collectset;

	acc1	2012-09-20	rec1
	acc1	2012-09-20	rec2
	acc1	2012-09-19	rec3
	acc1	2012-09-19	rec3

select acctid, max(tstamp), collect_set(rec) from collectset group by acctid;       

	acc1	2012-09-20	["rec1","rec2","rec3"]
	
	
--------------------------------------------------------------------	
	
CREATE TABLE resultset (
acctId string,  
tstamp string, 
rec array<string>
); 

insert into resultset
select acctid, max(tstamp), collect_set(rec) from collectset group by acctid;       

select * from resultset;
	acc1	2012-09-20	["rec1","rec2","rec3"]
	
--------------------------------------------------------------------	

CREATE TABLE resultsetstr (
acctId string,  
tstamp string, 
rec array<string>
); 

insert into resultsetstr
select acctid, max(tstamp), collect_set(rec) from collectset group by acctid;       

select * from resultsetstr;

	acc1	2012-09-20	["rec1","rec2","rec3"]

