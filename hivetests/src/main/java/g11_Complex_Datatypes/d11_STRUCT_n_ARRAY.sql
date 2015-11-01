
create table science (
   name     string,
   class    string,
   marks    struct<bny:string, zlgy:string>
);

insert overwrite table science
select name, class, named_struct('bny', botany, 'zlgy', zoology)
from subjects;

-----------------------------------
CREATE TABLE  subjects (
   name           string,
   class          string,
   History        string,
   Geography      string,
   botany         string,
   zoology        string,
   algebra        string,
   trigonometry   string,
   tamil          string,
   english        string 
)                      
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ',';

load data local inpath './g11_subjects.txt'
overwrite into table subjects;


CREATE TABLE  maths (
   name        string,
   class       string,
   maths       array<string> --Algebra,  Trigonometry
)                      
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ',';

insert overwrite table maths
select name, class, array(algebra, trigonometry) from subjects;


