package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.CustomerMailSummaryStatus;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by sandheepgr on 29/3/14.
 */
@Entity
@Table(name = "CUSTOMER_MAIL_SUMMARY")
public class CustomerMailSummary {

    @Id
    @Column(name = "CMS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cmsId;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMS_USER_NO")
    private int cmsUserNo;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMS_SUMMARY_STATUS")
    private CustomerMailSummaryStatus cmsSummaryStatus;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMS_SUMMARY_DATE")
    private Date cmsSummaryDate;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CMS_SUMMARY_TIMESTAMP")
    private Timestamp cmsSummaryTimestamp;


    public int getCmsId() {
        return cmsId;
    }

    public void setCmsId(int cmsId) {
        this.cmsId = cmsId;
    }

    public int getCmsUserNo() {
        return cmsUserNo;
    }

    public void setCmsUserNo(int cmsUserNo) {
        this.cmsUserNo = cmsUserNo;
    }

    public CustomerMailSummaryStatus getCmsSummaryStatus() {
        return cmsSummaryStatus;
    }

    public void setCmsSummaryStatus(CustomerMailSummaryStatus cmsSummaryStatus) {
        this.cmsSummaryStatus = cmsSummaryStatus;
    }

    public Date getCmsSummaryDate() {
        return cmsSummaryDate;
    }

    public void setCmsSummaryDate(Date cmsSummaryDate) {
        this.cmsSummaryDate = cmsSummaryDate;
    }

    public Timestamp getCmsSummaryTimestamp() {
        return cmsSummaryTimestamp;
    }

    public void setCmsSummaryTimestamp(Timestamp cmsSummaryTimestamp) {
        this.cmsSummaryTimestamp = cmsSummaryTimestamp;
    }


    @Override
    public String toString() {
        return "CustomerMailSummary{" +
                "cmsId=" + cmsId +
                ", cmsUserNo=" + cmsUserNo +
                ", cmsSummaryStatus=" + cmsSummaryStatus +
                ", cmsSummaryDate=" + cmsSummaryDate +
                ", cmsSummaryTimestamp=" + cmsSummaryTimestamp +
                '}';
    }
}
