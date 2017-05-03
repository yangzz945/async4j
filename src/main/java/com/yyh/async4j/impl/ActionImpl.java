package com.yyh.async4j.impl;

import java.util.concurrent.atomic.AtomicLong;

import com.yyh.async4j.IAction;

public class ActionImpl implements IAction {

	private static AtomicLong aLong = new AtomicLong();
	public ActionImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void act() throws Exception {
		System.out.println("act:"+aLong.incrementAndGet());
	}
}
