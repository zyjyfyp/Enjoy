package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/7/19/019.
 */

public  class PgyAppVersion {
    /**
     * buildBuildVersion : 6
     * downloadURL : https://www.pgyer.com/app/installUpdate/db97532dee6fd88104a694fc929c43c0?sig=8g0JNI%2Fg%2BXiLgSyM1R2HYVMQ8BRzxps96jQfS0IwkAYRkmXJVSwACgVL01wWl%2Ffy
     * buildHaveNewVersion : true
     * buildVersionNo : 101
     * buildVersion : 1.0.1
     * buildShortcutUrl : https://www.pgyer.com/UP9Q
     * buildUpdateDescription :
     */

    private String buildBuildVersion;
    private String downloadURL;
    private boolean buildHaveNewVersion;
    private String buildVersionNo;
    private String buildVersion;
    private String buildShortcutUrl;
    private String buildUpdateDescription;

    public String getBuildBuildVersion() {
        return buildBuildVersion;
    }

    public void setBuildBuildVersion(String buildBuildVersion) {
        this.buildBuildVersion = buildBuildVersion;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    public boolean isBuildHaveNewVersion() {
        return buildHaveNewVersion;
    }

    public void setBuildHaveNewVersion(boolean buildHaveNewVersion) {
        this.buildHaveNewVersion = buildHaveNewVersion;
    }

    public String getBuildVersionNo() {
        return buildVersionNo;
    }

    public void setBuildVersionNo(String buildVersionNo) {
        this.buildVersionNo = buildVersionNo;
    }

    public String getBuildVersion() {
        return buildVersion;
    }

    public void setBuildVersion(String buildVersion) {
        this.buildVersion = buildVersion;
    }

    public String getBuildShortcutUrl() {
        return buildShortcutUrl;
    }

    public void setBuildShortcutUrl(String buildShortcutUrl) {
        this.buildShortcutUrl = buildShortcutUrl;
    }

    public String getBuildUpdateDescription() {
        return buildUpdateDescription;
    }

    public void setBuildUpdateDescription(String buildUpdateDescription) {
        this.buildUpdateDescription = buildUpdateDescription;
    }
}
