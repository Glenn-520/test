package com.seatig.utils;


import com.seatig.common.BusinessException;
import com.seatig.common.NodeRPCConnection;
import net.corda.client.rpc.CordaRPCConnection;

/**
 * @version V1.0
 * @className: CordaUtil
 * @description: Corda工具类
 * @author: glenn
 * @create: 2019-03-12 13:35
 **/
public class CordaUtil {

    /**
     * @Description: 通过rpc连接Corda
     * @Param: [host, username, password, port]
     * @return: void
     * @Author: glenn
     * @Date: 2019/3/12
     */
    public static CordaRPCConnection rpcConnection(String host, String username, String password, int port) throws BusinessException {
        NodeRPCConnection nodeRPCConnection = new NodeRPCConnection();

        try {
            return nodeRPCConnection.initialiseNodeRPCConnection(host, username, password, port);
        } catch (Exception e) {
            String message = e.getMessage();
            if (message.contains("Unable to validate user")) {
                throw new BusinessException("203101");
            }
            if (message.contains("Invalid port:")) {
                throw new BusinessException("203101");
            }
            if (message.contains("Cannot connect to server(s). Tried with all available servers.")) {
                throw new BusinessException("203102");
            }
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @Description: 关闭Corda rpc连接
     * @Param: [rpcConnection]
     * @return: void
     * @Author: glenn
     * @Date: 2019/3/12
     */
    public static void closeRpcConnection(CordaRPCConnection connection) {
        connection.notifyServerAndClose();
    }
}
