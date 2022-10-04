package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChopStick {

	private static final Logger logger = LoggerFactory.getLogger(ChopStick.class);

	private Lock lock;
	private int id;
	
	public ChopStick(int id){
		this.id = id;
		this.lock = new ReentrantLock();
	}
	
	public boolean pickUp(Philosopher philosopher, State state) throws InterruptedException{
	
		if( this.lock.tryLock(10, TimeUnit.MILLISECONDS)){
			logger.info("{} picked up {} {}", philosopher, state, this);
			return true;
		}
		
		return false;
	}
	
	public void putDown(Philosopher philosopher, State state) {
		this.lock.unlock();
		logger.info("{} put down {} {}", philosopher, state, this);
	}
	
	@Override
	public String toString() {
		return "Chopstick-"+this.id;
	}
}
