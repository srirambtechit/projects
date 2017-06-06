package com.msrm.sqlrunner.core;

import java.io.File;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

public enum Configs {

	//@formatter:off
	DB_USERNAME("db.username"),
	DB_PASSWORD("db.password"),
	DB_JDBCURL("db.jdbcUrl"),
	APP_USER_MAXALLOWED("app.user.maxAllowed"), 
	APP_USER_DB_FILE("app.user.db"), 
	APP_SQL_ALLOWED_PER_USER("app.sql.allowedPerUser"), 
	APP_ALLOWED_TIME_FROM("app.time.allowed.from"), 
	APP_ALLOWED_TIME_TO("app.time.allowed.to");
	//@formatter:on

	private static final Configurations configs = new Configurations();

	private String key;

	private Configs(String key) {
		this.key = key;
	}

	public static String get(Configs cfg) {
		try {
			Configuration config = configs.properties(new File("config.properties"));
			return config.getString(cfg.key);
		} catch (ConfigurationException cex) {
			return "";
		}
	}

	public static Configurations getConfig() {
		return configs;
	}

}
