package com.inspirenetz.api.core.dictionary;

/**
 * Created by ameen on 5/10/15.
 */
public class CustomerRewardPoint {

    private double totalRewardPoint =0.0;

    private double totalRefereePoint =0.0;

    private double totalReferrerPoint =0.0;

    public double getTotalRewardPoint() {
        return totalRewardPoint;
    }

    public void setTotalRewardPoint(double totalRewardPoint) {
        this.totalRewardPoint = totalRewardPoint;
    }

    public double getTotalRefereePoint() {
        return totalRefereePoint;
    }

    public void setTotalRefereePoint(double totalRefereePoint) {
        this.totalRefereePoint = totalRefereePoint;
    }

    public double getTotalReferrerPoint() {
        return totalReferrerPoint;
    }

    public void setTotalReferrerPoint(double totalReferrerPoint) {
        this.totalReferrerPoint = totalReferrerPoint;
    }

    @Override
    public String toString() {
        return "CustomerRewardPoint{" +
                "totalRewardPoint=" + totalRewardPoint +
                ", totalRefereePoint=" + totalRefereePoint +
                ", totalReferrerPoint=" + totalReferrerPoint +
                '}';
    }
}
