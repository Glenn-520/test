package com.seatig.common;

import net.corda.client.rpc.CordaRPCClient;
import net.corda.client.rpc.CordaRPCConnection;
import net.corda.core.utilities.NetworkHostAndPort;

import java.util.HashMap;
import java.util.Map;

/**
 *   *包装节点RPC代理。
 *   *
 *   * @param host我们要连接的节点的主机。
 *   * @param rpcPort我们要连接的节点的RPC端口。
 *   * @param username登录RPC客户端的用户名。
 *   * @param password登录RPC客户端的密码。
 *   * @property proxy RPC代理。
 *  
 */
public class NodeRPCConnection {


    private  static class Connection{
        private static Map<String,CordaRPCConnection> map=new HashMap<>();
    }

    public static Map<String, CordaRPCConnection> getMap() {
        return Connection.map;
    }

    public CordaRPCConnection initialiseNodeRPCConnection(String host, String username, String password, int rpcPort) {
        NetworkHostAndPort rpcAddress = new NetworkHostAndPort(host, rpcPort);
        CordaRPCClient rpcClient = new CordaRPCClient(rpcAddress);
        CordaRPCConnection rpcConnection = rpcClient.start(username, password);
        return rpcConnection;
    }


    public static void close(CordaRPCConnection rpcConnection) {
        if (rpcConnection!=null){
            rpcConnection.notifyServerAndClose();
        }

    }
}