package com.inspirenetz.api.core.dictionary;

/**
 * Created by sandheepgr on 29/4/14.
 */
public class RedemptionStatus {

    public static final int RDM_STATUS_NEW = 1;
    public static final int RDM_STATUS_PROCESSED_SUCCESSFULLY = 2;
    public static final int RDM_STATUS_FAILED = 3;
    public static final int RDM_STATUS_CANCEL_REQUEST = 4;
    public static final int RDM_STATUS_CANCELLED = 5;
    public static final int RDM_STATUS_DELIVERED = 7;
    public static final int RDM_STATUS_IN_TRANSIT = 8;
    public static final int RDM_STATUS_EXTRACTED = 9;
    public static final int RDM_STATUS_PROCESSED = 10;
    public static final int RDM_STATUS_FULFILLED = 20;
    public static final int RDM_STATUS_APPROVAL_REQUEST_EXPIRED = 21;

    public static final int RDM_STATUS_REJECTED = 22;
    public static final int RDM_STATUS_APPROVAL_WAITING = 23;
    public static final int RDM_STATUS_PENDING_RECON = 24;
    public static final int RDM_STATUS_ORDER_CONFIRMED = 25;
}
