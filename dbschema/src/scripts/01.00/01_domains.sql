-- Creating domains table

CREATE TABLE  `domains` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL UNIQUE,
  PRIMARY KEY (`id`),
  KEY `domain_name_idx` (`name`)
)
ENGINE = InnoDB;

insert into domains (id, name) values (1, 'admin');
