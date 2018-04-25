package com.yunsen.enjoy.thirdparty;

import com.hengyushop.db.DBManager;

public class Common {
	// 支付宝
	// //商户PID
	// public static final String PARTNER = "2088811057210856";
	// //商户收款账号
	// public static final String SELLER = "szyunsen1@163.com";
	// //商户私钥，pkcs8格式
	// public static final String RSA_PRIVATE =
	// "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAOqwxrQuEihMXVPaEzvjzL0nfGplAZEB0hfkcf9gzIRFMwtbr+Rhljj+ntWbQfLqCjFABWsC/RL9VfKaW1mz+x07tYTDedL/MPXsifQxLrZ9vVDSApSTYJvO4VqZ/uJZTbC6i7WtFDShSLWjBcpb3OKejLS2HjzA5qF1qNZntNEZAgMBAAECgYEAtnuJpWQFPkxSbPat6e1wrstbFCdBlozB3U4Fzbpoi5h63iQGmh++/MYOnqzAFK8iCbVsAQ8r1G4jPCYFTbSCVXVs5rmB75WqGXu//btHucBtXpHle0uWQyqrXnZZPMJTP29sFztPMr6mUy6FKCaWeA0ihqRa+h2Ka9YaquOCJgECQQD5/ISeF/4JckoS7+a0U9TJkruguUbHHarWIZhjOS6yMnuTGSwGKUiIMzSicl+0pjP4WKCPZM9PX/eLhurZpVJhAkEA8FYP6yTB+3QQGshQXhJ9RK8Nzmii0w5J5bdnS66/a6pQs3EEak8dlSezZW5MPZ8VHZMjnrafNMGQs+npEoLpuQJBAKUICIDZ8/JGihJAX/ySDzrXbJhpWAlhU4OzgAeZG3O2khAFISQcIu8PZuMLQJVg15RO5ghkE9whzalF80qlsmECQQDc2ymbu9arPbgC9KuuFy2YrYlxcgSXER1lhUneacKsrQGmNKiLDRMxWx9niZl0UzlzSSDFnCrnry1LuBugED+5AkBGsxkquKKfgLAb9oxRrhwQwQ/c3hfg6ajkc48ixIPRtL3zivqtpU1WBH112yMgKjUbe8o2XKIQH+uK/4ATLJo6";

