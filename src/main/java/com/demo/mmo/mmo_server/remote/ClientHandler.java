package com.demo.mmo.mmo_server.remote;

import org.apache.mina.core.session.IoSession;

import com.demo.mmo.mmo_server.game.navigation.ActionNavigation;
import com.demo.mmo.mmo_server.game.navigation.ActionSupport;
import com.demo.mmo.mmo_server.game.navigation.ResponseNavigation;
import com.demo.mmo.mmo_server.protocals.base.Protocal.Request;
import com.demo.mmo.mmo_server.protocals.base.Protocal.Response;
import com.demo.mmo.mmo_server.server.IoHandlerProxy;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * 消息处理器
 * 
 */
public class ClientHandler extends IoHandlerProxy{

	// 当一个客户端连结进入时
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println(session.getRemoteAddress() + "打开连接");
	}

	// 当一个 新的session创建时
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println(session.getRemoteAddress() + "创建新的 会话");
	}

	// 当一个客户端关闭时
	@Override
	public void sessionClosed(IoSession session) {
		if (null != session) {
			// Integer roleInteger = (Integer) session.getAttribute("roleId");
			// Role role = new Role();
			// if (roleInteger != 0) {
			// role = RoleCache.getRoleById(roleInteger);
			// }
			// if (role != null) {
			// System.out.println("玩家正常断开数据处理，用户ID：" + role.getRoleId());
			// session.close(true);
			// SessionCloseHandler.manipulate(role);
			// }
		}
	}

	// 异常捕获
	@Override
	public void exceptionCaught(IoSession session, Throwable e) throws Exception {

		if (e.getMessage().equals("远程主机强迫关闭了一个现有的连接。")) {
			System.out.println(e.getMessage());
			this.sessionClosed(session);
		} else {
			System.err.println("程序业务逻辑出现异常,已处理该账户信息,并强制下线！" + (Integer) session.getAttribute("roleId"));
			e.printStackTrace();
			this.sessionClosed(session);
		}
	}

	// 当客户端发送的消息到达时
	@Override
	public void messageReceived(IoSession session, Object messageObj) throws Exception {
		Request message = (Request) messageObj;

		if (null == message) {
			System.out.println("ERR_MESSAGE_REC");
			return;
		}
		ActionSupport action = ActionNavigation.getAction(message.getProtocal());
		Integer roleId = (Integer) session.getAttribute("roleId");

		System.out.println("服务器接收用户：" + roleId + "的消息，请求指令号为:" + message.getProtocal());

		try {
			action.execute(message.getData(), session);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("伪造的协议ID：" + message.getProtocal());
			// 判断是否属于无效的协议号 TODO 处理方式可能需要变更
			// if(e.getClass().equals(NumberFakeException.class))
			// {
			 session.close(true);
			// }
		}
	}

	// 当发送消息成功时调用这个方法，注意这里的措辞，发送成功之后，也就是说发送消息是不能用这个方法的。

	@Override
	public void messageSent(IoSession session, Object message) {
		Integer roleId = (Integer) session.getAttribute("roleId");
		Response msg = (Response) message;

		try {
			PrintToClientMessage.print(roleId, msg.getProtocal(), ResponseNavigation.getAttribute(msg.getProtocal(), msg.getData()));
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
