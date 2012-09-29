-- Creating domains table

CREATE TABLE  `domains` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `domain` int(11) NULL,
  `name` varchar(255) NOT NULL UNIQUE,
  PRIMARY KEY (`id`),
  KEY `domain_name_idx` (`name`)
)
ENGINE = InnoDB;

insert into domains (id, domain, name) values (1, NULL, 'admin');