	// 商户PID
	public static final String PARTNER = "2088421415061673";
	// public static final String PARTNER = "2016091901925720";
	// 商户收款账号
	public static final String SELLER = "zamscn@163.com";
	// 商户私钥，pkcs8格式
	// public static final String RSA_PRIVATE =
	// "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMwZWsQSP/648JWwigCPRhByapZN5pkvOvBqtocmIrFPmO6wF7DEe1ed/4N7V2b9HQjCy+Yi2lR16cuIyz8zqg932FdyH4xOeC66IMLaQFj6Rg7C7gDFkK2LfOTz02rKMk6gnEVHNZfKmumLpgjlqxgYSgeDRqpsvBTUNklkqWrNAgMBAAECgYBUR/gSVZL1pfs7ZS5nssJ1EjpvnPWntjg5hWeggZ/75mm5zx/JUCm68bTM38ck8UrnDkHS4/uSuypzcSa1D6megsAQIoksCURbIqgKz1qTbAGy4WjnbN4xWd/M393PZWTrOG+HywgYCOiKDHsGGhm64fX+Mta1GhDVK2zS7sd14QJBAOpYLhPHLqkH9VgssoA0yBYDcVss5Tyq4ev6/R6aXhN6AoqaHZwT0nx2XOoUDiVsewbECfSQJALOKbjdlA0VdKUCQQDe9arfJD+eMFJww9+MAZHLUvY3+hVIxDZhn1/Aw9+b/n1bT3bsbmkE6+gkKokMNIoYhkg9uPARbYEyY8NWGT0JAkEA5A4AeVrje7e0+aQ16jJzbYjYLeSZCds6lU3iOn/7gXTBOGUW36citeDyNxJhWeYiK8ln7sq/YMOFMtTfmYQByQJABcGZ88Df05mzxy2Ha/nuZa1ypedS/VnzfXm74yIrd7+ORPk8PMZs96+Pgr+GYkZI+nEdFFrd99U37LEere5g4QJAcWfoXDbFinnFtxZAWnNg8xlFFvWsTMlzy/jijEsSg2WrYQXb6pAGi4fFj+a17SHTkvT6fn1G7SEDaNkp8gTx9g==";
	// public static final String RSA_PRIVATE =
	// "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCYhaNUct+L7kWDhwbcO768Snhwvvi9TGI8UW44LxTH00vDxxWYRjbHrqr8GlbosVMsw+sMnjk+Jzlj0DA2A5sd1KgQOD/+9KBqNwqSIXR9h87X/L5shHux9lEPKXPhF+w2EBA74Lz6zKEYxmqPlrwVqpbE84QHxDbQyeWosrq/Srh7aTdw9e3DsxCfQwFG+ZMSkxBGUIhRLl5l4GODzTIonQcmL6JEy2KCtD36IKKtLNsDfi831WD97JGhKZXCJohCB4nYYnPQ8Xu/fyc4wTlVwlufG691GJT7M9uq1mQ/N5BpLmwixDb0Crm4k6nItNKqGXSykldXcl5DaU1dT2JnAgMBAAECggEBAIbRigA50lRUd+m9T1kZDo5NnYy7og9iF2uN50mOhGXl5hDWR8ZkOkPjp4xUlAH3zLfDLl/m9F52OYp7GkKEiXai5TQBJMBu5+U7ZgKpw9i6VSIibQhrSxOR0TD/yUhs5wUayd7D1wgyHSxTJxppeTQFqGPLhSeAT5va+0BVTDGNsbKpfuXEvOHDwbvaeKkSmWyhUCvM7An2PxqZgIZXEKD5u/5rvV5R4OW3xai+IwosRPeNPvKpn3vBkkntxB6eXw4NqWRfm/swAtPLFsHfM/qzc4TCLw6jrUn/EcR7w8ZnTPgKMFWSPE7gLkji0tS3QejM33Fetvgz5F5WWu5Cz6ECgYEA2yzpZto/q1oDmL0LdlzCcZOChETuaOXjC/2eEJz06dslQskVwzGsHxL8Rd6GCf2Ukgx1foCmokYTDpxP6ExQMp0iWTxiSbdZ8ef7c+LvjqGEzCeKqlQsIMdSX5wqIUpIi3LR4PtfUNc7DZ6INFgQAy0FsJG41nV0C19Ft/2Ru7kCgYEAsiXbEBhGBQLih3MVeAnVQz6D/1k4y7eKYxBKDWX+aAghUNjWeMfOTLWilVaMDveGC/JLsyD5VbRssez+4KiaHLTKyJwIFPtbN6EZAvRuM6c5Z8zQEGlSyJU5RqiX2BM1cgL6N2LPql6wfFyTAOIXWfvXP+FmK+zp6esQozQAXx8CgYBP9y/lFcXbF8luJbMT6T4MQDyDU78ESLOxAUR1XT7CP/F1T3aANBRPdhbSyyBubrWRH7iSgg/J7vUvBHrnD0jHXqtUkYJu0NWhO/lzS54JlQalkdu2z2UWRILaXVjFspuLPUYz5qLdFNn32xpotqSI9Bk6VcBxhoLjT0xZl8tfCQKBgCBMcL0oSXmbHksEeH38+mdorDHdkUiODE8Nov9u4IMoBjJMPgZD44lQ3RtKR7Zm9iywTz042zvIrHRNzaAZsWdG+AULFQQI1m7tjEVW36wJw4SoQ4jWL6YiN/IxmuYYBXaYR5lHFvOCm2jQt+5PD4DHAi2YxhdsqrQ4ZBnS0cZtAoGAXkXJLDAoxPWzw281F2DGHfNbWSA/obSbEWrwLzqtYP4W/umho6+WcKafLJ2PWN3SsiZITZH8iTh5nqcAm1sjpCnaqteufDHomH4exhRYF6CqOgU2FkanWs8n9WOXGYhoT7gka6fx1wxSMgHWsXQBfZ8PWUJprPSu5o1R9h8J7sI=";
	//	public static final String RSA_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCLmrx8nlNKtjCi6KCSxs74W/vm0tFle0XgFfLVW5x9OR1MP3b6K0A/jr/uv8Ob9wThVuoKL/T97Cm9PIb9lpGHo94aifj2UxQSuBFPjvcfrKKcGhJIw70ZH/ATDQ7mS/3+XxBBEeTr40iSq7c6tK5U6SZVHg/LpvAORKnGlKM7QyB0OtaYCJpb6hJzZmGdUadu+l3I09q0sO8hC0yDYmOkDTNWRGISTiZeJtzabGf2wDVAVSHod8DuFTARwZzJbidbB/obSl8Y26xFR8dHe00vP77thJVK3xLz3QK5OZx6VsReanycXpjmkFlD6ImKgR4NLk0iLo7pXdu59Ch9nuHFAgMBAAECggEAMOBm93ZzEOhClPa/KURDuD50LjdHKrGDIFZu43Ykjs8NS+G4ZRP5dYcj+ZHYCWXtpntIi3KI4tHpNyDW3jf2Ebg0jbQanxstHvSk0ubp7IQDloWmOyWUshQ1eGYM/mJCtelQhoeeT1wOz5MhHoOduYq7afeRbiqvRz+Q9T6a+6cbJEOsJZcqY0FCFk95GmOLZwafFGGGyJRhS2Cd91fNZDalSLvFGiyKQv6H1h+086olQjndRPx4vgksEix7X2rfP/XkEqAg16RdJ1BeVotP8Ac3cGnhoKCl+WL/jVvHoM60crQhuPoaokVLj5IWXQF0zPRG4+fD0eEjTv+yFRwkrQKBgQDK3GPmkSKsgNgpyy09hEt7zYbg9rHZC8PbZnXk4Q8PyNSTOl8JCvPZa9YRqSNpra+v4yXkCTHrM9s2Lw3UP1RUICE7HU7OMl5tVtLGzfJcZb6+uGaD/8/PXwnwUfRGctjowOV+j+RTYSbzmj8vJuHH+VipWGb9C78ymHZgqWkhAwKBgQCwLHF7Ozi3FK2XZL0dKNlMgRs7zuyKLVkaBi9p7tI+IhzzQjsrjY30Adk/Q4sTd/fMMkXc37X3hyC585DPlKn3tF4GwpR4FA13CHVc3idYCtxOME4e6+iexJ80ATDReed1BWWdIwcwtX0Ozlx4WyrV94cvSJhd+lmNKI3BtLMjlwKBgQC+lBgiZx09FHp/oQoqbdwAPhDFzd09PClPOE3vrWPZ/2F9w2u+8hQuoXll9BF52Ztg0TQqrFzCS2BY4hHK4YgW1GyDvb/oRTei3tt7u8VB+rAdOt3Mioovlmie5k9n08NlvrqbkRD/KBlQ6AsOAChDF0StK4jtl5iTLtqfauiCAwKBgEht/Tns3yRmG/hiMl4KrUwGyGuOZzFn83n/kP68cTOaUsywLiN5zyLCn86itksKx5XsFUz5rJZXt9y7ho3RUQ/F1+JvnRrBI8gMcjP8IbDQxkJfGb8cvkaID+wRkUCOHdq+nUg9XndoJkIwFVRmJvZRDgMilYINq0OdqKo64GuBAoGAU56TGbpsiDkEHvHWj5IaJ2oqZFgBD73+Ca6hc06PAvQBZZfBPq5PvGhq9m2Bg/5hO7gebm7tufDBePA2NtyOBm4sv4UO3QiCCsApdchVxW8EPm9cG6cb3vuK5cOt/gwFKXwISrUicbs2lfR0IKMySPZYTP+Ww6dGLH6HFr0js28=";
	public static final String RSA_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDOoLucX7P8QYMsyZ+H5TPoWw4xZuGaNf/t0pl63rjR3XPzZIZFj/mZvA3E3b/Av1/dBDZ/WbuUlxje5PEhpVt/1D1XSs4EcTV7B7iLWhWtwmsc/439WSkVz0ZfaMS/ObO3Kj+U4GKdDF0Efua2+4dDPMd736sRVzaZ0i04sxLyPoS1ycZbqE7I8TLShHvvWwP7ZevV1vvSeib5v7zIATgdLHftjl47CtwjZG2ZUaU3/JvoCNWjNFtcpUCNzi4Z0FqVQOQl5VFzI4PxrNtkmtBdNvrdid+pIvK1kgmK4xHUHJtTQUwOfLyNpbNY9TmyWgxNOr1IDOHfJCVEOPzHUsllAgMBAAECggEAWbErvxVL65QEfMyIk1TK03GzF36xL1+BWJqyRMOpNnAKhPx9zHRSxIj88RfQxLpNwoKxpVYuNrHf+eEnSpOcxEA84Ed4ZjU+IcmYMeVIw8RA+rVzzSWG0BDJW2D/BoLln/yW3OkJ5WIYfnsXaxaEAIvBi+PrPfu9l8fEGXiiIMR6ObIdCHcl/a/GVHGYpx0PUKoRwMDGDvcvi2pjZHtqWNtfPOy1ToZFS5YOR6asSrRM1GCRe1wMM9o6BinaBOLavpKNMxF1PIHgZYmi14O8PAWokz0jwIrTjlzrnHDGNbwnhk2lnqQkM16Y0vCBgKVY+AWJeRs6NR/FKVk30KB4AQKBgQD59tj2zL6YKvbTS7ul1FHBQzd12i4idrdOTJkJ56jpD1vH3ZxVqmVWQmROIBO6CTNk22/2aD9fPGdud4Kff3Fb8xWm/FhXNkLfzCtgPw5j9YgGuLa1Ajr2UzBv9LZ6/iAJogNhMqP7dVei4igynCw43AHvFTKA92xp+yhZyNlqdQKBgQDTngBxuxPp+aSGE2I0DCeVPds3r3ho6S0eVxrCSucBaMKmDYjDNWbbPle4HXsTV2IzOkNLQOHpQ+Qjk9AE0tcUN8pgiAPU9vtzIDtuLY1lyLXSzc7tn90mfQN9bQchSboS91CHHNa2hEQp71+0kxf/hkG4CaHDh5eH8h1L8LilMQKBgBXdvysPzBh5oa/oLxkZkrFKVP6tQF3pbq2dy8FvD3qbylaYj0V7K3Q5SJcA/VjH10QaYhf3FJwbJ50lPTsApgT6Jcm+x2eRqpXzQVQhD/w0jVqxK+J3uHq12kXg7VmjA2B0WMFW8btcj3cDO6r5uHCc5lpoy0s22bgHrYb1J/fFAoGBAMaukuY06faEP3buXgZV3xgc8mQu6BM/0e+pgmMm5gqJrC8xh9UXn1F7D+q6KyK2qWlFa4cNDM3wwxKwJd9RgUPnOag2K7rYhK7LdWD1fcsxjZy1RHIP5RfMyekJ42bajMxfzQh+CA1m04S3zRiB/kcXaz56NJmbBadKpzz0brgRAoGAfErsQb2ZtQXNd2LTr9xgH+Xhb1e9/9BBd3HWzBb4E9QehM4E5FJzDo8C2LEUYjLvA7h/6pdCrxfu+Ym4KjHZIonZaKDHDtkMfti3Bl9mfnrOlmHi62NLJyWQ68vkI411hjlnEJ4XMXzgQrrF48c1yRhWpbPhin8HHI9cLI4lhF0=";

