package com.stabilaclick.sdk.bean;

import java.util.List;

public class Account {

    /**
     * address : 4179749512c55a82fcd9bc93d798302a6ed774cf35
     * balance : 21291070
     * create_time : 1548835482000
     * latest_opration_time : 1553759658000
     * latest_consume_free_time : 1553759658000
     * account_resource : {"cded_balance_for_ucr":{"cded_balance":1000000,"expire_time":1550998071000},"latest_consume_time_for_ucr":1552557906000}
     * assetV2 : [{"key":"1002072","value":548518},{"key":"1002000","value":5370548840}]
     * free_asset_net_usageV2 : [{"key":"1002072","value":0},{"key":"1002000","value":0}]
     */

    private String address;
    private double balance;
    private double acquired_delegated_cded_balance_for_bandwidth;
    private double delegated_cded_balance_for_bandwidth;
    private double allowance;
    private double latest_withdraw_time;
    private long create_time;
    private long latest_opration_time;
    private long latest_consume_free_time;
    private double net_usage;
    private AccountResourceBean account_resource;
    private List<AssetV2Bean> assetV2;
    private List<AssetV2Bean> asset;
    private List<FreeAssetNetUsageV2Bean> free_asset_net_usageV2;
    private List<Vote> votes;
    private List<Cded> cded;
    private int type;
    private String asset_issued_name;
    private String asset_issued_ID;
    private List<Cded> cded_supply;

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

    public double getAcquired_delegated_cded_balance_for_bandwidth() {
        return acquired_delegated_cded_balance_for_bandwidth;
    }

    public void setAcquired_delegated_cded_balance_for_bandwidth(double acquired_delegated_cded_balance_for_bandwidth) {
        this.acquired_delegated_cded_balance_for_bandwidth = acquired_delegated_cded_balance_for_bandwidth;
    }

    public double getDelegated_cded_balance_for_bandwidth() {
        return delegated_cded_balance_for_bandwidth;
    }

    public void setDelegated_cded_balance_for_bandwidth(double delegated_cded_balance_for_bandwidth) {
        this.delegated_cded_balance_for_bandwidth = delegated_cded_balance_for_bandwidth;
    }

    public double getAllowance() {
        return allowance;
    }

    public void setAllowance(double allowance) {
        this.allowance = allowance;
    }

    public double getLatest_withdraw_time() {
        return latest_withdraw_time;
    }

    public void setLatest_withdraw_time(double latest_withdraw_time) {
        this.latest_withdraw_time = latest_withdraw_time;
    }

    public double getNet_usage() {
        return net_usage;
    }

    public void setNet_usage(double net_usage) {
        this.net_usage = net_usage;
    }

    public List<AssetV2Bean> getAsset() {
        return asset;
    }

    public void setAsset(List<AssetV2Bean> asset) {
        this.asset = asset;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public List<Cded> getCded() {
        return cded;
    }

    public void setCded(List<Cded> cded) {
        this.cded = cded;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class AccountResourceBean {
        /**
         * cded_balance_for_ucr : {"cded_balance":1000000,"expire_time":1550998071000}
         * latest_consume_time_for_ucr : 1552557906000
         */

        private CdedBalanceForUcrBean cded_balance_for_ucr;
        private long latest_consume_time_for_ucr;

        public CdedBalanceForUcrBean getCded_balance_for_ucr() {
            return cded_balance_for_ucr;
        }

        public void setCded_balance_for_ucr(CdedBalanceForUcrBean cded_balance_for_ucr) {
            this.cded_balance_for_ucr = cded_balance_for_ucr;
        }

        public long getLatest_consume_time_for_ucr() {
            return latest_consume_time_for_ucr;
        }

        public void setLatest_consume_time_for_ucr(long latest_consume_time_for_ucr) {
            this.latest_consume_time_for_ucr = latest_consume_time_for_ucr;
        }

        public static class CdedBalanceForUcrBean {
            /**
             * cded_balance : 1000000
             * expire_time : 1550998071000
             */

            private double cded_balance;
            private long expire_time;

            public double getCded_balance() {
                return cded_balance;
            }

            public void setCded_balance(int cded_balance) {
                this.cded_balance = cded_balance;
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

    public static class Vote {
        private String vote_address;
        private double vote_count;

        public String getVote_address() {
            return vote_address;
        }

        public void setVote_address(String vote_address) {
            this.vote_address = vote_address;
        }

        public double getVote_count() {
            return vote_count;
        }

        public void setVote_count(double vote_count) {
            this.vote_count = vote_count;
        }
    }

    public static class Cded {
        private double cded_balance;
        private double expire_time;

        public double getCded_balance() {
            return cded_balance;
        }

        public void setCded_balance(double cded_balance) {
            this.cded_balance = cded_balance;
        }

        public double getExpire_time() {
            return expire_time;
        }

        public void setExpire_time(double expire_time) {
            this.expire_time = expire_time;
        }
    }

    public String getAsset_issued_name() {
        return asset_issued_name;
    }

    public void setAsset_issued_name(String asset_issued_name) {
        this.asset_issued_name = asset_issued_name;
    }

    public String getAsset_issued_ID() {
        return asset_issued_ID;
    }

    public void setAsset_issued_ID(String asset_issued_ID) {
        this.asset_issued_ID = asset_issued_ID;
    }

    public List<Cded> getCded_supply() {
        return cded_supply;
    }

    public void setCded_supply(List<Cded> cded_supply) {
        this.cded_supply = cded_supply;
    }
}
