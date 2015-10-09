
-- Used to combine multiple select statements

Syntax
-----------
select_statement UNION [ALL | DISTINCT] select_statement UNION [ALL | DISTINCT] select_statement ...
https://cwiki.apache.org/confluence/display/Hive/LanguageManual+Union

create table pastAds (
pagename string,
adidList Array<int>
)
row format delimited
fields terminated by ','
collection items terminated by '$';

load data local inpath 
'/home/mountain/workspace/gitrepos/HadoopAppRepository/hadooptests/src/main/java/a2_logfiles/a16_union_all.txt' 
overwrite into table pastAds;
--------------------------------------------
select * 
from 
(
select * from pageAds
union all
select * from pastAds
)unionResult;

front_page	[1,2]
back_page	[5,6]
front_page	[22,33]
back_page	[77,88]
--------------------------------------------

select * 
from 
(
select * from pageAds
where pagename='front_page'
union all
select * from pastAds
)unionResult;

front_page	[1,2]
front_page	[22,33]
back_page	[77,88]

--------------------------------------------


select * 
from 
(
select pagename from pageAds
union all
select adidList from pastAds
)unionResult;

FAILED: SemanticException 6:21 Schema of both sides of union should match: Column pagename is of type string on first table and type array<int> on second table. Error encountered near token 'pastAds'





