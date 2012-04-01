-- Creating groups table

CREATE TABLE  `groups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `domain` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `groups_domain_idx` (`domain`),
  KEY `groups_name_idx` (`name`)
)
ENGINE = InnoDB;

insert into groups (id, domain, name) values (1, 1, 'Administrators');
