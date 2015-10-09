
set hive.support.concurrency;
set hive.enforce.bucketing;
set hive.exec.dynamic.partition.mode;
set hive.txn.manager;
set hive.compactor.initiator.on;
set hive.compactor.worker.threads;

set hive.support.concurrency=true;
set hive.enforce.bucketing=true;
set hive.exec.dynamic.partition.mode=nonstrict;
set hive.txn.manager=org.apache.hadoop.hive.ql.lockmgr.DbTxnManager;
set hive.compactor.initiator.on=true;
set hive.compactor.worker.threads=2;

CREATE EXTERNAL TABLE ext (field1 STRING, field2 STRING) 
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
LOCATION '/home/mountain/h/src/main/java/delta/way2/hive/ext'
TBLPROPERTIES("transactional"="true");
        