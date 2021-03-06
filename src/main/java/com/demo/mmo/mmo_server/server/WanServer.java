package com.demo.mmo.mmo_server.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.http.HttpServerCodec;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class WanServer {
	public static enum WanServerType {
		TCP, UDP;
	}

	public static void startIOServer(IoHandlerProxy handler, InetSocketAddress inetSocketAddress) {
		startServer(new ProtocolCodecFilter(new MessageCodecFactory(Charset.forName(ServerConfig.getCharSet()))),
				handler, inetSocketAddress, WanServerType.UDP);
	}

	public static void startHttpServer(IoHandlerProxy handler, InetSocketAddress inetSocketAddress) {
		startServer(new HttpServerCodec(), handler, inetSocketAddress);
	}
	
	public static void startServer(IoFilter ioFilter, IoHandlerProxy handler, InetSocketAddress inetSocketAddress,
			WanServerType type) {
		switch (type) {
		case TCP:
			startServer(ioFilter, handler, inetSocketAddress);
			break;
		case UDP:
			UDPServer(ioFilter, handler, inetSocketAddress);
			break;
		}
	}
	
	private static void UDPServer(IoFilter ioFilter, IoHandlerProxy handler, InetSocketAddress inetSocketAddress) {
		NioDatagramAcceptor ioAcceptor = new NioDatagramAcceptor();

		DatagramSessionConfig dsc = ioAcceptor.getSessionConfig();
		dsc.setReadBufferSize(4096);
		dsc.setSendBufferSize(1024);
		dsc.setReceiveBufferSize(1024);
		dsc.setReuseAddress(true);
		dsc.setIdleTime(IdleStatus.BOTH_IDLE, 60);

		DefaultIoFilterChainBuilder chain = ioAcceptor.getFilterChain();

		chain.addLast("codec", ioFilter);
		chain.addLast("threadpool", new ExecutorFilter(Executors.newCachedThreadPool()));

		ioAcceptor.setHandler(handler);
		try {
			ioAcceptor.bind(inetSocketAddress);
			System.out.println("服务启动成功");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	

	public static void startServer(IoFilter ioFilter, IoHandlerProxy handler, InetSocketAddress inetSocketAddress) {
		NioSocketAcceptor ioAcceptor = new NioSocketAcceptor();

		ioAcceptor.getSessionConfig().setReadBufferSize(ServerConfig.getBufferSize());

		ioAcceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, ServerConfig.getIdleTime());

		ioAcceptor.getSessionConfig().setSendBufferSize(ServerConfig.getBufferSize());
		ioAcceptor.getSessionConfig().setTcpNoDelay(false);

		DefaultIoFilterChainBuilder chain = ioAcceptor.getFilterChain();

		chain.addLast("codec", ioFilter);

		chain.addLast("threadpool", new ExecutorFilter(Executors.newCachedThreadPool()));

		ioAcceptor.setHandler(handler);
		try {
			ioAcceptor.bind(inetSocketAddress);
			System.out.println("服务启动成功");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
