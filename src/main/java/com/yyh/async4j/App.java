package com.yyh.async4j;

import com.yyh.async4j.impl.ActionImpl;
import com.yyh.async4j.impl.ConsumerImpl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        AbsConsumer consumer = new ConsumerImpl();
        for (int i=0;i<100;i++) {
        	consumer.offer(new ActionImpl());
        }
        
    }
}
