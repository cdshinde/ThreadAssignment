package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

	private static final Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) throws InterruptedException {

		ExecutorService executorService = Executors.newFixedThreadPool(Constants.NUMBER_OF_PHILOSOPHERS);
		Philosopher[] philosophers = null;
		
		try{
			
			philosophers = new Philosopher[Constants.NUMBER_OF_PHILOSOPHERS];
			ChopStick[] chopSticks = new ChopStick[Constants.NUMBER_OF_PHILOSOPHERS];
			
			for(int i=0;i<Constants.NUMBER_OF_CHOPSTICKS;i++){
				chopSticks[i] = new ChopStick(i);
			}
			
			for(int i=0;i<Constants.NUMBER_OF_PHILOSOPHERS;i++){
				philosophers[i] = new Philosopher(i, chopSticks[i], chopSticks[(i+1) % Constants.NUMBER_OF_PHILOSOPHERS]);
				executorService.execute(philosophers[i]);
			}
			
			Thread.sleep(Constants.SIMULATION_RUNNING_TIME);
			
			for(Philosopher philosopher : philosophers){
				philosopher.setFull(true);
			}		
		}finally{
			
			executorService.shutdown();
			
			while(!executorService.isTerminated()){
				Thread.sleep(1000);
			}
			
			for(Philosopher philosopher : philosophers ){
				logger.info("{} eat #{}", philosopher, philosopher.getEatingCounter());
			}
			
		}
		
	}
}
