-- Creating users table

CREATE TABLE  `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `domain` int(11) NOT NULL DEFAULT '0',
  `login` varchar(50) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `pwd` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB;

