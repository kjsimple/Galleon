alter table ACT_ID_MEMBERSHIP 
    drop constraint ACT_FK_MEMB_GROUP;
    
alter table ACT_ID_MEMBERSHIP 
    drop constraint ACT_FK_MEMB_USER;

drop table IF EXISTS ACT_ID_INFO;
drop table IF EXISTS ACT_ID_GROUP;
drop table IF EXISTS ACT_ID_MEMBERSHIP;
drop table IF EXISTS ACT_ID_USER;