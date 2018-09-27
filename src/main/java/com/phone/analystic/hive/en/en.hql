1、编写事件的维度类和修改操作基础维度服务
2、编写udf函数

create function phone_event as 'com.phone.analystic.hive.EventDimensionUdf' using jar 'hdfs://hadoop01:9000/phone/udfjars/phone_analystic-1.0.jar';
create function phone_date as 'com.phone.analystic.hive.DateDimensionUdf' using jar 'hdfs://hadoop01:9000/phone/udfjars/phone_analystic-1.0.jar';
create function phone_platform as 'com.phone.analystic.hive.PlatformDimensionUdf' using jar 'hdfs://hadoop01:9000/phone/udfjars/phone_analystic-1.0.jar';


创键元数据对应的临时表：
create external table if not exists phone_tmp(
ver string,
s_time string,
en string,
u_ud string,
u_mid string,
u_sd string,
c_time string,
l string,
b_iev string,
b_rst string,
p_url string,
p_ref string,
tt string,
pl string,
ip String,
oid String,
`on` String,
cua String,
cut String,
pt String,
ca String,
ac String,
kv_ String,
du String,
browserName String,
browserVersion String,
osName String,
osVersion String,
country String,
province String,
city string
)
partitioned by(month string,day string)
;

load data inpath '/ods/09/19' into table phone_tmp partition(month=09,day=19);

3、创建hive表：
create external table if not exists phone(
ver string,
s_time string,
en string,
u_ud string,
u_mid string,
u_sd string,
c_time string,
l string,
b_iev string,
b_rst string,
p_url string,
p_ref string,
tt string,
pl string,
ip String,
oid String,
on String,
cua String,
cut String,
pt String,
ca String,
ac String,
kv_ String,
du String,
browserName String,
browserVersion String,
osName String,
osVersion String,
country String,
province String,
city string
)
partitioned by(month string,day string)
stored as orc
;


set hive.exec.local.mode=true;
from phone_tmp
insert into phone partition(month=09,day=19)
select
ver,
s_time,
en,
u_ud,
u_mid,
u_sd,
c_time,
l,
b_iev,
b_rst,
p_url,
p_ref,
tt,
pl,
ip,
oid,
`on`,
cua,
cut,
pt,
ca,
ac,
kv_,
du,
browserName,
browserVersion,
osName,
osVersion,
country,
province,
city
where month = 9
and day = 19
;

