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

