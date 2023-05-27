-- Dumping structure for table library.authors
CREATE TABLE IF NOT EXISTS `authors` (
  `a_id` int unsigned NOT NULL AUTO_INCREMENT,
  `a_name` varchar(150) NOT NULL,
  PRIMARY KEY (`a_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table library.authors: ~7 rows (approximately)
INSERT INTO `authors` (`a_id`, `a_name`) VALUES
	(1, 'Donald Ervin Knuth'),
	(2, 'Isaac Asimov'),
	(3, 'Dale Breckenridge Carnegie'),
	(4, 'Лев Давидович Ландау'),
	(5, 'Євген Михайлович Лі́фшиць'),
	(6, 'Bjarne Stroustrup'),
	(7, 'Тарас Григорович Шевченко');

-- Dumping structure for table library.books
CREATE TABLE IF NOT EXISTS `books` (
  `b_id` int unsigned NOT NULL AUTO_INCREMENT,
  `b_name` varchar(150) NOT NULL,
  `b_year` smallint unsigned NOT NULL,
  `b_quantity` smallint unsigned NOT NULL,
  PRIMARY KEY (`b_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table library.books: ~7 rows (approximately)
INSERT INTO `books` (`b_id`, `b_name`, `b_year`, `b_quantity`) VALUES
	(1, 'Гайдамаки', 2015, 2),
	(2, 'Кобзарь', 2018, 3),
	(3, 'Foundation and Empire', 2000, 5),
	(4, 'How to Win Friends and Influence People', 2019, 1),
	(5, 'The C++ Programming Language', 2012, 3),
	(6, 'Course of Theoretical Physics  2  2', 1981, 12),
	(7, 'The Art of Computer Programming', 2011, 7);

-- Dumping structure for view library.books_info
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `books_info` (
	`book_name` VARCHAR(150) NOT NULL COLLATE 'utf8mb3_general_ci',
	`book_authors` TEXT NULL COLLATE 'utf8mb3_general_ci',
	`book_geners` TEXT NULL COLLATE 'utf8mb3_general_ci',
	`b_year` SMALLINT(5) UNSIGNED NOT NULL,
	`b_quantity` SMALLINT(5) UNSIGNED NOT NULL
) ENGINE=MyISAM;

-- Dumping structure for function library.check_if_book_exist
DELIMITER //
CREATE FUNCTION `check_if_book_exist`(book_id INT) RETURNS tinyint(1)
    READS SQL DATA
BEGIN
	DECLARE result BOOLEAN;
	SELECT COUNT(*) INTO result FROM books WHERE books.b_id = book_id;
	RETURN result;
END//
DELIMITER ;

-- Dumping structure for function library.check_if_subscribers_exist
DELIMITER //
CREATE FUNCTION `check_if_subscribers_exist`(subscriber_id INT) RETURNS tinyint(1)
    READS SQL DATA
BEGIN
DECLARE result BOOLEAN;
SELECT COUNT(*) INTO result FROM subscribers WHERE subscribers.s_id = subscriber_id;
RETURN result;
END//
DELIMITER ;

-- Dumping structure for table library.genres
CREATE TABLE IF NOT EXISTS `genres` (
  `g_id` int unsigned NOT NULL AUTO_INCREMENT,
  `g_name` varchar(150) NOT NULL,
  PRIMARY KEY (`g_id`),
  UNIQUE KEY `UQ_genres_g_name` (`g_name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table library.genres: ~7 rows (approximately)
INSERT INTO `genres` (`g_id`, `g_name`) VALUES
	(5, 'Класика'),
	(4, 'Наука'),
	(1, 'Поезія'),
	(2, 'Програмування'),
	(3, 'Психологія'),
	(7, 'Фізика'),
	(6, 'Фантастика');

-- Dumping structure for function library.get_readers_by_books
DELIMITER //
CREATE FUNCTION `get_readers_by_books`(readed_books INT) RETURNS varchar(500) CHARSET utf8mb4
    READS SQL DATA
BEGIN
	DECLARE done INT DEFAULT 0;
	DECLARE current_id INT DEFAULT 0;
	DECLARE flg INT DEFAULT 0;
	DECLARE list_sername VARCHAR(500);
	DECLARE current_sername VARCHAR(50);
	DECLARE curs CURSOR FOR
		SELECT subscribers.s_id FROM subscribers
		JOIN subscriptions ON subscribers.s_id = subscriptions.sb_subscriber
		GROUP BY subscribers.s_id HAVING COUNT(subscriptions.sb_book) > readed_books;
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	OPEN curs;
	SET list_sername = '';
	REPEAT
	FETCH curs INTO current_id;
		IF NOT done THEN
			IF flg = 0 THEN
				SELECT s_name INTO list_sername FROM subscribers WHERE s_id = current_id;
				SET flg = 1;
			ELSE
				SELECT s_name INTO current_sername FROM subscribers WHERE s_id = current_id;
				SET list_sername = CONCAT(list_sername, ', ', current_sername);
			END IF;
		END IF;
	UNTIL done END REPEAT;
	CLOSE curs;
	RETURN (list_sername);
END//
DELIMITER ;

-- Dumping structure for procedure library.insert_subscription
DELIMITER //
CREATE PROCEDURE `insert_subscription`(
    IN book_id INT,
    IN subscriber_id INT,
    IN start_renting_date DATE,
    IN end_renting_date DATE,
    IN is_renting_active ENUM("Y", "N")
)
BEGIN    
    IF check_if_book_exist(book_id) = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Book with gived id does not exist';
    ELSEIF check_if_subscribers_exist(subscriber_id) = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Subscriber with given id does not exist';
    ELSEIF start_renting_date > CURDATE() THEN
    	  SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Start renting date cannot be in the future';
    ELSEIF end_renting_date < CURDATE() AND is_renting_active = "Y" THEN
    	  SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Subscription cannot be active, when it\'s already finished';
    ELSE
        INSERT INTO subscriptions(subscriptions.sb_book, subscriptions.sb_subscriber, subscriptions.sb_start, subscriptions.sb_finish, subscriptions.sb_is_active)
        VALUES(book_id, subscriber_id, start_renting_date, end_renting_date, is_renting_active);
    END IF;
END//
DELIMITER ;

-- Dumping structure for table library.m2m_books_authors
CREATE TABLE IF NOT EXISTS `m2m_books_authors` (
  `b_id` int unsigned NOT NULL,
  `a_id` int unsigned NOT NULL,
  PRIMARY KEY (`b_id`,`a_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table library.m2m_books_authors: ~8 rows (approximately)
INSERT INTO `m2m_books_authors` (`b_id`, `a_id`) VALUES
	(7, 1),
	(3, 2),
	(4, 3),
	(6, 4),
	(6, 5),
	(5, 6),
	(1, 7),
	(2, 7);

-- Dumping structure for table library.m2m_books_genres
CREATE TABLE IF NOT EXISTS `m2m_books_genres` (
  `b_id` int unsigned NOT NULL,
  `g_id` int unsigned NOT NULL,
  PRIMARY KEY (`b_id`,`g_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table library.m2m_books_genres: ~11 rows (approximately)
INSERT INTO `m2m_books_genres` (`b_id`, `g_id`) VALUES
	(1, 1),
	(2, 1),
	(5, 2),
	(7, 2),
	(4, 3),
	(6, 4),
	(1, 5),
	(2, 5),
	(3, 5),
	(3, 6),
	(6, 7);

-- Dumping structure for view library.owned_subscribers
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `owned_subscribers` (
	`s_name` VARCHAR(150) NOT NULL COLLATE 'utf8mb3_general_ci'
) ENGINE=MyISAM;

-- Dumping structure for table library.partitioned_subscriptions
CREATE TABLE IF NOT EXISTS `partitioned_subscriptions` (
  `sb_id` int NOT NULL,
  `sb_subscriber` int NOT NULL,
  `sb_book` int NOT NULL,
  `sb_start` date NOT NULL,
  `sb_finish` date DEFAULT NULL,
  `sb_is_active` enum('N','Y') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
/*!50500 PARTITION BY LIST  COLUMNS(sb_book)
(PARTITION Gaydamaku VALUES IN (1) ENGINE = InnoDB,
 PARTITION Kobzar VALUES IN (2) ENGINE = InnoDB,
 PARTITION Foundation VALUES IN (3) ENGINE = InnoDB,
 PARTITION Influence VALUES IN (4) ENGINE = InnoDB) */;

-- Dumping data for table library.partitioned_subscriptions: ~9 rows (approximately)
INSERT INTO `partitioned_subscriptions` (`sb_id`, `sb_subscriber`, `sb_book`, `sb_start`, `sb_finish`, `sb_is_active`) VALUES
	(2, 1, 1, '2021-01-12', '2021-02-12', 'N'),
	(86, 3, 1, '2020-08-03', '2020-09-03', 'Y'),
	(91, 4, 1, '2020-10-07', '2020-12-07', 'Y'),
	(42, 1, 2, '2022-06-11', '2022-08-11', 'N'),
	(102, 1, 2, '2021-10-07', '2021-11-07', 'N'),
	(3, 3, 3, '2022-05-17', '2022-07-17', 'Y'),
	(100, 1, 3, '2021-01-12', '2021-02-12', 'N'),
	(95, 1, 4, '2020-10-07', '2020-11-07', 'N');

-- Dumping structure for table library.subscribers
CREATE TABLE IF NOT EXISTS `subscribers` (
  `s_id` int unsigned NOT NULL AUTO_INCREMENT,
  `s_name` varchar(150) NOT NULL,
  PRIMARY KEY (`s_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table library.subscribers: ~4 rows (approximately)
INSERT INTO `subscribers` (`s_id`, `s_name`) VALUES
	(1, 'Коваленко О.О.'),
	(2, 'Ткаченко М.М.'),
	(3, 'Петренко П.П.'),
	(4, 'Петренко П.П.'),
	(5, 'Коваленко О.О.');

-- Dumping structure for table library.subscriptions
CREATE TABLE IF NOT EXISTS `subscriptions` (
  `sb_id` int unsigned NOT NULL AUTO_INCREMENT,
  `sb_subscriber` int unsigned NOT NULL,
  `sb_book` int unsigned NOT NULL,
  `sb_start` date NOT NULL,
  `sb_finish` date NOT NULL,
  `sb_is_active` enum('Y','N') NOT NULL,
  PRIMARY KEY (`sb_id`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table library.subscriptions: ~8 rows (approximately)
INSERT INTO `subscriptions` (`sb_id`, `sb_subscriber`, `sb_book`, `sb_start`, `sb_finish`, `sb_is_active`) VALUES
	(2, 1, 1, '2021-01-12', '2021-02-12', 'N'),
	(3, 3, 3, '2022-05-17', '2022-07-17', 'N'),
	(42, 1, 2, '2022-06-11', '2022-08-11', 'N'),
	(86, 3, 1, '2020-08-03', '2020-09-03', 'N'),
	(91, 4, 1, '2020-10-07', '2020-12-07', 'N'),
	(95, 1, 4, '2020-10-07', '2020-11-07', 'N'),
	(100, 1, 3, '2021-01-12', '2021-02-12', 'N'),
	(102, 1, 2, '2021-10-07', '2021-11-07', 'N'),
	(104, 1, 1, '2023-04-28', '2023-04-28', 'Y');

-- Dumping structure for trigger library.delete_subscribtions_trigger
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `delete_subscribtions_trigger` BEFORE DELETE ON `subscriptions` FOR EACH ROW BEGIN
	IF (SELECT COUNT(*) FROM subscriptions WHERE subscriptions.sb_book = OLD.sb_book AND subscriptions.sb_subscriber = OLD.sb_subscriber) > 0 THEN
		SET @cnt = @cnt + 1;
	END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger library.insert_subscriptions_trigger
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `insert_subscriptions_trigger` BEFORE INSERT ON `subscriptions` FOR EACH ROW BEGIN
	DECLARE CONTINUE HANDLER FOR 1062
		SET NEW.sb_id = NULL;
	IF (SELECT COUNT(*) FROM subscriptions WHERE subscriptions.sb_book = NEW.sb_book AND subscriptions.sb_subscriber = NEW.sb_subscriber AND subscriptions.sb_is_active = "Y") > 0 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cannot create subscription for this subscriber and this book, because it already exists in subscriptions';
	ELSEIF NEW.sb_start > NEW.sb_finish THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cannot create subscription for this subscriber and book, because renting start time is bigger than finish time';
	END IF;
	IF NEW.sb_start > CURDATE() THEN
		SET NEW.sb_start = CURDATE();
	END IF;
	IF NEW.sb_finish < CURDATE() AND NEW.sb_is_active = "Y" THEN
		SET NEW.sb_is_active = "N";
	END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger library.update_subscriptions_trigger
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `update_subscriptions_trigger` BEFORE UPDATE ON `subscriptions` FOR EACH ROW BEGIN
	DECLARE CONTINUE HANDLER FOR 1062
		SET NEW.sb_id = OLD.sb_id;
		IF (SELECT COUNT(*) FROM subscriptions WHERE subscriptions.sb_book = NEW.sb_book AND subscriptions.sb_subscriber = NEW.sb_subscriber AND OLD.sb_is_active = "Y") > 0 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cannot create subscription for this subscriber and this book, because it already exists in subscriptions';
	ELSEIF NEW.sb_start > NEW.sb_finish THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cannot create subscription for this subscriber and book, because renting start time is bigger than finish time';
	END IF;
	IF NEW.sb_start > CURDATE() THEN
		SET NEW.sb_start = OLD.sb_start;
	END IF;
	IF NEW.sb_finish < CURDATE() AND NEW.sb_is_active = "Y" THEN
		SET NEW.sb_is_active = "N";
	END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for view library.books_info
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `books_info`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `books_info` AS select distinct `books`.`b_name` AS `book_name`,group_concat(distinct `authors`.`a_name` separator ', ') AS `book_authors`,group_concat(distinct `genres`.`g_name` separator ', ') AS `book_geners`,`books`.`b_year` AS `b_year`,`books`.`b_quantity` AS `b_quantity` from (((((`books` left join `subscriptions` on((`books`.`b_id` = `subscriptions`.`sb_book`))) join `m2m_books_authors` on((`books`.`b_id` = `m2m_books_authors`.`b_id`))) join `authors` on((`m2m_books_authors`.`a_id` = `authors`.`a_id`))) join `m2m_books_genres` on((`books`.`b_id` = `m2m_books_genres`.`b_id`))) join `genres` on((`m2m_books_genres`.`g_id` = `genres`.`g_id`))) group by `books`.`b_id` having (count(`subscriptions`.`sb_book`) = 0);

-- Dumping structure for view library.owned_subscribers
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `owned_subscribers`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `owned_subscribers` AS select distinct `subscribers`.`s_name` AS `s_name` from (`subscribers` join `subscriptions` on((`subscribers`.`s_id` = `subscriptions`.`sb_subscriber`))) where (`subscriptions`.`sb_is_active` = 'N');

-- Dumping structure for procedure library.add_symbol_to_book_with_authors
DELIMITER //
CREATE PROCEDURE `add_symbol_to_book_with_authors`(amount_of_authors INT, symbol VARCHAR(3))
BEGIN
	DECLARE done INT DEFAULT 0;
	DECLARE current_row INT;
	DECLARE curs CURSOR FOR
		SELECT b_id FROM books
		JOIN m2m_books_authors USING(b_id)
		GROUP BY b_id
		HAVING COUNT(m2m_books_authors.a_id) = amount_of_authors;
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1; OPEN curs;
	WHILE done = 0 DO
		FETCH curs INTO current_row;
		UPDATE books SET b_name=CONCAT(b_name, ' ', symbol) WHERE b_id = current_row;
	END WHILE;
	CLOSE curs;
END//
DELIMITER ;
