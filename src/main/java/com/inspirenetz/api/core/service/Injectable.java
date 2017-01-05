package com.inspirenetz.api.core.service;
import com.inspirenetz.api.util.TransferPointUtils;

public interface Injectable{

    public void inject(TransferPointUtils beansManager);

}