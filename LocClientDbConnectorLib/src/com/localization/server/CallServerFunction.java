/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.localization.server;

import com.localization.other.ApplicationConfiguration;
import com.localization.other.ObjectMapping;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import locationaware.protocol.Constant;

/**
 *
 * @author leobin
 */
public class CallServerFunction {

    public static Object callServerObjectFunction(String objectId, int objectType, int functionName, Object parameter) {
        ObjectOutputStream out = null;
        try {
            Socket socket = new Socket(ApplicationConfiguration.load().serverHost, ApplicationConfiguration.load().callServerObjectFunctionPort);
            out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            if (objectType == ObjectMapping.LOCATION_OBJECT && functionName == ObjectMapping.LOCATION_GET_ALGORITHMS) {
                System.out.println("Call get algorithms to server");
            }

            out.writeInt(Constant.CALL_SERVER_OBJECT);


            out.writeObject(objectId);
            out.writeInt(objectType);
            out.writeInt(functionName);
            out.writeObject(parameter);
            Object readObject = in.readObject();
            out.close();
            in.close();
            socket.close();
            return readObject;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CallServerFunction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CallServerFunction.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(CallServerFunction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static Object callServerFunction(int objectType, int function, Object parameter, Object parameter2) {
        ObjectOutputStream out = null;
        try {
            Socket socket = new Socket(ApplicationConfiguration.load().serverHost, ApplicationConfiguration.load().callServerFunctionPort);
            out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            out.writeInt(Constant.CALL_SERVER_FUNCTION);

            if (objectType == ObjectMapping.LOCATION_OBJECT && function == ObjectMapping.LOCATION_GET_ALGORITHMS) {
                System.out.println("Call get algorithms to server");
            }

            out.writeInt(objectType);
            out.writeInt(function);
            out.writeObject(parameter);
            out.writeObject(parameter2);
            Object readObject = in.readObject();
            out.close();
            in.close();
            socket.close();
            return readObject;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CallServerFunction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CallServerFunction.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(CallServerFunction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
}
