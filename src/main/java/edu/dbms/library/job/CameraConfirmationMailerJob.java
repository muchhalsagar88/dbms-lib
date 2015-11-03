package edu.dbms.library.job;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
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
import edu.dbms.library.utils.DateUtils;

public class CameraConfirmationMailerJob implements Runnable {

	@Override
	public void run() {

		LocalDate localDate = DateUtils.getNextFriday();
		LocalDateTime time = localDate.toLocalDateTime(LocalTime.MIDNIGHT);
		time = time.plusHours(8);

		Scheduler scheduler;
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();

	        JobDetail jobDetail = newJob(CameraConfirmationMailer.class).build();

	        Trigger trigger = newTrigger()
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
		time = time.plusHours(8);

		Scheduler scheduler;
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();

	        JobDetail jobDetail = newJob(CameraConfirmationMailer.class).build();

	        Trigger trigger = newTrigger()
	        		//.withIdentity(triggerKey("myTrigger", "myTriggerGroup"))
	        		.withSchedule(simpleSchedule())
	                    .startAt(time.toDate())
	                    .build();

	        scheduler.scheduleJob(jobDetail, trigger);
	        System.out.println("Job scheduled");
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
