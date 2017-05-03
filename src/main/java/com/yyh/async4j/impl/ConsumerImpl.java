package com.yyh.async4j.impl;

import java.util.concurrent.BlockingQueue;

import com.yyh.async4j.AbsConsumer;
import com.yyh.async4j.IAction;

public class ConsumerImpl extends AbsConsumer {

	public ConsumerImpl() {
		super();
	}
	
	public ConsumerImpl(int initConcurrency, int initialCapacity) {
		super(initConcurrency, initialCapacity);
	}

	@Override
	public void consume(BlockingQueue<IAction> paramBlockingQueue) {
		// TODO Auto-generated method stub
		if (null!=paramBlockingQueue) {
			try {
				paramBlockingQueue.take().act();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void report(IAction action) {
		// TODO Auto-generated method stub
		

	}

}
