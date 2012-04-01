-- Creating users table

CREATE TABLE  `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `domain` int(11) NOT NULL,
  `login` varchar(50) NOT NULL UNIQUE,
  `name` varchar(255) DEFAULT NULL,
  `pwd` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,  
  PRIMARY KEY (`id`),
  KEY `users_domain_idx` (`domain`),
  KEY `users_login_idx` (`login`)
)
ENGINE = InnoDB;

CREATE TABLE  `usergroups` (
  `groupid` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  PRIMARY KEY (`groupid`, `userid`),
  KEY `usergroups_group_idx` (`groupid`),
  KEY `usergroups_user_idx` (`userid`)
)
ENGINE = InnoDB;

insert into users (id, domain, login, name, pwd) values (1, 1, 'admin', 'Administrator', '=YkGlRJwkv9ucCUdEI2nDbsV7VAMKP3EZLd1H9gAwixz88bgbTiazN5ns2foD2Iz4Pg0xWgKr5FpuvRb2Tpzyve227vh/w72KQ==');
insert into usergroups (groupid, userid) values (1, 1);
