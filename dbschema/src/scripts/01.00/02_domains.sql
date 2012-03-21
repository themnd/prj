-- Creating domains table

CREATE TABLE  `domains` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB;

insert into domains (id, name) values (1, 'admin');