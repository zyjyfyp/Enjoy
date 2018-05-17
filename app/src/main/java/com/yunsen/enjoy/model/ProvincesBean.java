package com.yunsen.enjoy.model;

import java.util.List;

/**
 * Created by Administrator on 2018/5/17.
 */
public  class ProvincesBean {
    /**
     * citys : [{"citysName":"石家庄市"},{"citysName":"邯郸市"},{"citysName":"唐山市"},{"citysName":"保定市"},{"citysName":"秦皇岛市"},{"citysName":"邢台市"},{"citysName":"张家口市"},{"citysName":"承德市"},{"citysName":"沧州市"},{"citysName":"廊坊市"},{"citysName":"衡水市"},{"citysName":"辛集市"},{"citysName":"晋州市"},{"citysName":"新乐市"},{"citysName":"遵化市"},{"citysName":"迁安市"},{"citysName":"霸州市"},{"citysName":"三河市"},{"citysName":"定州市"},{"citysName":"涿州市"},{"citysName":"安国市"},{"citysName":"高碑店市"},{"citysName":"泊头市"},{"citysName":"任丘市"},{"citysName":"黄骅市"},{"citysName":"河间市"},{"citysName":"冀州市"},{"citysName":"深州市"},{"citysName":"南宫市"},{"citysName":"沙河市"},{"citysName":"武安市"}]
     * provinceName : 河北省
     */

    private String provinceName;
    private List<CitysBean> citys;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public List<CitysBean> getCitys() {
        return citys;
    }

    public void setCitys(List<CitysBean> citys) {
        this.citys = citys;
    }

}