package locationaware.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.localization.server.CallServerFunction;

public class ServerHandlerClientCallFunction implements Runnable{

	private Socket client;
	
	public ServerHandlerClientCallFunction(Socket accept) {
		this.client = accept;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		ObjectInputStream in = null;
		ObjectOutputStream out = null;

		try {
			out = new ObjectOutputStream(this.client.getOutputStream());
			in = new ObjectInputStream(this.client.getInputStream());
			
			int objectType = in.readInt();
			int function = in.readInt();
			Object parameter = in.readObject();
			Object parameter2 = in.readObject();
			
			Object writeObject = CallServerFunction.callServerFunction(objectType, function, parameter, parameter2);
			
			out.writeObject(writeObject);
			
			in.close();
			out.close();
			this.client.close();
			return;
		} catch (IOException e) {
			System.out.println("in or out failed");
			return;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
