package edu.dbms.library.job;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.repeatHourlyForTotalCount;
import static org.quartz.SimpleScheduleBuilder.repeatHourlyForever;
import static org.quartz.TriggerBuilder.newTrigger;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import edu.dbms.library.runner.CameraConfirmationMailer;
import edu.dbms.library.runner.CameraReservationStatusUpdater;
import edu.dbms.library.runner.DueDateReminders;
import edu.dbms.library.runner.FineDueUpdateMailer;
import edu.dbms.library.utils.DateUtils;

public class QuartzJob implements Runnable {

	@Override
	public void run() {

		Scheduler scheduler;

		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();

			startCameraConfimrationMailerJob(scheduler);
			startCameraResStatusUpdateMailerJob(scheduler);
			startDueDateMailerJob(scheduler);
			startFineDueReminderMailerJob(scheduler);

		} catch (SchedulerException e) {
			e.printStackTrace();
		}



	}

	private void startCameraConfimrationMailerJob(Scheduler scheduler) {

		LocalDate localDate = DateUtils.getNextFriday();
		LocalDateTime time = localDate.toLocalDateTime(LocalTime.MIDNIGHT);
		time = time.plusHours(8);

		try {
			JobDetail jobDetail = newJob(CameraConfirmationMailer.class).build();

	        Trigger trigger = newTrigger()
	        		.withSchedule(repeatHourlyForever(24*7))
	        		.startAt(time.toDate())
	                .build();

	        scheduler.scheduleJob(jobDetail, trigger);

		} catch (SchedulerException e) {

			e.printStackTrace();
		}
	}

	private void startCameraResStatusUpdateMailerJob(Scheduler scheduler) {

		LocalDate localDate = DateUtils.getNextFriday();
		LocalDateTime time = localDate.toLocalDateTime(LocalTime.MIDNIGHT);
		time = time.plusHours(10);

		try {
			JobDetail jobDetail = newJob(CameraReservationStatusUpdater.class).build();

	        Trigger trigger = newTrigger()
	        			.withSchedule(repeatHourlyForTotalCount(3))
	                    .startAt(time.toDate())
	                    .build();

	        scheduler.scheduleJob(jobDetail, trigger);

		} catch (SchedulerException e) {

			e.printStackTrace();
		}
	}

	private void startDueDateMailerJob(Scheduler scheduler) {

		LocalDate localDate = DateUtils.getNextFriday();
		LocalDateTime time = localDate.toLocalDateTime(LocalTime.MIDNIGHT);

		try {
			JobDetail jobDetail = newJob(DueDateReminders.class).build();

	        Trigger trigger = newTrigger()
	        		.withSchedule(repeatHourlyForever(24))
	        		.startAt(time.toDate())
	                .build();

	        scheduler.scheduleJob(jobDetail, trigger);

		} catch (SchedulerException e) {

			e.printStackTrace();
		}
	}

	private void startFineDueReminderMailerJob(Scheduler scheduler) {

		LocalDate localDate = DateUtils.getNextFriday();
		LocalDateTime time = localDate.toLocalDateTime(LocalTime.MIDNIGHT);

		try {
			JobDetail jobDetail = newJob(FineDueUpdateMailer.class).build();

	        Trigger trigger = newTrigger()
	        		.withSchedule(repeatHourlyForever(24))
	        		.startAt(time.toDate())
	                .build();

	        scheduler.scheduleJob(jobDetail, trigger);

		} catch (SchedulerException e) {

			e.printStackTrace();
		}
	}

}
