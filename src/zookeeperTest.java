import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * @author wf
 * @date 2020/4/10 18:00
 */
public class zookeeperTest {
    public static void main(String[] args) {
        ZooKeeper zooKeeper = null;
        try{
            //创建一个计数器对象
            CountDownLatch countDownLatch=new CountDownLatch(1);
            System.out.println("wf");
            //第一个参数是服务器ip和端口号，第二个参数是客户端与服务器的会话超时时间单位ms，第三个参数是监视器对象
            zooKeeper=new ZooKeeper("192.168.8.118:2181", 5000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if(event.getState()==Event.KeeperState.SyncConnected){
                        System.out.println("连接创建成功");
                        //通知主线程解除阻塞
                        countDownLatch.countDown();
                    }
                }
            });
            //主线程阻塞，等待连接对象的创建成功
            countDownLatch.await();
            System.out.println("会话编号"+zooKeeper.getSessionId());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(zooKeeper!=null) {
                try {
                    zooKeeper.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

