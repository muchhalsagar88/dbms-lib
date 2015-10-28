package edu.dbms.library.job;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.repeatHourlyForTotalCount;
import static org.quartz.TriggerBuilder.newTrigger;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import edu.dbms.library.runner.CameraReservationStatusUpdater;
import edu.dbms.library.utils.DateUtils;

public class CameraReservationStatusUpdaterJob implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String []args) {
		
		LocalDate localDate = DateUtils.getNextFriday();
		LocalDateTime time = localDate.toLocalDateTime(LocalTime.MIDNIGHT);
		time = time.plusHours(10);
		
		Scheduler scheduler;
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();

	        JobDetail jobDetail = newJob(CameraReservationStatusUpdater.class).build();

	        Trigger trigger = newTrigger()
	        		//.withIdentity(triggerKey("myTrigger", "myTriggerGroup"))	
	        		/*.withSchedule(simpleSchedule()
	                        .withIntervalInHours(1)
	        				//.withIntervalInSeconds(1)
	                        .repeatHourlyForTotalCount(3))*/
	        			.withSchedule(repeatHourlyForTotalCount(3))
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
