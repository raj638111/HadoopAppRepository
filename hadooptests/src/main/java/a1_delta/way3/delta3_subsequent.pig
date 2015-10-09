
REGISTER tg/hadooptests-0.0.1-SNAPSHOT.jar;

curDenormalized = LOAD './input/cur.txt' USING PigStorage('|') 
AS (field1:chararray, field2:chararray, field3:chararray);
 

prevDenormalized = LOAD './input/prev.txt' USING PigStorage('|') 
AS (field1:chararray, field2:chararray, field3:chararray);


prev = FOREACH prevDenormalized GENERATE *, delta.way2.pig.CheckSum($2) as checksum;	
current = FOREACH curDenormalized GENERATE *, delta.way2.pig.CheckSum($2) as checksum;	


curPrev = JOIN current BY ($0, $1) FULL OUTER, prev BY ($0, $1);

SPLIT curPrev INTO
newRecord IF current::checksum IS NOT NULL AND prev::checksum is NULL,
compareRecord IF ( current::checksum IS NOT NULL and prev::checksum IS NOT NULL AND  prev::checksum != current::checksum),
matchedRecord IF ( current::checksum IS NOT NULL and prev::checksum IS NOT NULL AND  prev::checksum == current::checksum);


new = FOREACH newRecord GENERATE current::field1, current::field2, current::field3;
modified = FOREACH compareRecord GENERATE current::field1, current::field2, current::field3; 
matched = FOREACH matchedRecord GENERATE current::field1, current::field2, current::field3;

delta = union new, modified;
byDep = COGROUP delta BY $1, matched BY $1;
byDepFiltered = filter byDep by SIZE(($1)) > 0;

flatLeft = FOREACH byDepFiltered GENERATE FLATTEN($1);
flatRight = FOREACH byDepFiltered GENERATE FLATTEN($2);

flat = union flatLeft, flatRight;
 store flat into './deltatable/' using PigStorage('|');
