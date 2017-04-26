package ptuddd.app.android.KQXSapp.rest.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by KTC on 4/26/17.
 */

public class KQXS implements Serializable {
    private String province;
    private String date;
    private HashMap<String, List<String>> prizes;

    public KQXS(String province, String date, HashMap<String, List<String>> prizes) {
        this.province = province;
        this.date = date;
        this.prizes = prizes;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public HashMap<String, List<String>> getPrizes() {
        return prizes;
    }

    public void setPrizes(HashMap<String, List<String>> prizes) {
        this.prizes = prizes;
    }
}
