
------------------------------------------------------
Eclipse Arguments
----------------------
	src/main/resources/input/1997_1998
	output
	
	NOTE : -D<reduce count> do not have any effect when running in eclipse
	
------------------------------------------------------
Output Reference
----------------------
	src/main/resources/results/job_sort_totalorder

------------------------------------------------------
Hadoop Command
----------------------
	hadoop jar airlinedatatest-0.0.1-SNAPSHOT.jar job.sort.totalorder.SortAscMonthDescWeekMRJob  -Dmapred.reduce.tasks=4 input output 
	hadoop jar airlinedatatest-0.0.1-SNAPSHOT.jar job.sort.totalorder.SortAscMonthDescWeekMRJob input output