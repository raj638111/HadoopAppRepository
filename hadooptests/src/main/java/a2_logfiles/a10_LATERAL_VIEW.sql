
--A field with an array can be exploded into multiple records using Lateral view

https://cwiki.apache.org/confluence/display/Hive/LanguageManual+LateralView
--Syntax
lateralView: LATERAL VIEW udtf(expression) tableAlias AS columnAlias (',' columnAlias)*
fromClause: FROM baseTable (lateralView)*

-- This is a Hive Query(hql)
create table pageAds (
pagename string,
adidList Array<int>
)
row format delimited
fields terminated by ','
collection items terminated by '$';



load data local inpath 
'/home/mountain/workspace/gitrepos/HadoopAppRepository/hadooptests/src/main/java/a2_logfiles/later_view.txt' 
overwrite into table pageads;
------------------------------------------------------------------
select pagename, adid
from pageAds LATERAL VIEW EXPLODE(adidList) adTable as adid;

front_page	1
front_page	2
back_page	5
back_page	6
------------------------------------------------------------------

select pagename, adid
from pageAds LATERAL VIEW EXPLODE(adidList) adTable as adid
where pagename='front_page';

front_page	1
front_page	2
