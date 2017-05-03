package com.yyh.async4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public abstract class AbsConsumer {

	protected final  Logger log = LoggerFactory.getLogger(this.getClass());
	protected int concurrency;
	protected List<BlockingQueue<IAction>> queues = new ArrayList();
	protected ExecutorService executor;

	public AbsConsumer() {
		this(16,16);
	}
	
	public AbsConsumer(int initConcurrency, int initialCapacity) {
		
		//线程数是 2的n次方,源于HashMap 中的设计
		// 使 indexFor 函数中 & 的作用，和 去模% 运算的结果一致，提高效率
        concurrency = 1;
        while (concurrency < initConcurrency) {
        	concurrency <<= 1;
        }
		
		//this.concurrency = initConcurrency;
		this.executor = Executors.newFixedThreadPool(concurrency);
		for (int i = 0; i < concurrency; i++) {
			this.queues.add(new ArrayBlockingQueue(initialCapacity));
		}
		for (final BlockingQueue<IAction> queue : this.queues) {
			this.executor.submit(new Runnable() {
				public void run() {
					for (;;) {
						AbsConsumer.this.consume(queue);
					}
				}
			});
		}
	}

	public boolean offer(IAction a) {
		// 查找放到在哪个 queues 中 ，函数取自 HashMap的实现中，减少碰撞，使每个队列的数量尽量均匀
		int index = indexFor(hash(a),this.queues.size());
		//是否成功，如果true，表明成功；false 表明队列已经满了，此时需要汇报
		boolean isSuccess = ((BlockingQueue) this.queues.get(index)).offer(a);
		//
		if (!isSuccess) {
			this.report(a);
		}
		
		return isSuccess;
	}

	//hash function copy from HashMap 
    private int hash(Object k) {
        int h = 0;       
        h ^= k.hashCode();
        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    //indexFor function copy from HashMap 
    /**
     * Returns index for hash code h.
     */
    private int indexFor(int h, int length) {
        return h & (length-1);
    }
	
	public abstract void consume(BlockingQueue<IAction> paramBlockingQueue);
	public abstract void report(IAction action);

}
