package com.tronlink.sdk.bean;

import java.util.List;

public class ResourceMessage {

    /**
     * freeNetLimit : 5000
     * assetNetUsed : [{"key":"1002072","value":0},{"key":"1002000","value":0}]
     * assetNetLimit : [{"key":"1002000","value":0},{"key":"1002072","value":0}]
     * TotalNetLimit : 43200000000
     * TotalNetWeight : 6659083654
     * EnergyLimit : 123
     * TotalEnergyLimit : 100000000000
     * TotalEnergyWeight : 808672148
     */

    private double freeNetLimit;
    private long TotalNetLimit;
    private long TotalNetWeight;
    private double EnergyLimit;
    private long TotalEnergyLimit;
    private double TotalEnergyWeight;
    private List<AssetNetUsedBean> assetNetUsed;
    private List<AssetNetLimitBean> assetNetLimit;

    public double getFreeNetLimit() {
        return freeNetLimit;
    }

    public void setFreeNetLimit(double freeNetLimit) {
        this.freeNetLimit = freeNetLimit;
    }

    public long getTotalNetLimit() {
        return TotalNetLimit;
    }

    public void setTotalNetLimit(long TotalNetLimit) {
        this.TotalNetLimit = TotalNetLimit;
    }

    public long getTotalNetWeight() {
        return TotalNetWeight;
    }

    public void setTotalNetWeight(long TotalNetWeight) {
        this.TotalNetWeight = TotalNetWeight;
    }

    public double getEnergyLimit() {
        return EnergyLimit;
    }

    public void setEnergyLimit(double EnergyLimit) {
        this.EnergyLimit = EnergyLimit;
    }

    public long getTotalEnergyLimit() {
        return TotalEnergyLimit;
    }

    public void setTotalEnergyLimit(long TotalEnergyLimit) {
        this.TotalEnergyLimit = TotalEnergyLimit;
    }

    public double getTotalEnergyWeight() {
        return TotalEnergyWeight;
    }

    public void setTotalEnergyWeight(double TotalEnergyWeight) {
        this.TotalEnergyWeight = TotalEnergyWeight;
    }

    public List<AssetNetUsedBean> getAssetNetUsed() {
        return assetNetUsed;
    }

    public void setAssetNetUsed(List<AssetNetUsedBean> assetNetUsed) {
        this.assetNetUsed = assetNetUsed;
    }

    public List<AssetNetLimitBean> getAssetNetLimit() {
        return assetNetLimit;
    }

    public void setAssetNetLimit(List<AssetNetLimitBean> assetNetLimit) {
        this.assetNetLimit = assetNetLimit;
    }

    public static class AssetNetUsedBean {
        /**
         * key : 1002072
         * value : 0
         */

        private String key;
        private double value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }

    public static class AssetNetLimitBean {
        /**
         * key : 1002000
         * value : 0
         */

        private String key;
        private double value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }
}
