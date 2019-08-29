package com.seatig.service;

import com.quanaxy.state.GrantsState;
import com.seatig.domain.*;
import net.corda.core.identity.Party;
import net.corda.core.node.NodeInfo;

import java.util.List;
import java.util.Map;

public interface QuanaxyService {
    int findClientTotal();

    int findSupplierTotal();

    int findContractTotal();

    int findNodeTotal();

    List<Party> findNotary();

    List<String> findFlow();

    List<NodeInfo> findNode();

    List<State> findState();

    List<GrantState> findTransaction();

    List<GrantState> findTransactionByid(String linrarId);

    List<Monitor> findMonitor();

    List<State> findContract();

    Map<String, List<String>> findMarketIndicator(int timeCode);

    ClientTrend findClientMarketIndicator(int timeCode);

    Map<String, List<String>> findMarket(int timeCode);

    Map<String, String> indexMarket();
}
