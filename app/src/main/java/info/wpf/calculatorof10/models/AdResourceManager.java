package info.wpf.calculatorof10.models;

public class AdResourceManager {

    public static final String AD_NETWORK_FB      = "FACEBOOK";
    public static final String AD_NETWORK_ADMOB   = "ADMOB";

    // "405536320813497_1004176627616127"
    private String JSON_AD_UNIT_FB_BANNER;
    private String JSON_AD_UNIT_ADMOB_BANNER;
    private String JSON_ACTIVE_AD_NETWORK;
    private Boolean JSON_SHOW_ADS;
    private Boolean DEV_MONITOR;



    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public AdResourceManager() { }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public Boolean getDEV_MONITOR() {
        return DEV_MONITOR;
    }

    public void setDEV_MONITOR(Boolean DEV_MONITOR) {
        this.DEV_MONITOR = DEV_MONITOR;
    }

    public String getJSON_AD_UNIT_FB_BANNER() {
        return JSON_AD_UNIT_FB_BANNER;
    }

    public void setJSON_AD_UNIT_FB_BANNER(String JSON_AD_UNIT_FB_BANNER) {
        this.JSON_AD_UNIT_FB_BANNER = JSON_AD_UNIT_FB_BANNER;
    }

    public String getJSON_AD_UNIT_ADMOB_BANNER() {
        return JSON_AD_UNIT_ADMOB_BANNER;
    }

    public void setJSON_AD_UNIT_ADMOB_BANNER(String JSON_AD_UNIT_ADMOB_BANNER) {
        this.JSON_AD_UNIT_ADMOB_BANNER = JSON_AD_UNIT_ADMOB_BANNER;
    }

    public String getJSON_ACTIVE_AD_NETWORK() {
        return JSON_ACTIVE_AD_NETWORK;
    }

    public void setJSON_ACTIVE_AD_NETWORK(String JSON_ACTIVE_AD_NETWORK) {
        this.JSON_ACTIVE_AD_NETWORK = JSON_ACTIVE_AD_NETWORK;
    }

    public Boolean getJSON_SHOW_ADS() {
        return JSON_SHOW_ADS;
    }

    public void setJSON_SHOW_ADS(Boolean JSON_SHOW_ADS) {
        this.JSON_SHOW_ADS = JSON_SHOW_ADS;
    }

}
