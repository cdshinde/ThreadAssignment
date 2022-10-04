package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class Philosopher implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(Philosopher.class);

	private int id;
	private ChopStick leftChopStick;
	private ChopStick rightChopStick;
	private volatile boolean isFull = false;
	private Random random;
	private int eatingCounter;
	
	public Philosopher(int id, ChopStick leftChopStick, ChopStick rightChopStick){
		this.id = id;
		this.leftChopStick = leftChopStick;
		this.rightChopStick = rightChopStick;
		this.random = new Random();
	}
	
	@Override
	public void run() {
		
		try{
			
			while( !isFull ){
				
				think();
				
				if( leftChopStick.pickUp(this, State.LEFT) ){
					if( rightChopStick.pickUp(this, State.RIGHT)){
						eat();
						rightChopStick.putDown(this, State.RIGHT);
					}
					
					leftChopStick.putDown(this, State.LEFT);
				}	
			}
		}catch (InterruptedException ie){
			logger.error("Throwing InterruptedException");
			ie.printStackTrace();
			Thread.currentThread().interrupt();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void think() throws InterruptedException {
		logger.info("{} is thinking...", this);
		Thread.sleep(this.random.nextInt(1000));
	}
	
	private void eat() throws InterruptedException {
		logger.info("{} is eating...", this);
		this.eatingCounter++;
		Thread.sleep(this.random.nextInt(1000));
	}
	
	public int getEatingCounter(){
		return this.eatingCounter;
	}
	
	public void setFull(boolean isFull){
		this.isFull = isFull;
	}
	
	@Override
	public String toString() {
		return "Philosopher-"+this.id;
	}
}
