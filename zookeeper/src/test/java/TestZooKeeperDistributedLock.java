import com.bwing.HighZooKeeperDistributedLock;
import com.bwing.SimpleZooKeeperDistributedLock;
import com.bwing.ZooKeeperDistributedLock;

public class  TestZooKeeperDistributedLock {

    private  static int  count = 100;
    public static void main(String[] args) {



//        for(int i =0;i < 100;i++){
//
//            new Thread( new Runnable() {
//                @Override
//                public void run() {
//                    ZooKeeperDistributedLock lock = new SimpleZooKeeperDistributedLock();
//                    lock.getLock();
//
//                    count--;
//                    try {
//                        Thread.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//
//                    System.out.println(count);
//                    lock.releaseLock();
//                }
//            },"线程" + i).start();
//        }


        for(int i =0;i < 100;i++){

            new Thread( new Runnable() {
                @Override
                public void run() {
                    ZooKeeperDistributedLock lock = new HighZooKeeperDistributedLock();
                    lock.getLock();

                    count--;
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    System.out.println(count);
                    lock.releaseLock();
                }
            },"线程" + i).start();
        }

    }
}
