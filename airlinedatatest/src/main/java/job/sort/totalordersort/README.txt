
------------------------------------------------------
Eclipse Arguments
----------------------
	src/main/resources/input/1997_1998
	output
------------------------------------------------------
Output Reference in Eclipse
----------------------------
	src/main/resources/results/job_sort_totalorder
	
------------------------------------------------------
Output Format
----------------------------
	Month, Day of the Week, Year, Date, ...

------------------------------------------------------
Hadoop Command
----------------------
	hadoop jar airlinedatatest-0.0.1-SNAPSHOT.jar job.sort.totalorder.SortAscMonthDescWeekMRJob  -Dmapred.reduce.tasks=4 input output 
	hadoop jar airlinedatatest-0.0.1-SNAPSHOT.jar job.sort.totalorder.SortAscMonthDescWeekMRJob input output
	
------------------------------------------------------
Reference
----------------------
	Pro Apache Hadoop : Page : 89
	