

REGISTER tg/hadooptests-0.0.1-SNAPSHOT.jar;

curDenormalized = LOAD './input' USING PigStorage('|') 
AS (field1:chararray, field2:chararray, field3:chararray);
 

prevDenormalized = LOAD './stgtable' USING PigStorage('|') 
AS (field1:chararray, field2:chararray, field3:chararray);


prev = FOREACH prevDenormalized GENERATE *, delta.way2.pig.CheckSum($2) as checksum;	
current = FOREACH curDenormalized GENERATE *, delta.way2.pig.CheckSum($2) as checksum;	


curPrev = JOIN current BY ($0, $1) FULL OUTER, prev BY ($0, $1);

SPLIT curPrev INTO
newRecord IF current::checksum IS NOT NULL AND prev::checksum is NULL,
noMatch IF current::checksum IS NULL AND prev::checksum IS NOT NULL,
compareRecord IF ( current::checksum IS NOT NULL and prev::checksum IS NOT NULL AND  prev::checksum != current::checksum),
matchedRecord IF ( current::checksum IS NOT NULL and prev::checksum IS NOT NULL AND  prev::checksum == current::checksum);


new = FOREACH newRecord GENERATE current::field1, current::field2, current::field3;
inactive = FOREACH noMatch GENERATE prev::field1, prev::field2, prev::field3;
modified = FOREACH compareRecord GENERATE current::field1, current::field2, current::field3; 
matched = FOREACH matchedRecord GENERATE current::field1, current::field2, current::field3;
result = union new, modified, matched;
store result into './output' using PigStorage('|');
--store result into './output' using org.apache.pig.piggybank.storage.MultiStorage('./output', '1');

newDs = FOREACH new generate field2;
inactiveDs = foreach inactive generate field2;
modifiedDs = foreach modified generate field2;

deltaDeps = union newDs, modifiedDs, inactiveDs;
depGroup = group deltaDeps by field2; 
	deltaDepsUnique = foreach depGroup generate group;
	store deltaDepsUnique into './output/deltadeps' using PigStorage('|');

--allDeps = union newDs, modifiedDs, inactiveDs, matchedDs;
--depGroup = group allDeps by field2;
--	allDepUnqiue = foreach depGroup generate group;
--	store allDepsUnique into './output/alldeps'

--resultGroup = group result by field2;






