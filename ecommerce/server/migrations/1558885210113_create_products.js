module.exports = {
    "up": "CREATE TABLE `products` (`Id` varchar(64) NOT NULL,`Name` longtext NOT NULL,`Code` MEDIUMTEXT NOT NULL,`IsActive` tinyint(1) NOT NULL,`Description` longtext NULL,`CategoryId` varchar(64) NOT NULL,`Quantity` varchar(64) NOT NULL,`Color` varchar(64) NOT NULL,`Size` varchar(64) NOT NULL,`Image` varchar(255) NOT NULL,`Price` varchar(255) NOT NULL, PRIMARY KEY (`Id`), UNIQUE KEY `Id` (`Id`),  KEY `CategoryId_idx` (`CategoryId`), CONSTRAINT `ProductCategoryId` FOREIGN KEY (`CategoryId`) REFERENCES `category` (`id`));",
    "down": "DROP TABLE `products`"
}