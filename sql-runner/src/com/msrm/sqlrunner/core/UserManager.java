package com.msrm.sqlrunner.core;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;

import com.msrm.sqlrunner.beans.User;
import com.msrm.sqlrunner.beans.User.Role;
import com.msrm.sqlrunner.exception.ExceptionUtil;
import com.msrm.sqlrunner.exception.SqlRunnerException;

public class UserManager {

	private static Logger logger = Logger.getLogger(UserManager.class);

	public static boolean add(User user) throws SqlRunnerException {
		String userFile = Configs.get(Configs.APP_USER_DB_FILE);
		try {
			List<String> lines = new ArrayList<>();
			if (userExists(user.getUsername())) {
				return false;
			}
			lines.add(user.getUsername() + ":" + user.getPassword());
			Files.write(Paths.get(userFile), lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
		} catch (IOException e) {
			ExceptionUtil.raiseApplicationException(e, logger);
		}
		System.out.println("User " + user.getUsername() + " added");
		logger.info("User " + user.getUsername() + " added");
		return true;
	}

	private static boolean userExists(String username) throws SqlRunnerException {
		return selectAll().stream().filter(u -> u.getUsername().equals(username)).count() == 1;
	}

	public static void edit(User user) throws SqlRunnerException {
		String userFile = Configs.get(Configs.APP_USER_DB_FILE);
		try {
			List<String> lines = Files.readAllLines(Paths.get(userFile));
			int index = 0;
			for (; index < lines.size(); index++) {
				if (lines.get(index).contains(user.getUsername())) {
					lines.set(index, user.getUsername() + ":" + user.getPassword());
					break;
				}
			}
			Files.write(Paths.get(userFile), lines, StandardCharsets.UTF_8, StandardOpenOption.WRITE);
		} catch (IOException e) {
			ExceptionUtil.raiseApplicationException(e, logger);
		}
		logger.info("User " + user.getUsername() + " edited");
	}

	public static void remove(User user) throws SqlRunnerException {
		String userFile = Configs.get(Configs.APP_USER_DB_FILE);
		try {
			List<String> lines = Files.readAllLines(Paths.get(userFile));
			int index = 0;
			for (; index < lines.size(); index++) {
				if (lines.get(index).contains(user.getUsername())) {
					lines.remove(index);
					Files.delete(Paths.get(userFile));
					break;
				}
			}
			Files.write(Paths.get(userFile), lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
		} catch (IOException e) {
			ExceptionUtil.raiseApplicationException(e, logger);
		}
		logger.info("User " + user.getUsername() + " removed");
	}

	public static List<User> selectAll() throws SqlRunnerException {
		String userFile = Configs.get(Configs.APP_USER_DB_FILE);
		try {
			List<User> users = new ArrayList<>();
			Files.readAllLines(Paths.get(userFile)).stream().forEach(s -> {
				String[] data = s.split(":");
				if (Objects.nonNull(data) && data.length >= 3) {
					User user = new User(data[0], data[1]);
					user.setRole(Role.get(data[2]));
					users.add(user);
				}
			});
			return users;
		} catch (IOException e) {
			ExceptionUtil.raiseApplicationException(e, logger);
			return null;
		}
	}

	public static void main(String[] args) throws SqlRunnerException {
		User userAlreadyExists = new User("praveen", "prave3656");
		if (!UserManager.add(userAlreadyExists)) {
			System.out.println("User already exists");
		} else {
			System.out.println("User is added");
		}

		//
		// User user = new User("raj", "raj324");
		// user.setRole(Role.USER);
		//
		// System.out.println("Add user");
		// UserManager.add(user);
		// UserManager.selectAll().forEach(System.out::println);
		// System.out.println();
		//
		// System.out.println("Update user");
		// user.setPassword("rajkumar");
		// UserManager.edit(user);
		// UserManager.selectAll().forEach(System.out::println);
		// System.out.println();
		//
		// System.out.println("Delete user");
		// UserManager.remove(user);
		// UserManager.selectAll().forEach(System.out::println);
		// System.out.println();
	}

}
