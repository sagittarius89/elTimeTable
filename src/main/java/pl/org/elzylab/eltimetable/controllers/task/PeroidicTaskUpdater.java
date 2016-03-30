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

package pl.org.elzylab.eltimetable.controllers.task;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import pl.org.elzylab.eltimetable.beans.task.Tasks;

/**
 * ====================================================================
 * Description
 * ==================================================================== Servlet
 * - context listener is lunched periodically to change status of done tasks to
 * archived. In other words servlet move tasks marked as done to archive.
 *
 */
public class PeroidicTaskUpdater implements ServletContextListener {

	private Timer mTimer;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		mTimer.purge();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		mTimer = new Timer(true);

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		if (cal.get(Calendar.HOUR_OF_DAY) >= 3)
			cal.add(Calendar.HOUR_OF_DAY, 24);

		cal.set(Calendar.HOUR_OF_DAY, 3);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		mTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				Tasks.cleanupStatuses();
			}

		}, cal.getTime(), 86400000l);
	}

}
