package com.ca.imagefinder;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by carlosyang on 2016/12/31.
 */
public class PixabayResponse {

    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("totalHits")
    @Expose
    private String totalHits;
    @SerializedName("hits")
    @Expose
    private List<PixabayImage> hits = null;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(String totalHits) {
        this.totalHits = totalHits;
    }

    public List<PixabayImage> getHits() {
        return hits;
    }

    public void setHits(List<PixabayImage> hits) {
        this.hits = hits;
    }
}
