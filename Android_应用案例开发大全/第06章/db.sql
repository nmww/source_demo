drop table losebook;
drop table overtime;
drop table manager;
drop table record;
drop table orderbook;
drop table student;
drop table book;
drop table bdetailedinformation;

create table book
(
   ISBN varchar(20) primary key,
   B_Name varchar(50),
   B_Author varchar(50),   
   B_Publishment varchar(80),
   B_BuyTime varchar(50)
);
insert into book values('9787302164289','3ds max 9三维建模','程凤娟','清华大学出版社','2010-02-01');
insert into book values('9787121060953','photoshop cs3 图像处理','卓越科技','电子工业出版社','2003-02-01');
insert into book values('9787121102462','Java程序员职场全攻略-从小工到专家','吴亚峰','电子工业出版社','2004-04-05');
insert into book values('9787115227508','Android 2.0游戏开发实战宝典','吴亚峰','人民邮电出版社','2003-06-07');
insert into book values('9787030236630','PowerBuilder_10.5实用教程','樊金生','科学出版社','2005-07-05');
insert into book values('9787121079528','PowerBuilder 实用教程（第3版）','郑阿奇','电子工业出版社','2005-07-09');
insert into book values('9787302244158','大学计算机基础（21世纪普通高校计算机公共课程规划教材）','许薇，王东来 ','清华大学出版社','2005-0-09');
insert into book values('9787562324560','微型计算机原理及应用','吴荣光，吕钧星','华南理工大学出版社','2005-07-09');
insert into book values('9787111187776','算法导论','（美）科曼（Cormen,T.H.） 等著，潘金贵 等译','机械工业出版社','2005-07-09');

图书详细信息表
drop table bdetailedinformation;
create table bdetailedinformation
(
   B_Num varchar(20) primary key,      
   ISBN varchar(20),     
   Borrowed varchar(50),
   Ordered varchar(50),
   Introduction varchar(1000)
);


insert into bdetailedinformation values('10001','9787302164289','是','否','本书通过通俗易懂的实例解析,从简单建模到复杂动画的制作,逐步将读者引入动画设计堂');

insert into bdetailedinformation values('10002','9787302164289','是','否','本书通过通俗易懂的实例解析,从简单建模到复杂动画的制作,逐步将读者引入动画设计堂');

insert into bdetailedinformation values('10003','9787302164289','否','否','本书通过通俗易懂的实例解析,从简单建模到复杂动画的制作,逐步将读者引入动画设计堂');

insert into bdetailedinformation values('10004','9787302164289','否','否','本书通过通俗易懂的实例解析,从简单建模到复杂动画的制作,逐步将读者引入动画设计堂');

insert into bdetailedinformation values('10005','9787121060953','否','否','本书内容新颖,版式清晰,语言浅显易懂,操作举例丰富,每章以知识讲解+实战演练+上机练习为讲解构');

insert into bdetailedinformation values('10006','9787121060953','是','否','本书内容新颖,版式清晰,语言浅显易懂,操作举例丰富,每章以知识讲解+实战演练+上机练习为讲解结构');

insert into bdetailedinformation values('10007','9787121102462','否','否','本书以包罗万象的IT大江湖为背景,将Java职场中从入门前的学校菜鸟成长为技术大牛的过程展现给读者,内容饱满但又不失趣味性.在本书中既有入职前的行业探秘误区排除,也有入职后的口诀传授和江湖新锐兵器介绍');

insert into bdetailedinformation values('10008','9787121102462','是','否','本书以包罗万象的IT大江湖为背景,将Java职场中从入门前的学校菜鸟成长为技术大牛的过程展现给读者,内容饱满但又不失趣味性.在本书中既有入职前的行业探秘、误区排除,也有入职后的口诀传授和江湖新锐兵器介绍');


insert into bdetailedinformation values('10009','9787115227508','是','否','本书以Android手机游戏的开发为主题,结合真实的游戏案例向读者详细介绍了Android平台下游戏开发的整个流程,同时在游戏开发的介绍过程中还分享了笔者多年积累的开发技巧与经验');

insert into bdetailedinformation values('10010','9787115227508','是','否','本书以Android手机游戏的开发为主题,结合真实的游戏案例向读者详细介绍了Android平台下游戏开发的整个流程,同时在游戏开发的介绍过程中还分享了笔者多年积累的开发技巧与经验');

