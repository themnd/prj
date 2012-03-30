-- Creating domains table

CREATE TABLE  `domains` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL UNIQUE,
  PRIMARY KEY (`id`),
  KEY `domain_name_idx` (`name`)
)
ENGINE = InnoDB;

insert into domains (id, name) values (1, 'admin');
insert into users (domain, login, name, pwd) values (1, 'admin', 'Administrator', '=YkGlRJwkv9ucCUdEI2nDbsV7VAMKP3EZLd1H9gAwixz88bgbTiazN5ns2foD2Iz4Pg0xWgKr5FpuvRb2Tpzyve227vh/w72KQ==');
