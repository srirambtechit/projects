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
	APP_ALLOWED_TIME_TO("app.time.allowed.to"),
	APP_CONFIG_FILE("app.config.file"),
	TOMCAT_CATALINA_HOME("tomcat.catalina.home");
	//@formatter:on

	private static Configurations configs;
	private static Configuration config;

	static {
		configs = new Configurations();
		try {
			config = configs.properties(new File("config.properties"));
		} catch (ConfigurationException cex) {
		}
	}

	private String key;

	private Configs(String key) {
		this.key = key;
	}

	public static String key(Configs cfg) {
		return cfg.key;
	}

	public static String value(Configs cfg) {
		return config.getString(cfg.key);
	}

	public static Configurations getConfig() {
		return configs;
	}

}
