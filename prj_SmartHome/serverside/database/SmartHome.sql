-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Anamakine: localhost
-- Üretim Zamanı: 16 Oca 2020, 16:20:53
-- Sunucu sürümü: 10.4.8-MariaDB
-- PHP Sürümü: 7.3.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `SmartHome`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tblAccount`
--

CREATE TABLE `tblAccount` (
  `Id` int(11) NOT NULL,
  `Email` varchar(255) DEFAULT '',
  `PhoneNumber` varchar(100) DEFAULT '',
  `Password` varchar(255) DEFAULT '',
  `Nickname` varchar(100) DEFAULT '',
  `AccountLocation` varchar(50) DEFAULT '',
  `TemperatureUnit` varchar(10) DEFAULT 'C',
  `TimeZone` varchar(50) DEFAULT '',
  `VerificationCode` int(11) DEFAULT NULL,
  `IsActive` int(11) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tablo döküm verisi `tblAccount`
--

INSERT INTO `tblAccount` (`Id`, `Email`, `PhoneNumber`, `Password`, `Nickname`, `AccountLocation`, `TemperatureUnit`, `TimeZone`, `VerificationCode`, `IsActive`) VALUES
(1, 'kerimfirat@gmail.com', '05439728313', '123456', 'raptiye', 'unknow', 'C', 'istanbul', 55555, 1),
(2, 'kerimfirat@hotmail.com', '', '1234563', '', 'Istanbuldf', 'f', 'timezonehw', 554106, 1);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tblAutomation`
--

