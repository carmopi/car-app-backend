package com.carmen.app.timer;



import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.TimerService;
import javax.ejb.Singleton;

import org.apache.log4j.Logger;

import com.carmen.app.control.CarService;



@Startup
@Singleton

public class SoftDeleteTimer {

	private final static Logger log = Logger.getLogger(SoftDeleteTimer.class);
	
	@EJB
	private CarService carService;
	
	
	@Resource
	private TimerService timerService;
	
	@PostConstruct
	private void init() {
		timerService.createTimer(1000, 60000, "IntervalTimer_DeleteCars");
	}
	
	@Timeout
	public final void deleteCars() {
		log.info("Starting with the deletion of the cars...");
		carService.deleteMarkedCars();
		log.info("Completed");
		
	}
}
