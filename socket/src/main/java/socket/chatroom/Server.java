package socket.chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import socket.tcp.SocketUtils;

public class Server {
	private List<MyChannel> container = new ArrayList<MyChannel>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Server().start();
	}

	public void start() {
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(9999);
			while (true) {
				Socket sock = socket.accept();
				MyChannel channel = new MyChannel(sock);
				container.add(channel);
				new Thread(channel).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			SocketUtils.closeSock(socket);
		}

	}

	private class MyChannel implements Runnable {
		private DataInputStream in;
		private DataOutputStream out;
		boolean isRunning = true;

		public MyChannel(Socket socket) {
			try {
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				SocketUtils.closeSock(in, out);
				container.remove(this);
				isRunning = false;
			}
		}

		public void send(String msg) {
			if (null == msg || "".equals(msg)) {
				return;
			}
			try {
				out.writeUTF(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				isRunning = false;
				container.remove(this);
				SocketUtils.closeSock(out);
			}
		}

		public String receive() {
			String msg = "";
			try {
				msg = in.readUTF();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				isRunning = false;
				container.remove(this);
				SocketUtils.closeSock(in);
			}
			return msg;
		}

		public void sendOthers(String msg)
		{
			for (MyChannel obj : container)
			{
				if (this == obj)
				{
					continue;
				}
				obj.send(msg);
			}
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (isRunning) {
			//	send(receive());
				sendOthers(receive());
			}
		}

	}

}