insert into bdetailedinformation values('10011','9787030236630','是','否','本书系统描述了PowerBuilder 10.5版的组成及其语言,事件和函数,对构成PowerBuilder的各种对象(应用,窗口,菜单,控件,数据窗口,用户对象)以及它们的创建和使用方法等内容做了详细讲解');

insert into bdetailedinformation values('10012','9787030236630','否','否','本书系统描述了PowerBuilder 10.5版的组成及其语言,事件和函数,对构成PowerBuilder的各种对象(应用,窗口,菜单,控件,数据窗口,用户对象)以及它们的创建和使用方法等内容做了详细讲解');


insert into bdetailedinformation values('10013','9787030236630','否','否','本书系统描述了PowerBuilder 10.5版的组成及其语言,事件和函数,对构成PowerBuilder的各种对象(应用,窗口,菜单,控件,数据窗口,用户对象)以及它们的创建和使用方法等内容做了详细讲解');
insert into bdetailedinformation values('10014','9787030236630','否','否','本书系统描述了PowerBuilder 10.5版的组成及其语言,事件和函数,对构成PowerBuilder的各种对象(应用,窗口,菜单,控件,数据窗口,用户对象)以及它们的创建和使用方法等内容做了详细讲解');




insert into bdetailedinformation values('10015','9787121079528','否','否','本书包含实用教程,习题,上机操作指导和综合应用实习等4部分.实用教程比较系统地介绍了PowerBuilder 9.0开发环境,PowerScript语言,窗口及窗口控件,创建数据库,数据窗口对象及控件,用户自定义对象及事件函数和结构,选单,SQL语句,游标,数据管道,PBL库管理器等');



insert into bdetailedinformation values('10016','9787121079528','否','否','本书包含实用教程,习题,上机操作指导和综合应用实习等4部分.实用教程比较系统地介绍了PowerBuilder 9.0开发环境,PowerScript语言,窗口及窗口控件,创建数据库,数据窗口对象及控件,用户自定义对象及事件函数和结构,选单,SQL语句,游标,数据管道,PBL库管理器等');




insert into bdetailedinformation values('10017','9787121079528','否','否','本书包含实用教程,习题,上机操作指导和综合应用实习等4部分.实用教程比较系统地介绍了PowerBuilder 9.0开发环境,PowerScript语言,窗口及窗口控件,创建数据库,数据窗口对象及控件,用户自定义对象及事件函数和结构,选单,SQL语句,游标,数据管道,PBL库管理器等');



insert into bdetailedinformation values('10018','9787121079528','否','否','本书包含实用教程,习题,上机操作指导和综合应用实习等4部分.实用教程比较系统地介绍了PowerBuilder 9.0开发环境,PowerScript语言,窗口及窗口控件,创建数据库,数据窗口对象及控件,用户自定义对象及事件函数和结构,选单,SQL语句,游标,数据管道,PBL库管理器等');



insert into bdetailedinformation values('10019','9787302244158','否','否','本书既注重基本原理和方法的阐述,又注重实践能力的培养,以理论与实践相结合的方式培养学生的应用能力.');


insert into bdetailedinformation values('10020','9787302244158','是','否','本书既注重基本原理和方法的阐述,又注重实践能力的培养,以理论与实践相结合的方式培养学生的应用能力.');


insert into bdetailedinformation values('10021','9787302244158','是','否','本书既注重基本原理和方法的阐述,又注重实践能力的培养,以理论与实践相结合的方式培养学生的应用能力.');

insert into bdetailedinformation values('10022','9787562324560','否','否','本书全面深入地介绍了微型计算机的基本组成,工作原理和实际应用.全书共10章,循序渐进地介绍了微型计算机的基本知识,从8086到19entium 4微处理器的内部结构和特点,寻址方式,指令系统及汇编语言程序设计,半导体存储器,8086中断系统,输入与输出接口技术,MSC-51单片机的功能及其扩展方法');


insert into bdetailedinformation values('10023','9787562324560','否','否','本书全面深入地介绍了微型计算机的基本组成,工作原理和实际应用.全书共10章,循序渐进地介绍了微型计算机的基本知识,从8086到19entium 4微处理器的内部结构和特点,寻址方式,指令系统及汇编语言程序设计,半导体存储器,8086中断系统,输入与输出接口技术,MSC-51单片机的功能及其扩展方法');


