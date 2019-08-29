package com.seatig.dao;

import com.quanaxy.state.QXState;
import com.seatig.domain.State;
import net.corda.core.contracts.StateAndRef;

import java.util.List;

public interface QuanaxyDao {
    int findClientTotal();

    int findSupplierTotal();

    int findContractTotal();

    List<State> findState();

    List<State> findTransaction();

    void findMarketIndicatorForAut();
}
