
------------------------------------------------------
Eclipse Arguments
----------------------
	src/main/resources/input/1997_1998
	src/main/resources/input/master/carriers.csv
	output
------------------------------------------------------
Output Reference in Eclipse
----------------------------
	src/main/resources/results/job_sort_secondarysort
	
------------------------------------------------------
Output Format
----------------------------
	<Arrival Delay Record>|<Previous Arrival Delay Record>	

------------------------------------------------------
Hadoop Command
----------------------
	hadoop jar airlinedatatest-0.0.1-SNAPSHOT.jar job.join.reducerside_with_secondarysort.JoinMRJob  input carriermaster output 
	hadoop jar airlinedatatest-0.0.1-SNAPSHOT.jar job.join.reducerside_with_secondarysort.JoinMRJob  -D map.reduce.tasks=12 input master/carriers.csv output
------------------------------------------------------
Reference
----------------------
	Pro Apache Hadoop : Page : 112
	