insert into bdetailedinformation values('10024','9787111187776','是','否','本书深入讨论各类算法,并着力使这些算法的设计和分析能为各个层次的读者接受.各章自成体系,可以作为独立的学习单元。算法以英语和伪代码的形式描述,具备初步程序设计经验的人就能看懂.说明和解释力求浅显易懂,不失深度和数学严谨性');


insert into bdetailedinformation values('10025','9787111187776','是','否','本书深入讨论各类算法,并着力使这些算法的设计和分析能为各个层次的读者接受.各章自成体系,可以作为独立的学习单元。算法以英语和伪代码的形式描述,具备初步程序设计经验的人就能看懂.说明和解释力求浅显易懂,不失深度和数学严谨性');

insert into bdetailedinformation values('10026','9787111187776','是','否','本书深入讨论各类算法,并着力使这些算法的设计和分析能为各个层次的读者接受.各章自成体系,可以作为独立的学习单元。算法以英语和伪代码的形式描述,具备初步程序设计经验的人就能看懂.说明和解释力求浅显易懂,不失深度和数学严谨性');



create table student
(
  S_Num varchar(20) primary key,
  S_Name varchar(50),
  S_Age varchar(10),
  S_Sex varchar(50),
  S_Class varchar(50),
  S_Department varchar(50),
  S_Phone varchar(11),
  S_Permitted varchar(50),
  S_Pwd varchar(20)
);

insert into student values('10001','李亚','20','女','计算机1班','计算机系','15176536034','是','001');
insert into student values('10002','王飞',21,'女','计算机1班','计算机系','13730220123','是','002');
insert into student values('10003','孙好',20,'男','计算机1班','计算机系','13633654578','是','003');
insert into student values('10004','何光',22,'男','计算机1班','计算机系','2578975','是','004');
insert into student values('10005','唐心',21,'女','计算机1班','计算机系','13936968956','是','005');
insert into student values('10006','宋理光',20,'男','计算机2班','计算机系','1234667','是','006');


//借阅图书信息表的
create table record
(
   B_Num varchar(50) primary key,
   S_Num varchar(20),
   BorrowTime varchar(50),
   ReturnTime varchar(50),
   Borrowed varchar(50),
   Ordered varchar(50)
);

insert into record values('10001','10001','2010-1-2','2010-3-2','是','否');
insert into record values('10002','10001','2010-1-23','2010-3-23','是','否');
insert into record values('10006','10001','2010-1-2','2010-3-2','是','否');
insert into record values('10008','10002','2010-1-2','2010-3-2','是','否');
insert into record values('10009','10001','2010-1-2','2010-3-2','是','否');

insert into record values('10010','10002','2010-1-2','2010-3-2','是','否');
insert into record values('10011','10001','2010-2-2','2010-4-2','是','否');
insert into record values('10020','10002','2010-1-2','2010-3-2','是','否');
insert into record values('10021','10001','2010-2-2','2010-4-2','是','否');
insert into record values('10025','10002','2010-1-2','2010-3-2','是','否');
insert into record values('10024','10002','2010-1-2','2010-3-2','是','否');
insert into record values('10026','10002','2010-1-2','2010-3-2','是','否');




create table orderbook
(
   B_Num varchar(50) primary key,
   S_Name varchar(50),
   S_Class varchar(50),
   B_Name varchar(50),
   S_Num varchar(20),
   B_Author varchar(50)
);

create table losebook
(
   GSBH int primary key,
   B_Num varchar(50), 
   B_Name varchar(50),
   S_Num varchar(20)
);



create table manager
(
   M_Num varchar(20) primary key,
   M_Permitted varchar(50),
   M_Pwd varchar(50)
);
insert into manager values('456','高级','123');



create table overtime
(
   S_Num varchar(20),
   B_Num varchar(20),
   B_Name varchar(50),
   overtime int(20),
   primary key(S_Num,B_Num)
);


insert into overtime values('10002','10020','大学计算机基础（21世纪普通高校计算机公共课程规划教材）',null);
insert into overtime values('10002','10024','算法导论',null);
insert into overtime values('10002','10025','算法导论',null);




