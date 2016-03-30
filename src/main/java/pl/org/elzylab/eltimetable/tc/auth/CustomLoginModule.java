/**
 * =====================================================================
 * License
 * =====================================================================
 * Copyright (C) ${date} elZyLab
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 * 
 * @author Piotr Zych
 */

package pl.org.elzylab.eltimetable.tc.auth;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import pl.org.elzylab.eltimetable.beans.user.UserDTO;
import pl.org.elzylab.eltimetable.beans.user.Users;

/**
 * ====================================================================
 * Description
 * ==================================================================== Class
 * ovverides login module and keeps login data (such as role, groups, users
 * principal
 * 
 *
 */
public class CustomLoginModule implements LoginModule {

	private CallbackHandler handler;
	private Subject subject;
	private Principal userPrincipal;
	private Principal rolePrincipal;
	private List<String> userGroups;

	// Configurable option
	private boolean debug = false;

	// Logger used to output debug information
	private static final Logger logger = Logger
		.getLogger(CustomLoginModule.class.getName());

	// User credentials
	private String username = null;
	private String password = null;

	private boolean isAuthenticated = false;
	private boolean commitSucceeded = false;

	/**
	 * Constructor
	 */
	public CustomLoginModule() {
		super();
	}

	/**
	 * Initializer
	 *
	 * @param subject
	 * @param callbackHandler
	 * @param sharedState
	 * @param options
	 */
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler,
		Map<String, ?> sharedState, Map<String, ?> options) {

		// Store the handler
		this.handler = callbackHandler;

		// Subject reference holds the principals
		this.subject = subject;

		// Setup a logging class / state
		if ("true".equalsIgnoreCase((String) options.get("debug"))) {
			ConsoleHandler consoleHandler = new ConsoleHandler();
			logger.addHandler(consoleHandler);
			debug = true;
		}
	}

	/**
	 * Login usert into application
	 * 
	 * @return if action successed
	 * @throws LoginException
	 */
	@Override
	public boolean login() throws LoginException {

		// If no handler is specified throw a error
		if (handler == null) {
			throw new LoginException("Error: no CallbackHandler available to "
				+ "recieve  authentication information from the user");
		}

		// Declare the callbacks based on the JAAS spec
		Callback[] callbacks = new Callback[2];
		callbacks[0] = new NameCallback("login");
		callbacks[1] = new PasswordCallback("password", true);

		try {

			// Handle the callback and recieve the sent inforamation
			handler.handle(callbacks);
			username = ((NameCallback) callbacks[0]).getName();
			password =
				String.valueOf(((PasswordCallback) callbacks[1]).getPassword());

			// Debug the username / password
			if (debug) {
				logger.log(Level.INFO, "Username: {0}", username);
				logger.log(Level.INFO, "Password: {0}", password);
			}

			// We should never allow empty strings to be passed
			if (username == null || username.isEmpty() || password == null
				|| password.isEmpty()) {
				throw new LoginException("Data specified had null values");
			}

			// Validate against our database or any other options (this check is
			// a example only)
			if (username != null && password != null) {

				UserDTO user = Users.getUser(username, password);

				if (user == null) {
					return false;
				}

				userPrincipal = new UserData(user);

				// Assign the user roles
				userGroups = this.getRoles();
				isAuthenticated = true;

				return true;
			}

			throw new LoginException("Authentication failed");

		} catch (IOException | UnsupportedCallbackException e) {
			throw new LoginException(e.getMessage());
		}

	}

	/**
	 * Adds the username / roles to the principal
	 *
	 * @return @throws LoginException
	 */
	@Override
	public boolean commit() throws LoginException {

		if (!isAuthenticated) {
			return false;
		} else {
			subject.getPrincipals().add(userPrincipal);

			if (userGroups != null && userGroups.size() > 0) {
				for (String groupName : userGroups) {
					rolePrincipal = new Role(groupName);
					subject.getPrincipals().add(rolePrincipal);
				}
			}

			commitSucceeded = true;

			return true;
		}
	}

	/**
	 * Terminates the logged in session on error
	 *
	 * @return @throws LoginException
	 */
	@Override
	public boolean abort() throws LoginException {
		if (!isAuthenticated) {
			return false;
		} else if (isAuthenticated && !commitSucceeded) {
			isAuthenticated = false;
			username = null;
			password = null;
			userPrincipal = null;
		} else {
			logout();
		}
		return true;
	}

	/**
	 * Logs the user out
	 *
	 * @return @throws LoginException
	 */
	@Override
	public boolean logout() throws LoginException {
		isAuthenticated = false;
		isAuthenticated = commitSucceeded;
		subject.getPrincipals().clear();
		return true;
	}

	/**
	 * Returns list of roles assigned to authenticated user.
	 *
	 * @return
	 */
	private List<String> getRoles() {

		List<String> roleList = new ArrayList<>();
		roleList.add("admin");

		return roleList;
	}

}