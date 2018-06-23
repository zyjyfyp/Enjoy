package com.yunsen.enjoy.thirdparty;

import com.hengyushop.db.DBManager;

public class Common {
    // 商户PID
    public static String PARTNER = "2088131337200802";
    // public static final String PARTNER = "2016091901925720";
    // 商户收款账号
    public static String SELLER = "2262810449@qq.com";
    // 商户私钥，pkcs8格式
    public static String RSA_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDrsKfVDpj9Onv2Dkf3OsKMfmmu7TqyFXHCDVETFzv4Z1qDjo2H3cEsbFdIUzDcvCXK+OkHNLN3ofLiWL4I2qiRLJF/ndrUDddYSk0fkB3XjWsiAP5u6Zwktnqr7SaIMQaJnvfy3C+nLYA+nGG8DExRGRkLC+LOHkfaYuWiy/XqS9eeTia0LO914aqA+j6uUTCd6YDyJVAcs+Am7MfKN/jsUQi7wkwMF5BAvSMmDXvpFPKjBh21glSMW/SQYrpPKGyq4DgaEmHrvw5rKvBpRdHW5RUNmwe8GhyNS9qU/AMuGIuvAW1BAtliQtZbhJM2WxouvIT5cgONcVsZUQhWxefpAgMBAAECggEAQI+Dabk17ye2DRCcZfySjimSiXgyO9Xj82PycWhL5R9qi97gpfb4k1KaFhWzrMNdFtxX+MFW585U2eu1InfC9QFgWbTD3JJuCqFPvvWbNxBl6EqdZ4RWYTgbIj0z8Np9D/WbtcrawtCT8JKlsv0raouVcJEw74Ub6ZZtkMqN8j+NkPhyxxpVWN126L0qKPD8kRzAl2SphdBF+H3umP7XKFv3riBaXJKIWnvVwtFmgvpY+D14LKqoXyU6reE8uFGErpKq/Xfsnme3rAlVNbWgnb8kCcLxgBNG8TIt3o/oYpvclUOm6Q35ADa8a8V82TTYZj7zrpiYqsfjo1hfJ/USgQKBgQD8k5vr6C7YsCtWlqcaUX5SP0s74amj508bA06ST+Q/eQdFL0VzyGCCCOEyCaE0d4xRSnjJnhLST5r3BRzmPxA0ozsMDBD6FXQTAsYVcDbAEk71lo/YIs6q9Ek75j2/UiQ3RtT5K8OeD8fCsdIkqLRDWv/v1Z/RbVB8KVXwhf1GHwKBgQDu4nQdmSc1h2IjdMMhFMsIUmSFHo3pqdiCY+eTnFwqD4kxYUAIOQkf1FHmLalLQoUJlHeloTTMep7alzIOG8GPL+zoSb4uekft5IA3nwH5TmajrFWijOfdOjEtuxiE2Zy57IFij7OQ6Ng3OIAc46U4GE8mg+5icrdA40KzazDA9wKBgFH+LSDBB+wqDHeYgniluedDSs4OrL5bia+QPEJ9EvCT/os0FEzI4KN19H0E6UMaCAAft+Rjn2KMmxbTnxUTpdQgpTt880nsCvlc7ibpfayOhPluvhWA/yw6D0gVrzfbl4wmP18AGQnHv6ZxiXGDHrm64djetXoMDZOw64ggRx8rAoGBANrn+bAF4MBUx8HmBcoS29tMZYD3duRhGF6kXloONwb0JaxyidL/MTVRw5tCzJiC5JbazqmhCFk5JMj7NItpM/6a1GYo7XZ55+BAPJ67v3tONRBXpEHJDdDUuqxFfzp+j/hwmkBPnyVzum8f/4Z5tbWp0R5iV9kvdm6n0z/HQm4RAoGAR933l8i3CsAbx4qCSpY6G5sdToHgbiDoaBGnivph1zEcH7yGYCgakeK1YM0dv03RzLzKRaJXp5mgmhGoAdaW6AidpATQ53rr/5XS2t9NKleH3r7Dl0JTKL+gF9TDWvX4E8mNaxAxfS2k8b7TPK2qN0f/z4Yl73drGrOwoS1hDNs=";

    public static final String RSA_PUBLIC = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzqC7nF+z/EGDLMmfh+Uz6FsOMWbhmjX/7dKZet640d1z82SGRY/5mbwNxN2/wL9f3QQ2f1m7lJcY3uTxIaVbf9Q9V0rOBHE1ewe4i1oVrcJrHP+N/VkpFc9GX2jEvzmztyo/lOBinQxdBH7mtvuHQzzHe9+rEVc2mdItOLMS8j6EtcnGW6hOyPEy0oR771sD+2Xr1db70nom+b+8yAE4HSx37Y5eOwrcI2RtmVGlN/yb6AjVozRbXKVAjc4uGdBalUDkJeVRcyOD8azbZJrQXTb63YnfqSLytZIJiuMR1BybU0FMDny8jaWzWPU5sloMTTq9SAzh3yQlRDj8x1LJZQIDAQAB";// 查看应用公钥

    public static String IMEI;
    public static String locationName = "locals";
    public static String TRAIN_SECKEY = "3e8d9caee01c485fd5414edee3d49660";
    public static String TRAIN_USERID = "szyunsen@163.com";
    /**
     * 定位的配置文件
     */
    public static final String DB_NAME = "tuangou.db";
    /**
     * 团购地址
     */
    public static String TUAN_PATH = "/data/data/" + DBManager.PACKAGE_NAME
            + "/databases/" + Common.DB_NAME;

    // 携程酒店
    public static String XIECHENG_KEY = "17FDBE73-0D17-40B3-A889-E90388E11C75";
    public static String ULR_KEY = "443386";
    public static String USER_KEY = "17562";
    public static int PAGER_LENGTH = 0;
    public static String WX_APP_ID;
    public static String WX_MCH_ID = "1505676961";

}
