package com.gydoc.galleon.test;

import com.gydoc.galleon.IdGenerator;
import com.gydoc.galleon.SpringUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Test
public class Test_IdGenerator {
    
    @Test
    public void testNoDuplicatedId() throws InterruptedException {
        FetchIdThread[] threads = new FetchIdThread[1000];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new FetchIdThread((IdGenerator) SpringUtil.getBean(IdGenerator.S_ID));
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        int count = 0;
        while (true) {
            count++;
            boolean finished = true;
            for (int i = 0; i < threads.length; i++) {
                FetchIdThread thread = threads[i];
                if (!thread.isFinished()) {
                    finished = false;
                    break ;
                }
            }
            if (count > 100) {
                throw new RuntimeException("Test IdGenerator timeout.");
            }
            if (finished) {
                break ;
            } else {
                Thread.sleep(100);
            }
        }
        Map<Long, Boolean> idsMap = new HashMap<Long, Boolean>();
        for (int i = 0; i < threads.length; i++) {
            Thread thread = threads[i];
            idsMap.put(thread.getId(), Boolean.TRUE);
        }
        Assert.assertEquals(threads.length, idsMap.size(), "Get duplicated id from IdGenerator.");
    }
    
    private static class FetchIdThread extends Thread {

        private IdGenerator generator;
        private Long generatedId;
        
        private FetchIdThread(IdGenerator generator) {
            this.generator = generator;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(50);
                generatedId = generator.nextId();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        
        public Long getGeneratedId() {
            return generatedId;
        }

        public boolean isFinished() {
            return getGeneratedId() != null;
        }
        
    }
    
}