CREATE TABLE `tblAutomation` (
  `Id` int(11) NOT NULL,
  `AccountId` int(11) DEFAULT NULL,
  `FamilyId` int(11) DEFAULT NULL,
  `Name` varchar(150) DEFAULT NULL,
  `CoverImage` int(11) DEFAULT NULL,
  `AutomationCondition` varchar(10) DEFAULT NULL,
  `ValidTimePeriod` varchar(50) DEFAULT NULL,
  `CurrentCity` varchar(50) DEFAULT NULL,
  `IsActive` int(11) DEFAULT 1,
  `ItemSort` int(11) DEFAULT NULL,
  `Monday` int(11) DEFAULT NULL,
  `Tuesday` int(11) DEFAULT NULL,
  `Wednesday` int(11) DEFAULT NULL,
  `Thursday` int(11) DEFAULT NULL,
  `Friday` int(11) DEFAULT NULL,
  `Saturday` int(11) DEFAULT NULL,
  `Sunday` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tblAutomationCondition`
--

CREATE TABLE `tblAutomationCondition` (
  `Id` int(11) NOT NULL,
  `AccountId` int(11) DEFAULT NULL,
  `AutomationId` int(11) DEFAULT NULL,
  `ConditionType` varchar(15) DEFAULT NULL,
  `ConditionValue` varchar(50) DEFAULT NULL,
  `DeviceId` int(11) DEFAULT NULL,
  `DeviceSwitch` varchar(10) DEFAULT NULL,
  `CurrentCity` varchar(50) DEFAULT NULL,
  `Monday` int(11) DEFAULT -1,
  `Tuesday` int(11) DEFAULT -1,
  `Wednesday` int(11) DEFAULT -1,
  `Thursday` int(11) DEFAULT -1,
  `Friday` int(11) DEFAULT -1,
  `Saturday` int(11) DEFAULT -1,
  `Sunday` int(11) DEFAULT -1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tblAutomationOperations`
--

CREATE TABLE `tblAutomationOperations` (
  `Id` int(11) NOT NULL,
  `AccountId` int(11) DEFAULT NULL,
  `AutomationId` int(11) DEFAULT NULL,
  `ActionType` varchar(25) DEFAULT NULL,
  `DeviceId` int(11) DEFAULT NULL,
  `DeviceSwitch` varchar(10) DEFAULT NULL,
  `AutomationIdAssign` int(11) DEFAULT NULL,
  `TimeLapseValue` varchar(15) DEFAULT NULL,
  `Command` varchar(50) DEFAULT NULL,
  `ItemSort` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tblDevice`
--

CREATE TABLE `tblDevice` (
  `Id` int(11) NOT NULL,
  `AccountId` int(11) DEFAULT NULL,
  `FamilyId` int(11) DEFAULT NULL,
  `DeviceLocation` int(11) DEFAULT NULL,
  `DeviceName` varchar(100) DEFAULT NULL,
  `VirtualId` varchar(100) DEFAULT NULL,
  `IpAddress` varchar(100) DEFAULT NULL,
  `MacAddress` varchar(50) DEFAULT NULL,
  `DeviceType` varchar(50) DEFAULT NULL,
  `DeviceTimeZone` varchar(50) DEFAULT NULL,
  `ItemSort` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tablo döküm verisi `tblDevice`
--

INSERT INTO `tblDevice` (`Id`, `AccountId`, `FamilyId`, `DeviceLocation`, `DeviceName`, `VirtualId`, `IpAddress`, `MacAddress`, `DeviceType`, `DeviceTimeZone`, `ItemSort`) VALUES
(1, 1, 1, 1, 'Lamba', '1fdfdbsfddmacaddress', '155.114.', 'macadrrss', 'lamp', 'istanbul', 3),
(7, 1, 1, 1, 'lambam', 'fdr34343', '555.4.154.12', 'dfd5df5macadres', 'lamp', 'istanbul', 1);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tblDontDisturbDevices`
--

CREATE TABLE `tblDontDisturbDevices` (
  `Id` int(11) NOT NULL,
  `AccountId` int(11) DEFAULT NULL,
  `Title` varchar(100) DEFAULT NULL,
  `TimeStart` varchar(15) DEFAULT NULL,
  `TimeEnd` varchar(15) DEFAULT NULL,
  `DevicesId` varchar(500) DEFAULT NULL,
  `Monday` int(11) DEFAULT 1,
  `Tuesday` int(11) DEFAULT 1,
  `Wednesday` int(11) DEFAULT 1,
  `Thursday` int(11) DEFAULT 1,
  `Friday` int(11) DEFAULT 1,
  `Saturday` int(11) DEFAULT 1,
  `Sunday` int(11) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tablo döküm verisi `tblDontDisturbDevices`
--

INSERT INTO `tblDontDisturbDevices` (`Id`, `AccountId`, `Title`, `TimeStart`, `TimeEnd`, `DevicesId`, `Monday`, `Tuesday`, `Wednesday`, `Thursday`, `Friday`, `Saturday`, `Sunday`) VALUES
(1, 4, 'Rahatsız Etme1', '12:00', '13:00', '4,5,9,101', 1, 1, 1, 1, 1, 1, 1),
(2, 1, 'Rahatsız Etme2', '11:00', '11:30', '5', 1, 1, 1, 1, 1, 1, 1),
(3, 1, 'Rahatsız Etme3', '10:02', '10:55', '4', 1, 1, 1, 1, 1, 1, 1),
(29, 4, '2', '13:55', '16:00', '4,5,9,101', 1, 1, 1, 1, 1, 1, 1),
(30, 4, '3', '18:57', '19:10', '4,5,9,101', 1, 1, 1, 1, 1, 1, 1);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tblFamily`
--

CREATE TABLE `tblFamily` (
  `Id` int(11) NOT NULL,
  `AccountId` int(11) DEFAULT NULL,
  `FamilyName` varchar(100) DEFAULT NULL,
  `RoomsId` varchar(255) DEFAULT NULL,
  `FamilyLocation` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tablo döküm verisi `tblFamily`
--

INSERT INTO `tblFamily` (`Id`, `AccountId`, `FamilyName`, `RoomsId`, `FamilyLocation`) VALUES
(1, 1, 'Aile1', '1,2,3,4,5', 'loc'),
(4, 1, 'Aile2', '1,2,3,4,5', 'loc'),
(5, 1, 'Aile3', '1,2,3,4,5', 'loc');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tblFamilyMember`
--

CREATE TABLE `tblFamilyMember` (
  `Id` int(11) NOT NULL,
  `FamilyId` int(11) DEFAULT NULL,
  `AccountId` int(11) DEFAULT NULL,
  `AccountIsAdmin` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tablo döküm verisi `tblFamilyMember`
--

INSERT INTO `tblFamilyMember` (`Id`, `FamilyId`, `AccountId`, `AccountIsAdmin`) VALUES
(1, 1, 1, 1),
(2, 1, 1, 1),
(3, 5, 1, 0),
(9, 62, 1, 0),
(11, 1, 1, 1);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tblFeedback`
--

CREATE TABLE `tblFeedback` (
  `Id` int(11) NOT NULL,
  `AccountId` int(11) DEFAULT NULL,
  `Content` varchar(255) DEFAULT NULL,
  `FdDateTime` varchar(50) DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tablo döküm verisi `tblFeedback`
--

INSERT INTO `tblFeedback` (`Id`, `AccountId`, `Content`, `FdDateTime`) VALUES
(1, 1, 'Diğerleri', '08-01-2020 15:21'),
(3, 1, 'Cihaz', '08-01-2020 15:26');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tblHelpCenter`
--

CREATE TABLE `tblHelpCenter` (
  `Id` int(11) NOT NULL,
  `Title` varchar(255) DEFAULT NULL,
  `ContentPath` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tablo döküm verisi `tblHelpCenter`
--

INSERT INTO `tblHelpCenter` (`Id`, `Title`, `ContentPath`) VALUES
(1, 'Bu bir yardım başlığıdır1', 'help1.html'),
(2, 'YARDIM BAŞLIĞI', 'help2.html');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tblMessageCenter`
--

CREATE TABLE `tblMessageCenter` (
  `Id` int(11) NOT NULL,
  `AccountId` int(11) DEFAULT NULL,
  `Message` varchar(255) DEFAULT NULL,
  `MessageDateTime` varchar(50) DEFAULT NULL,
  `MessageType` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tablo döküm verisi `tblMessageCenter`
--

INSERT INTO `tblMessageCenter` (`Id`, `AccountId`, `Message`, `MessageDateTime`, `MessageType`) VALUES
(1, 1, 'BU MESAJ SANA', '07-02-2020 17:07', 2);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tblRoom`
--

CREATE TABLE `tblRoom` (
  `Id` int(11) NOT NULL,
  `AccountId` int(11) DEFAULT NULL,
  `FamilyId` int(11) DEFAULT NULL,
  `RoomName` varchar(100) DEFAULT NULL,
  `ItemSort` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tablo döküm verisi `tblRoom`
--

INSERT INTO `tblRoom` (`Id`, `AccountId`, `FamilyId`, `RoomName`, `ItemSort`) VALUES
(1, 1, 1, 'Mutfak', 1),
(2, 1, 1, 'Oturma Odası', 1),
(3, 1, 1, 'Yemek Odası', 1),
(4, 1, 1, 'Çocuk Odası', 2);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tblScenario`
--

CREATE TABLE `tblScenario` (
  `Id` int(11) NOT NULL,
  `AccountId` int(11) DEFAULT NULL,
  `FamilyId` int(11) DEFAULT NULL,
  `Name` varchar(150) DEFAULT NULL,
  `CoverImage` int(11) DEFAULT NULL,
  `ShowOnMainPage` int(11) DEFAULT NULL,
  `ItemSort` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tablo döküm verisi `tblScenario`
--

INSERT INTO `tblScenario` (`Id`, `AccountId`, `FamilyId`, `Name`, `CoverImage`, `ShowOnMainPage`, `ItemSort`) VALUES
(1, 1, 1, 'Senaryo1', 15, 1, 1),
(2, 1, 1, 'Senaryo2', 16, 1, 2);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tblScenarioSub`
--

CREATE TABLE `tblScenarioSub` (
  `Id` int(11) NOT NULL,
  `AccountId` int(11) DEFAULT NULL,
  `ScenarioId` int(11) DEFAULT NULL,
  `ActionType` varchar(25) DEFAULT NULL,
  `DeviceId` int(11) DEFAULT NULL,
  `DeviceSwitch` varchar(10) DEFAULT NULL,
  `AutomationId` int(11) DEFAULT NULL,
  `TimeLapseValue` varchar(15) DEFAULT NULL,
  `Command` varchar(50) DEFAULT NULL,
  `ItemSort` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tablo döküm verisi `tblScenarioSub`
--

INSERT INTO `tblScenarioSub` (`Id`, `AccountId`, `ScenarioId`, `ActionType`, `DeviceId`, `DeviceSwitch`, `AutomationId`, `TimeLapseValue`, `Command`, `ItemSort`) VALUES
(1, 1, 1, 'device', 4, 'ON', 0, '0', '/kerimfirathesap/device1macadresss/ON', 1),
(2, 1, 1, 'device', 44, 'OFF', 0, '0', '/virtualaddress/OFF', 2);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tblSchedule`
--

CREATE TABLE `tblSchedule` (
  `Id` int(11) NOT NULL,
  `AccountId` int(11) DEFAULT NULL,
  `DeviceId` int(11) DEFAULT NULL,
  `ScheduleTime` time DEFAULT NULL,
  `Switch` varchar(10) DEFAULT NULL,
  `IsActive` int(11) DEFAULT 1,
  `Monday` int(11) DEFAULT NULL,
  `Tuesday` int(11) DEFAULT NULL,
  `Wednesday` int(11) DEFAULT NULL,
  `Thursday` int(11) DEFAULT NULL,
  `Friday` int(11) DEFAULT NULL,
  `Saturday` int(11) DEFAULT NULL,
  `Sunday` int(11) DEFAULT NULL,
  `OnlyOnce` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tablo döküm verisi `tblSchedule`
--

INSERT INTO `tblSchedule` (`Id`, `AccountId`, `DeviceId`, `ScheduleTime`, `Switch`, `IsActive`, `Monday`, `Tuesday`, `Wednesday`, `Thursday`, `Friday`, `Saturday`, `Sunday`, `OnlyOnce`) VALUES
(1, 1, 4, '11:10:00', 'ON', 1, 1, 1, 1, 1, 1, 1, 1, 0),
(2, 1, 4, '12:50:00', 'OFF', 1, 1, 1, 1, 1, 1, 1, 1, 0);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tblSettings`
--

CREATE TABLE `tblSettings` (
  `Id` int(11) NOT NULL,
  `AccountId` int(11) DEFAULT NULL,
  `DontDisturb` int(11) DEFAULT NULL,
  `Sound` int(11) DEFAULT NULL,
  `PushNotification` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tesS`
--

CREATE TABLE `tesS` (
  `id` int(11) NOT NULL,
  `date` date NOT NULL,
  `datetime` datetime NOT NULL,
  `c` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tablo döküm verisi `tesS`
--

INSERT INTO `tesS` (`id`, `date`, `datetime`, `c`) VALUES
(8, '2029-12-20', '2029-12-20 00:00:00', '00:05:00'),
(8, '2029-12-20', '2029-12-20 00:00:00', '00:05:00'),
(8, '2029-12-20', '2029-12-20 00:00:00', '00:05:00'),
(8, '2029-12-20', '2029-12-20 00:00:00', '00:05:00'),
(8, '2029-12-20', '2029-12-20 00:00:00', '00:05:00'),
(4, '2019-12-20', '2019-12-20 00:00:00', '22:05:00');

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `tblAccount`
--
ALTER TABLE `tblAccount`
  ADD PRIMARY KEY (`Id`);

--
-- Tablo için indeksler `tblAutomation`
--
ALTER TABLE `tblAutomation`
  ADD PRIMARY KEY (`Id`);

--
-- Tablo için indeksler `tblAutomationCondition`
--
ALTER TABLE `tblAutomationCondition`
  ADD PRIMARY KEY (`Id`);

--
-- Tablo için indeksler `tblAutomationOperations`
--
ALTER TABLE `tblAutomationOperations`
  ADD PRIMARY KEY (`Id`);

--
-- Tablo için indeksler `tblDevice`
--
ALTER TABLE `tblDevice`
  ADD PRIMARY KEY (`Id`);

--
-- Tablo için indeksler `tblDontDisturbDevices`
--
ALTER TABLE `tblDontDisturbDevices`
  ADD PRIMARY KEY (`Id`);

--
-- Tablo için indeksler `tblFamily`
--
ALTER TABLE `tblFamily`
  ADD PRIMARY KEY (`Id`);

--
-- Tablo için indeksler `tblFamilyMember`
--
ALTER TABLE `tblFamilyMember`
  ADD PRIMARY KEY (`Id`);

--
-- Tablo için indeksler `tblFeedback`
--
ALTER TABLE `tblFeedback`
  ADD PRIMARY KEY (`Id`);

--
-- Tablo için indeksler `tblHelpCenter`
--
ALTER TABLE `tblHelpCenter`
  ADD PRIMARY KEY (`Id`);

--
-- Tablo için indeksler `tblMessageCenter`
--
ALTER TABLE `tblMessageCenter`
  ADD PRIMARY KEY (`Id`);

--
-- Tablo için indeksler `tblRoom`
--
ALTER TABLE `tblRoom`
  ADD PRIMARY KEY (`Id`);

--
-- Tablo için indeksler `tblScenario`
--
ALTER TABLE `tblScenario`
  ADD PRIMARY KEY (`Id`);

--
-- Tablo için indeksler `tblScenarioSub`
--
ALTER TABLE `tblScenarioSub`
  ADD PRIMARY KEY (`Id`);

--
-- Tablo için indeksler `tblSchedule`
--
ALTER TABLE `tblSchedule`
  ADD PRIMARY KEY (`Id`);

--
-- Tablo için indeksler `tblSettings`
--
ALTER TABLE `tblSettings`
  ADD PRIMARY KEY (`Id`);

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

--
-- Tablo için AUTO_INCREMENT değeri `tblAccount`
--
ALTER TABLE `tblAccount`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Tablo için AUTO_INCREMENT değeri `tblAutomation`
--
ALTER TABLE `tblAutomation`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Tablo için AUTO_INCREMENT değeri `tblAutomationCondition`
--
ALTER TABLE `tblAutomationCondition`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Tablo için AUTO_INCREMENT değeri `tblAutomationOperations`
--
ALTER TABLE `tblAutomationOperations`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Tablo için AUTO_INCREMENT değeri `tblDevice`
--
ALTER TABLE `tblDevice`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Tablo için AUTO_INCREMENT değeri `tblDontDisturbDevices`
--
ALTER TABLE `tblDontDisturbDevices`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- Tablo için AUTO_INCREMENT değeri `tblFamily`
--
ALTER TABLE `tblFamily`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Tablo için AUTO_INCREMENT değeri `tblFamilyMember`
--
ALTER TABLE `tblFamilyMember`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Tablo için AUTO_INCREMENT değeri `tblFeedback`
--
ALTER TABLE `tblFeedback`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Tablo için AUTO_INCREMENT değeri `tblHelpCenter`
--
ALTER TABLE `tblHelpCenter`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Tablo için AUTO_INCREMENT değeri `tblMessageCenter`
--
ALTER TABLE `tblMessageCenter`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Tablo için AUTO_INCREMENT değeri `tblRoom`
--
ALTER TABLE `tblRoom`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Tablo için AUTO_INCREMENT değeri `tblScenario`
--
ALTER TABLE `tblScenario`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Tablo için AUTO_INCREMENT değeri `tblScenarioSub`
--
ALTER TABLE `tblScenarioSub`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Tablo için AUTO_INCREMENT değeri `tblSchedule`
--
ALTER TABLE `tblSchedule`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Tablo için AUTO_INCREMENT değeri `tblSettings`
--
ALTER TABLE `tblSettings`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
