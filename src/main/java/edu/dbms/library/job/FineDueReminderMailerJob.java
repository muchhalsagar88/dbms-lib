package edu.dbms.library.job;

import static org.quartz.JobBuilder.newJob;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.dbms.library.runner.FineDueUpdateMailer;
import edu.dbms.library.utils.DateUtils;

public class FineDueReminderMailerJob implements Runnable {

final static Logger logger = LoggerFactory.getLogger(FineDueReminderMailerJob.class);

	@Override
	public void run() {

		LocalDate localDate = DateUtils.getNextFriday();
		LocalDateTime time = localDate.toLocalDateTime(LocalTime.MIDNIGHT);

		Scheduler scheduler;
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();

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

	public static void main(String []args) {

		LocalDate localDate = DateUtils.getNextFriday();
		LocalDateTime time = localDate.toLocalDateTime(LocalTime.MIDNIGHT);

		Scheduler scheduler;
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();

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
