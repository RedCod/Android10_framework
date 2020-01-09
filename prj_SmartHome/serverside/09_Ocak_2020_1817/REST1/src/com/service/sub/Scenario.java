package com.service.sub;

public class Scenario {
	/**
	 * Scenario(Senaryo) işlemleri. Kaydet,Sil,Düzenle,etc.
	 * used by:
	 * 		-WS_Scenario.java
	 */
	/*
	 CREATE TABLE IF NOT EXISTS tblScenario (
    Id INT AUTO_INCREMENT,
    AccountId INT,
    FamilyId INT,
    Name VARCHAR(150),
    CoverImage INT,
    ShowOnMainPage INT,
    ItemSort INT,
    PRIMARY KEY (Id)
);
CREATE TABLE IF NOT EXISTS tblScenarioSub (
    Id INT AUTO_INCREMENT,
    AccountId INT,
    ScenarioId INT,
    ActionType VARCHAR(25),
    DeviceId INT,
    DeviceSwitch VARCHAR(10),
    AutomationId INT,
    TimeLapseValue VARCHAR(15),
    ItemSort INT,
    PRIMARY KEY (Id)
);
	 */
	private static String TAG = "Scenario";
	public Scenario() {}
	
	
}
