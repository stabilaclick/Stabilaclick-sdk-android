package com.stabilaclick.sdk.bean;

import java.util.List;

public class ResourceMessage {

    /**
     * freeNetLimit : 5000
     * assetNetUsed : [{"key":"1002072","value":0},{"key":"1002000","value":0}]
     * assetNetLimit : [{"key":"1002000","value":0},{"key":"1002072","value":0}]
     * TotalNetLimit : 43200000000
     * TotalNetWeight : 6659083654
     * UcrLimit : 123
     * TotalUcrLimit : 100000000000
     * TotalUcrWeight : 808672148
     */
    private double freeNetUsed;
    private double NetUsed;
    private double NetLimit;
    private double UcrUsed;

    private double freeNetLimit;
    private double TotalNetLimit;
    private double TotalNetWeight;
    private double UcrLimit;
    private double TotalUcrLimit;
    private double TotalUcrWeight;
    private double storageUsed;
    private double storageLimit;

    private List<AssetNetUsedBean> assetNetUsed;
    private List<AssetNetLimitBean> assetNetLimit;

    public double getFreeNetUsed() {
        return freeNetUsed;
    }

    public void setFreeNetUsed(double freeNetUsed) {
        this.freeNetUsed = freeNetUsed;
    }

    public double getNetUsed() {
        return NetUsed;
    }

    public void setNetUsed(double netUsed) {
        NetUsed = netUsed;
    }

    public double getNetLimit() {
        return NetLimit;
    }

    public void setNetLimit(double netLimit) {
        NetLimit = netLimit;
    }

    public double getUcrUsed() {
        return UcrUsed;
    }

    public void setUcrUsed(double ucrUsed) {
        UcrUsed = ucrUsed;
    }

    public double getStorageUsed() {
        return storageUsed;
    }

    public void setStorageUsed(double storageUsed) {
        this.storageUsed = storageUsed;
    }

    public double getStorageLimit() {
        return storageLimit;
    }

    public void setStorageLimit(double storageLimit) {
        this.storageLimit = storageLimit;
    }

    public double getFreeNetLimit() {
        return freeNetLimit;
    }

    public void setFreeNetLimit(double freeNetLimit) {
        this.freeNetLimit = freeNetLimit;
    }

    public double getTotalNetLimit() {
        return TotalNetLimit;
    }

    public void setTotalNetLimit(long TotalNetLimit) {
        this.TotalNetLimit = TotalNetLimit;
    }

    public double getTotalNetWeight() {
        return TotalNetWeight;
    }

    public void setTotalNetWeight(long TotalNetWeight) {
        this.TotalNetWeight = TotalNetWeight;
    }

    public double getUcrLimit() {
        return UcrLimit;
    }

    public void setUcrLimit(double UcrLimit) {
        this.UcrLimit = UcrLimit;
    }

    public double getTotalUcrLimit() {
        return TotalUcrLimit;
    }

    public void setTotalUcrLimit(long TotalUcrLimit) {
        this.TotalUcrLimit = TotalUcrLimit;
    }

    public double getTotalUcrWeight() {
        return TotalUcrWeight;
    }

    public void setTotalUcrWeight(double TotalUcrWeight) {
        this.TotalUcrWeight = TotalUcrWeight;
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
