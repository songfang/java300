package org.xxhh.java300.metric;
 
import java.util.Random;
import java.util.concurrent.TimeUnit;
 
import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.Timer;
import com.yammer.metrics.core.TimerContext;
import com.yammer.metrics.reporting.ConsoleReporter;
 
/**
 * TODO
 * @author scutshuxue.chenxf
 */
public class TestTimers {
        private static Timer timer = Metrics.newTimer(TestTimers.class, "responses", TimeUnit.MILLISECONDS,TimeUnit.SECONDS);
        /**
         * TODO
         * @author scutshuxue.chenxf
         * @param args
         * void
         * @throws InterruptedException
         */
        public static void main(String[] args) throws InterruptedException {
               // TODOAuto-generated method stub
               ConsoleReporter.enable(2,TimeUnit.SECONDS);
               Random rn = new Random();
               timer.time();
               System.out.println();
               while(true){
                       TimerContext context = timer.time();
                      
                       Thread.sleep(rn.nextInt(1000));
                       context.stop();
               }
        }
 
}