package com.lee.owner.io;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author joseph.li
 * @date 2019-11-27 12:06
 */
public class IOServer {

    private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(4,
            8,
            300, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1024),
            new ThreadPoolExecutor.AbortPolicy());

    // 通道管理器(Selector)
    private static Selector selector;

    public static void main(String[] args) throws IOException {
        // 创建通道管理器(Selector)
        selector = Selector.open();

        // 创建通道ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 将通道设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8989));
        /**
         * 将通道(Channel)注册到通道管理器(Selector)，并为该通道注册selectionKey.OP_ACCEPT事件
         * 注册该事件后，当事件到达的时候，selector.select()会返回，
         * 如果事件没有到达selector.select()会一直阻塞。
         */
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 循环处理
        while (true) {
            // 当注册事件到达时，方法返回，否则该方法会一直阻塞
            selector.select();

            // 获取监听事件
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            // 迭代处理
            while (iterator.hasNext()) {
                // 获取事件
                SelectionKey key = iterator.next();
                // 移除事件，避免重复处理
                iterator.remove();

                // 客户端请求连接事件，接受客户端连接就绪
                if (key.isAcceptable()) {
                    handleAccept(key);
                } else if (key.isReadable()) {// 监听到读事件，对读事件进行处理
                    handleRead(key);
                }
            }


        }
    }

    /**
     * 处理客户端连接成功事件
     */
    private static void handleAccept(SelectionKey key) throws IOException {
        // 获取客户端连接通道
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = server.accept();
        socketChannel.configureBlocking(false);

        // 给通道设置读事件，客户端监听到读事件后，进行读取操作
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    /**
     * 监听到读事件，读取客户端发送过来的消息
     */
    private static void handleRead(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();

        // 从通道读取数据到缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(128);
        channel.read(buffer);

        // 输出客户端发送过来的消息
        byte[] data = buffer.array();
        String msg = new String(data).trim();
        System.out.println("server receive msg from client：" + msg);

        THREAD_POOL.execute(() -> {
            try {
                channel.write(ByteBuffer.wrap("Hello Client1".getBytes()));
                TimeUnit.SECONDS.sleep(2);
                channel.write(ByteBuffer.wrap("Hello Client2".getBytes()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
