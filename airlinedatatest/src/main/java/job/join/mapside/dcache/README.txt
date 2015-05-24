
------------------------------------------------------
Eclipse Arguments
----------------------
	src/main/resources/input/1997_1998
	output
	src/main/resources/input/master/airports.csv
	src/main/resources/input/master/carriers.csv
	
	
------------------------------------------------------
Output Reference in Eclipse
----------------------------
	src/main/resources/results/job_join_mapside_dcache
	
------------------------------------------------------
Output Format
----------------------------
	

------------------------------------------------------
Hadoop Command
----------------------
	hadoop jar airlinedatatest-0.0.1-SNAPSHOT.jar job.join.mapside.dcache.MapSideJoinMRJob  input output master/airports.csv master/carriers.csv output 
	
------------------------------------------------------
Reference
----------------------
	Pro Apache Hadoop : Page : 112
	