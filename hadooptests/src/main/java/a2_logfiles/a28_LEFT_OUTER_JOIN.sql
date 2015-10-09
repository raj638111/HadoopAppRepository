-- Links
http://stackoverflow.com/questions/354070/sql-join-where-clause-vs-on-clause

--Name
create table studentname (
id string,
name string
)
row format delimited
fields terminated by ',';

load data local inpath 
'/home/mountain/workspace/gitrepos/HadoopAppRepository/hadooptests/src/main/java/a2_logfiles/a29_studentname.txt' 
overwrite into table studentname;

-- Mark
create table studentmark(
id string,
mark string
)
row format delimited
fields terminated by ',';

load data local inpath 
'/home/mountain/workspace/gitrepos/HadoopAppRepository/hadooptests/src/main/java/a2_logfiles/a30_studentmark.txt' 
overwrite into table studentmark;

-- address

create table studentaddress(
id string,
address string
)
row format delimited
fields terminated by ',';

load data local inpath 
'/home/mountain/workspace/gitrepos/HadoopAppRepository/hadooptests/src/main/java/a2_logfiles/a31_studentaddress.txt' 
overwrite into table studentaddress;

-----------------------------------------------------------------
Two left join (on + and clause) (Hint : Check stack overflow link)  
--------------------------------------------

select
name.name,
mark.mark,
address.address
from
studentname name
left outer join
studentmark mark
on name.id=mark.id
and name.name!='ThreeName'
left outer join
studentaddress address
on name.id=address.id
and name.name != 'ThreeName';


OneName	11	add1
TwoName	22	add2
ThreeName	NULL	NULL
FourName	NULL	NULL
FiveName	55	add5


-----------------------------------------------------------------
Single left join (on + and clause)  
--------------------------------------------
select
name.name,
mark.mark
from
studentname name
left outer join
studentmark mark
on name.id=mark.id
and name.name != 'ThreeName';

OneName		11
TwoName		22
ThreeName	NULL	# 	Check this out : We still get 'ThreeName' 
						But the fields on right table is set to NULL
FourName	NULL
FiveName	55

-----------------------------------------------------------------
Single left join (on clause) 
--------------------------------------------
select
name.name,
mark.mark
from
studentname name
left outer join
studentmark mark
on name.id=mark.id;

OneName		11
TwoName		22
ThreeName	33 
FourName	NULL
FiveName	55


-----------------------------------------------------------------
Single left join (This is a cross product)
--------------------------------------------
select
name.name,
mark.mark
from
studentname name
left outer join
studentmark mark;






