	// 支付宝公钥
	// public static final String RSA_PUBLIC =
	// "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDqsMa0LhIoTF1T2hM748y9J3xqZQGRAdIX5HH/YMyERTMLW6/kYZY4/p7Vm0Hy6goxQAVrAv0S/VXymltZs/sdO7WEw3nS/zD17In0MS62fb1Q0gKUk2CbzuFamf7iWU2wuou1rRQ0oUi1owXKW9zinoy0th48wOahdajWZ7TRGQIDAQAB";
	// public static final String RSA_PUBLIC =
	// "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyoI0DWtiEMxccOmqagfcxEPOTairGgzkUSOjmeP9+aBH03dyPChOdlRUKZ9kQrZGeKhTrpLO0bUqvDSh8NrFYE3O3hqN0qCPzvy/8YkrliJTlVNuXlmur0ZEmkbdnXhO3E8+J8fuIuRjGHItdu19Fe+ylw7E5d7FnzgAA0fpFfBtjQjUoB7Js5mXDQWf5bG2GgEYMoqucuNU/In8PREU2lVOYdd8yLlS+HOKQntZeMk7OL0H7nP5l1naoGOEA/Vaxm1p1xsU8PE6lDsvjw84BPYtdMjCGcHV1W21+eqqZINaro/s/8J+Yl61Ofa6TQNL/rSU7LO10c3+QZaJzoVbuQIDAQAB";//查看支付宝公钥
	//	public static final String RSA_PUBLIC = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi5q8fJ5TSrYwouigksbO+Fv75tLRZXtF4BXy1VucfTkdTD92+itAP46/7r/Dm/cE4VbqCi/0/ewpvTyG/ZaRh6PeGon49lMUErgRT473H6yinBoSSMO9GR/wEw0O5kv9/l8QQRHk6+NIkqu3OrSuVOkmVR4Py6bwDkSpxpSjO0MgdDrWmAiaW+oSc2ZhnVGnbvpdyNPatLDvIQtMg2JjpA0zVkRiEk4mXibc2mxn9sA1QFUh6HfA7hUwEcGcyW4nWwf6G0pfGNusRUfHR3tNLz++7YSVSt8S890CuTmcelbEXmp8nF6Y5pBZQ+iJioEeDS5NIi6O6V3bufQofZ7hxQIDAQAB";// 查看应用公钥
	// public static final String RSA_PUBLIC =
	// "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";//使用其他加签方式
	// 查看支付宝公钥
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
	 *
	 *
	 */
	public static String TUAN_PATH = "/data/data/" + DBManager.PACKAGE_NAME
			+ "/databases/" + Common.DB_NAME;

	// 携程酒店
	public static String XIECHENG_KEY = "17FDBE73-0D17-40B3-A889-E90388E11C75";
	public static String ULR_KEY = "443386";
	public static String USER_KEY = "17562";
	public static int PAGER_LENGTH = 0;
}