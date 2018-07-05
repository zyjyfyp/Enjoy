package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/7/5.
 */

public  class AliPaySignBean {
    /**
     * partner : 2088131337200802
     * seller : 2262810449@qq.com
     * private_key : MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDGvvHCLkPMOPqIvu/vRjyZzp849Pwh/7EHLXtJdv+dc6S/logxaKa/M/1Vr6AmeMm7iP6ukd154u1eyfccqGSkZxsWkNCL1pWqNJp71jpJOCVuAm4VOPrw3yLMqd7eQ24BLx4hBmbBUxRpnv5p8q0wPYC0w2UbwXWzeAHPhJLULQMLCwDYt1mntlmdA84WlAhcTUFF3daevY57fz193aSAinQWUlcUOJ9ca2UqHZrwLtYmhVWfTEwDqIcOmV0JOyMOomRzR3e+MPwCFiIhnyLfDkmY9pgq1YSa0hYr8qHamVlVZJn+KwJ5oCnvoNahhfbiHP10vFnvXLs4/5wKo6y7AgMBAAECggEAPEgSQBmUSs9FflCbqfGd0/EN7lKwXweVBF5RNLk5UuKOp+XarVHWscvtHR5F7yVX2t/0j7KCgMPJx6RupWyWCh+T4cZO2vtdLeECsmkKCMdU4wctqJY91u13OqiThsrhBM7rzoyIgj7WxCqLdEs9KJKfBTT25/JH5wWRVwYZGu17EzsPdg2VWVawGJqyQx1XYcU61vZ1nO3hbWnw9VRaJfND1kDCrwg+9xKxMbRUf9fWEQjGPlU3uN694wpgMDgtwaKZ7VmyaTNW5ywOCUy1rpTNo337K2kcdK8Pr93V+sWUngUX9mKmjfW6gjdQRBbTnVtBm2RNlgf73EUW8LsQoQKBgQDwF2ppd6dTNGR2UuZavkJfvxEFCsLKG0OfoRbBTYui2ryJgxIabPAXUQR3NfH/Ixn95w5EEk/Ok8pAEWUQeg2nZjoYOH0TrswFDrDw9fJ19KxyYJ5oFq2M/KEcOlROzSt5ZiceY7WQYAaWrod88dfvEYsxPSEANjyfMypqg2iheQKBgQDT6jLOOjuw82JAOvWroHGrGSuw/ERu40yfJNq2kFN0n6FVXtPWws5DLcQ35boK93KTdxbzyXiPsplXbMffHBB7F/T2iU2ZxyYdrtFHdXWETkMVeKOfM+k5gd1dLI6QI/jrZs71kezejixIHxoXUV5x4VOBLapueqTqIMMzDNvG0wKBgA/hjKFjT8caLRcCjsgr05ofulF6D75spUF211l79qOUEmqKJ/VlhVAVhNUZ1WMKAFsiIHhyOaMxdWxdejDn0eVjxq35dpSJuGcRPLUDn8EbqRh7uZ6q+VndEA8c8Jyxx5FlJ2tELytYnrby3nVuTdk7cNiV8YvhCouBR+n5vT8JAoGBAIjQL+LS18zDJRCAwDEA0cH76yhFhcztdH9eZNHu1eJ6WMdsBGontNDMUF0iNH12CgPIpAnabbqgPL2jnlqdnMpLyjwMowv/9cy+G6D95VP4zviivUhAVHdrL0ZDSPsoLkGUS1Nh1cJNmasqYUbioo31Qlso51qSVbxOUJe52n4/AoGARD8OTZojTvg1CXF4s+pyDatw/L3fw1QFtkWYFQh8Fyly3HrkN/fVHx0p9UGjEYqvM3OUAyk/GxDM3s1Gs13CsgTpeeecTwE0gCHbvrrlwZWHh3OL5QRYkTSKOhxkDYriKNHBYa8QIX2uO16B2f7AzzVYU933Gv6vHWxeh0eDQvc=
     * return_url : http://www.szlxkg.com/api/payment/alipayphone/return_url.aspx
     * notify_url : http://www.szlxkg.com/api/payment/alipayphone/notify_url.aspx
     */

    private String partner;
    private String seller;
    private String private_key;
    private String return_url;
    private String notify_url;

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getPrivate_key() {
        return private_key;
    }

    public void setPrivate_key(String private_key) {
        this.private_key = private_key;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }
}