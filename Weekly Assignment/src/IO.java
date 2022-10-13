//
// Port IO emulator class. All read/writes are send over TCP/IP to the host
// using a dedicated protocol on port 81.
// Use IO.init(host, port) to modify behaviour
// by Wim Verhoef
// Avans Hogeschool 's-Hertogenbosch
//
// @author  Wim Verhoef
// @version 1.1, December 6 2004
//

import java.io.*;
import java.net.*;

public class IO {
  public static String host = "localhost";
  public static int port = 81;

  /**
   * Initialize.
   * Before calling the emulated IO routines, use one of the init routines
   * to set the host name and port number of the server emulating the HW.
   **/
  public static boolean init() {
    return (init("localhost", 81));
  }

  public static boolean init(String newHost) {
    return (init(newHost, 81));
  }

  public static boolean init(int newPort) {
    return (init("localhost", newPort));
  }

  public static boolean init(String newHost, int newPort) {
    host = newHost;
    port = newPort;
    return startConnectionToServer();
  }

  /**
   * Read a short value (16-bits) from an I/O port.
   * @param  address port address (16-bits)
   * @return short value (16-bits)
   **/
  public static short readShort(short address) {
    return remoteIO( (short) 0, address, (short) 0);
  }

  public static short readShort(int address) {
    return remoteIO( (short) 0, (short) address, (short) 0);
  }

  /**
   * Write a short value (16-bits) to an I/O port.
   * @param    address     port address (16-bits)
   * @param  value     short value (16-bits)
   **/
  public static void writeShort(short address, short value) {
    remoteIO( (short) 1, address, value);
  }

  public static void writeShort(int address, int value) {
    remoteIO( (short) 1, (short) address, (short) value);
  }

  /**
   * Wait a specified time. The routine uses Thread.sleep so below 20 ms
   * timing becomes very erraneous, but works fine for longer times
   *
   * @param lMilliSeconds number of milliseconds to wait
   */

  public static void delay(long lMilliSeconds) {
    if (lMilliSeconds > 0) {
      try {
        Thread.sleep(lMilliSeconds);
      }
      catch (InterruptedException ex) {
      }
    }
  }

//============================= below are the support routines =====================

  private static Socket client = null;
  private static DataOutputStream output = null;
  private static DataInputStream input = null;

  private static void closeConnectionToServer() {
    try {
      if (output != null)
        output.close();
      if (input != null)
        input.close();
      if (client != null)
        client.close();
    }
    catch (IOException e) {
      // no errors plz. e.printStackTrace();
    }
  }

  private static boolean startConnectionToServer() {
    closeConnectionToServer(); // close any pending connection
    try {
      client = new Socket(InetAddress.getByName(host), port);
      client.setSoTimeout(2000); // we expect answers within 2 seconds
      client.setTcpNoDelay(true); //disable nagles algorithm for short packets
      output = new DataOutputStream(new BufferedOutputStream(client.
          getOutputStream()));
      input = new DataInputStream(new BufferedInputStream(client.getInputStream()));
    }
    catch (Exception e) {
      //e.printStackTrace();
      client = null;
      return false;
    }
    return true;
  }

  private static short remoteIO(short IOCode, short address, short value) {
    if (client == null)
      return (short) - 1;

    short readvalue = (short) 0;
    try {
      output.writeShort(IOCode);
      output.writeShort(address);
      output.writeShort(value);
      output.flush();
      readvalue = input.readShort();
    }
    catch (Exception e) {
      // no errors plz. e.printStackTrace();
    }
    return readvalue;
  }
}