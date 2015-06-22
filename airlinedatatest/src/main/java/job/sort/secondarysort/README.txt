
------------------------------------------------------
Eclipse Arguments
----------------------
	src/main/resources/input/1997_1998
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
	hadoop jar airlinedatatest-0.0.1-SNAPSHOT.jar job.sort.secondarysort.AnalyzeConsecutiveArrivalDelaysMRJob  input output 
	
------------------------------------------------------
Reference
----------------------
	Pro Apache Hadoop : Page : 103
	More : D.Guid : #262
	