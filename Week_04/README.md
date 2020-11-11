# 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 ## 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
    - useJoin，useSleep，useCountDownLatch，useCyclicBarrier 都是通过直接阻塞  让线程对常量进行设置后，获取其值
        -  int x = 0;   play(){ x = 2};
    - useFutureTask，useFuture，useCompletableFuture 都是直接返回值的 （内部也是阻塞跟上面的好像一样）
        -  int x = Task(){return 2;}
   
```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>compile</scope>
</dependency>
```
```java
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class TreadTest {

    private static int ResultNum;
    

    @Before
    public void start() {
        System.out.println("ResultNum开始值:"+ResultNum);
    }
    @After
    public void finish() {
        System.out.println("ResultNum结束值:"+ResultNum);
    }

    //使用  Join 阻塞主线程 获取返回值
    @Test
    public void useJoin() throws InterruptedException {
       Thread thread = new Thread(() -> {
           ResultNum = Result.fibo(36);
       });
       thread.start();
       //阻塞线程 等待线程运行完再继续运行
       thread.join();
    }

     //使用  sleep 阻塞主线程 获取返回值
    @Test
    public void useSleep() throws InterruptedException {
        Thread thread = new Thread(() -> {
            ResultNum = Result.fibo(36);
        });
        thread.start();
        //阻塞线程
        Thread.sleep(1000) ;
    }



    // 使用 CountDownLatch  获取返回值
    @Test
    public void useCountDownLatch() throws InterruptedException {
       
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ResultNum =  Result.fibo(36);
                countDownLatch.countDown();
            }
        }).start();
        //集合点
        countDownLatch.await();
    }

    /**
    使用  CyclicBarrier 回调获取返回值 
        - 为什么在@Test中需要sleep才能获取掉回调，而main函数中执行不用sleep,群里老是让我多试几次，
          我循环20次main中的方法还是都不需要sleep
        主线程执行完毕后 子线程还没有完成就会放弃回调吗
    */
    @Test
    public void useCyclicBarrier() throws InterruptedException, BrokenBarrierException {
       
        CyclicBarrier cyclicBarrier = new CyclicBarrier(1, new Runnable() {
            @Override
            public void run() {
                System.out.println("回调>>"+Thread.currentThread().getName());
                System.out.println("回调>>线程组执行结束:"+ResultNum);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ResultNum = Result.fibo(36);
                    System.out.println("线程组任务结束，其他任务继续");
                    cyclicBarrier.await();   // 注意跟CountDownLatch不同，这里在子线程await
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        // 为什么 在@Test中不睡一睡 回调就不成功呢  main函数中不存在这问题
        Thread.sleep(1000);
        System.out.println("==>各个子线程执行结束。。。。");
        System.out.println("==>主线程执行结束。。。。");
    }

   // 使用 FutureTask （Callable回调） 获取返回值
    @Test
    public void useFutureTask() throws ExecutionException, InterruptedException {
        FutureTask futureTask = new FutureTask(() -> {
            return Result.fibo(36);
        });
        Thread thread = new Thread(futureTask);
        thread.start();
        ResultNum = Integer.parseInt(futureTask.get().toString());
    }


    // 使用 线程池  更上面一样只是使用了线程池 获取返回值
    @Test
    public void useFuture() throws ExecutionException, InterruptedException {

        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Integer> result = (Future<Integer>) executor.submit(() ->{
            return Result.fibo(36);
        });
        executor.shutdown();
        ResultNum =  result.get();
    }


   // 使用 CompletableFuture 异步回调  更上面一样只是使用了线程池 获取返回值
    @Test
    public void useCompletableFuture() throws ExecutionException, InterruptedException {
        //supplyAsync可以支持返回值
        CompletableFuture.supplyAsync(()->{return Result.fibo(36);}).thenApplyAsync(v -> ResultNum = v).join();
    }

}

class Result {

    public static int fibo(int a) {
        if ( a < 2) {
            return 1;
        }
        return fibo(a-1) + fibo(a-2);
    }
}
```