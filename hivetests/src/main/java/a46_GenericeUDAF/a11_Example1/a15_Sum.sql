
-- Create table Subject as described in 'Complext_Datatypes'

create table marks ( 
   name     string,
   class    string,
   maths    array<string>, --Trigonometry, Algebra
   science  struct<btny:string, zlgy:string> 
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ',';

insert overwrite table marks 
select 
   name, 
   class, 
   array(trigonometry, algebra), 
   named_struct('btny', botany, 'zlgy', zoology)
from subjects;

hive> select * from marks;
OK
name1 XI ["85","80"] {"btny":"70","zlgy":"75"}
name2 XI ["86","81"] {"btny":"71","zlgy":"76"}