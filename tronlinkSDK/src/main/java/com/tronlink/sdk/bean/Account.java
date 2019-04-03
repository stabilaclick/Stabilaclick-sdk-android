package com.tronlink.sdk.bean;

import java.util.List;

public class Account {

    /**
     * address : 4179749512c55a82fcd9bc93d798302a6ed774cf35
     * balance : 21291070
     * create_time : 1548835482000
     * latest_opration_time : 1553759658000
     * latest_consume_free_time : 1553759658000
     * account_resource : {"frozen_balance_for_energy":{"frozen_balance":1000000,"expire_time":1550998071000},"latest_consume_time_for_energy":1552557906000}
     * assetV2 : [{"key":"1002072","value":548518},{"key":"1002000","value":5370548840}]
     * free_asset_net_usageV2 : [{"key":"1002072","value":0},{"key":"1002000","value":0}]
     */

    private String address;
    private double balance;
    private long create_time;
    private long latest_opration_time;
    private long latest_consume_free_time;
    private AccountResourceBean account_resource;
    private List<AssetV2Bean> assetV2;
    private List<FreeAssetNetUsageV2Bean> free_asset_net_usageV2;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getLatest_opration_time() {
        return latest_opration_time;
    }

    public void setLatest_opration_time(long latest_opration_time) {
        this.latest_opration_time = latest_opration_time;
    }

    public long getLatest_consume_free_time() {
        return latest_consume_free_time;
    }

    public void setLatest_consume_free_time(long latest_consume_free_time) {
        this.latest_consume_free_time = latest_consume_free_time;
    }

    public AccountResourceBean getAccount_resource() {
        return account_resource;
    }

    public void setAccount_resource(AccountResourceBean account_resource) {
        this.account_resource = account_resource;
    }

    public List<AssetV2Bean> getAssetV2() {
        return assetV2;
    }

    public void setAssetV2(List<AssetV2Bean> assetV2) {
        this.assetV2 = assetV2;
    }

    public List<FreeAssetNetUsageV2Bean> getFree_asset_net_usageV2() {
        return free_asset_net_usageV2;
    }

    public void setFree_asset_net_usageV2(List<FreeAssetNetUsageV2Bean> free_asset_net_usageV2) {
        this.free_asset_net_usageV2 = free_asset_net_usageV2;
    }

    public static class AccountResourceBean {
        /**
         * frozen_balance_for_energy : {"frozen_balance":1000000,"expire_time":1550998071000}
         * latest_consume_time_for_energy : 1552557906000
         */

        private FrozenBalanceForEnergyBean frozen_balance_for_energy;
        private long latest_consume_time_for_energy;

        public FrozenBalanceForEnergyBean getFrozen_balance_for_energy() {
            return frozen_balance_for_energy;
        }

        public void setFrozen_balance_for_energy(FrozenBalanceForEnergyBean frozen_balance_for_energy) {
            this.frozen_balance_for_energy = frozen_balance_for_energy;
        }

        public long getLatest_consume_time_for_energy() {
            return latest_consume_time_for_energy;
        }

        public void setLatest_consume_time_for_energy(long latest_consume_time_for_energy) {
            this.latest_consume_time_for_energy = latest_consume_time_for_energy;
        }

        public static class FrozenBalanceForEnergyBean {
            /**
             * frozen_balance : 1000000
             * expire_time : 1550998071000
             */

            private double frozen_balance;
            private long expire_time;

            public double getFrozen_balance() {
                return frozen_balance;
            }

            public void setFrozen_balance(int frozen_balance) {
                this.frozen_balance = frozen_balance;
            }

            public long getExpire_time() {
                return expire_time;
            }

            public void setExpire_time(long expire_time) {
                this.expire_time = expire_time;
            }
        }
    }

    public static class AssetV2Bean {
        /**
         * key : 1002072
         * value : 548518
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

    public static class FreeAssetNetUsageV2Bean {
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
}
