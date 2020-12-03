package com.pla_bear.graph;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class GraphDTO implements Parcelable {
    @SerializedName("CITY_JIDT_NM")
    private String CITY_JIDT_NM;
    @SerializedName("CTS_JIDT_NM")
    private String CTS_JIDT_NM;
    @SerializedName("DATA_TM_NM")
    private String DATA_TM_NM;
    @SerializedName("TOT_SUM")
    private float TOT_SUM;
    @SerializedName("SPEC_SUM")
    private float SPEC_SUM;
    @SerializedName("COMB_SUM")
    private float COMB_SUM;
    @SerializedName("COMB_FOOD_VEGET")
    private float COMB_FOOD_VEGET;

    @SerializedName("COMB_PPR_KIND")
    private float COMB_PPR_KIND;
    @SerializedName("COMB_WOOD_KIND")
    private float COMB_WOOD_KIND;
    @SerializedName("COMB_RUBBER_KIND")
    private float COMB_RUBBER_KIND;
    @SerializedName("COMB_PLAS_KIND")
    private float COMB_PLAS_KIND;
    @SerializedName("COMB_ETC_ETC_KIND")
    private float COMB_ETC_ETC_KIND;
    @SerializedName("NCMB_SUM")
    private float NCMB_SUM;
    @SerializedName("NCMB_GLSS_KIND")
    private float NCMB_GLSS_KIND;
    @SerializedName("NCMB_MET_KIND")
    private float NCMB_MET_KIND;
    @SerializedName("NCMB_SAND_KIND")
    private float NCMB_SAND_KIND;
    @SerializedName("NCMB_ETC_KIND")
    private float NCMB_ETC_KIND;
    @SerializedName("ETC_KIND")
    private float ETC_KIND;
    @SerializedName("DSTRCT_SUM")
    private float DSTRCT_SUM;
    @SerializedName("DSTRCT_PPR_KIND_QTY")
    private float DSTRCT_PPR_KIND_QTY;
    @SerializedName("DSTRCT_GLSS_KIND_QTY")
    private float DSTRCT_GLSS_KIND_QTY;
    @SerializedName("DSTRCT_CAN_KIND_QTY")
    private float DSTRCT_CAN_KIND_QTY;
    @SerializedName("DSTRCT_SYNRSNS_KIND")
    private float DSTRCT_SYNRSNS_KIND;
    @SerializedName("DSTRCT_PLAS_KIND_QTY")
    private float DSTRCT_PLAS_KIND_QTY;
    @SerializedName("DSTRCT_FMDRSNS_KIND_QTY")
    private float DSTRCT_FMDRSNS_KIND_QTY;
    @SerializedName("DSTRCT_ELEPDT_KIND_QTY")
    private float DSTRCT_ELEPDT_KIND_QTY;
    @SerializedName("DSTRCT_BATTERY_KIND_QTY")
    private float DSTRCT_BATTERY_KIND_QTY;
    @SerializedName("DSTRCT_TIRE_KIND_QTY")
    private float DSTRCT_TIRE_KIND_QTY;
    @SerializedName("DSTRCT_LUBRICANT_QTY")
    private float DSTRCT_LUBRICANT_QTY;
    @SerializedName("DSTRCT_F_LIGHT_QTY")
    private float DSTRCT_F_LIGHT_QTY;
    @SerializedName("DSTRCT_S_IRON_KIND_QTY")
    private float DSTRCT_S_IRON_KIND_QTY;
    @SerializedName("DSTRCT_CLOTHES_KIND_QTY")
    private float DSTRCT_CLOTHES_KIND_QTY;
    @SerializedName("DSTRCT_FARM_KIND_QTY")
    private float DSTRCT_FARM_KIND_QTY;
    @SerializedName("DSTRCT_FURN_KIND_QTY")
    private float DSTRCT_FURN_KIND_QTY;
    @SerializedName("DSTRCT_WTC_OIL_KIND_QTY")
    private float DSTRCT_WTC_OIL_KIND_QTY;
    @SerializedName("DSTRCT_ETC_KIND_QTY")
    private float DSTRCT_ETC_KIND_QTY;
    @SerializedName("DSTRCT_RSDL_KIND_QTY")
    private float DSTRCT_RSDL_KIND_QTY;

    public String getCITY_JIDT_NM() {
        return CITY_JIDT_NM;
    }

    public void setCITY_JIDT_NM(String CITY_JIDT_NM) {
        this.CITY_JIDT_NM = CITY_JIDT_NM;
    }

    public String getCTS_JIDT_NM() {
        return CTS_JIDT_NM;
    }

    public void setCTS_JIDT_NM(String CTS_JIDT_NM) {
        this.CTS_JIDT_NM = CTS_JIDT_NM;
    }

    public String getDATA_TM_NM() {
        return DATA_TM_NM;
    }

    public void setDATA_TM_NM(String DATA_TM_NM) {
        this.DATA_TM_NM = DATA_TM_NM;
    }

    public float getTOT_SUM() {
        return TOT_SUM;
    }

    public void setTOT_SUM(float TOT_SUM) {
        this.TOT_SUM = TOT_SUM;
    }

    public float getSPEC_SUM() {
        return SPEC_SUM;
    }

    public void setSPEC_SUM(float SPEC_SUM) {
        this.SPEC_SUM = SPEC_SUM;
    }

    public float getCOMB_SUM() {
        return COMB_SUM;
    }

    public void setCOMB_SUM(float COMB_SUM) {
        this.COMB_SUM = COMB_SUM;
    }

    public float getCOMB_FOOD_VEGET() {
        return COMB_FOOD_VEGET;
    }

    public void setCOMB_FOOD_VEGET(float COMB_FOOD_VEGET) {
        this.COMB_FOOD_VEGET = COMB_FOOD_VEGET;
    }

    public float getCOMB_PPR_KIND() {
        return COMB_PPR_KIND;
    }

    public void setCOMB_PPR_KIND(float COMB_PPR_KIND) {
        this.COMB_PPR_KIND = COMB_PPR_KIND;
    }

    public float getCOMB_WOOD_KIND() {
        return COMB_WOOD_KIND;
    }

    public void setCOMB_WOOD_KIND(float COMB_WOOD_KIND) {
        this.COMB_WOOD_KIND = COMB_WOOD_KIND;
    }

    public float getCOMB_RUBBER_KIND() {
        return COMB_RUBBER_KIND;
    }

    public void setCOMB_RUBBER_KIND(float COMB_RUBBER_KIND) {
        this.COMB_RUBBER_KIND = COMB_RUBBER_KIND;
    }

    public float getCOMB_PLAS_KIND() {
        return COMB_PLAS_KIND;
    }

    public void setCOMB_PLAS_KIND(float COMB_PLAS_KIND) {
        this.COMB_PLAS_KIND = COMB_PLAS_KIND;
    }

    public float getCOMB_ETC_ETC_KIND() {
        return COMB_ETC_ETC_KIND;
    }

    public void setCOMB_ETC_ETC_KIND(float COMB_ETC_ETC_KIND) {
        this.COMB_ETC_ETC_KIND = COMB_ETC_ETC_KIND;
    }

    public float getNCMB_SUM() {
        return NCMB_SUM;
    }

    public void setNCMB_SUM(float NCMB_SUM) {
        this.NCMB_SUM = NCMB_SUM;
    }

    public float getNCMB_GLSS_KIND() {
        return NCMB_GLSS_KIND;
    }

    public void setNCMB_GLSS_KIND(float NCMB_GLSS_KIND) {
        this.NCMB_GLSS_KIND = NCMB_GLSS_KIND;
    }

    public float getNCMB_MET_KIND() {
        return NCMB_MET_KIND;
    }

    public void setNCMB_MET_KIND(float NCMB_MET_KIND) {
        this.NCMB_MET_KIND = NCMB_MET_KIND;
    }

    public float getNCMB_SAND_KIND() {
        return NCMB_SAND_KIND;
    }

    public void setNCMB_SAND_KIND(float NCMB_SAND_KIND) {
        this.NCMB_SAND_KIND = NCMB_SAND_KIND;
    }

    public float getNCMB_ETC_KIND() {
        return NCMB_ETC_KIND;
    }

    public void setNCMB_ETC_KIND(float NCMB_ETC_KIND) {
        this.NCMB_ETC_KIND = NCMB_ETC_KIND;
    }

    public float getETC_KIND() {
        return ETC_KIND;
    }

    public void setETC_KIND(float ETC_KIND) {
        this.ETC_KIND = ETC_KIND;
    }

    public float getDSTRCT_SUM() {
        return DSTRCT_SUM;
    }

    public void setDSTRCT_SUM(float DSTRCT_SUM) {
        this.DSTRCT_SUM = DSTRCT_SUM;
    }

    public float getDSTRCT_PPR_KIND_QTY() {
        return DSTRCT_PPR_KIND_QTY;
    }

    public void setDSTRCT_PPR_KIND_QTY(float DSTRCT_PPR_KIND_QTY) {
        this.DSTRCT_PPR_KIND_QTY = DSTRCT_PPR_KIND_QTY;
    }

    public float getDSTRCT_GLSS_KIND_QTY() {
        return DSTRCT_GLSS_KIND_QTY;
    }

    public void setDSTRCT_GLSS_KIND_QTY(float DSTRCT_GLSS_KIND_QTY) {
        this.DSTRCT_GLSS_KIND_QTY = DSTRCT_GLSS_KIND_QTY;
    }

    public float getDSTRCT_CAN_KIND_QTY() {
        return DSTRCT_CAN_KIND_QTY;
    }

    public void setDSTRCT_CAN_KIND_QTY(float DSTRCT_CAN_KIND_QTY) {
        this.DSTRCT_CAN_KIND_QTY = DSTRCT_CAN_KIND_QTY;
    }

    public float getDSTRCT_SYNRSNS_KIND() {
        return DSTRCT_SYNRSNS_KIND;
    }

    public void setDSTRCT_SYNRSNS_KIND(float DSTRCT_SYNRSNS_KIND) {
        this.DSTRCT_SYNRSNS_KIND = DSTRCT_SYNRSNS_KIND;
    }

    public float getDSTRCT_PLAS_KIND_QTY() {
        return DSTRCT_PLAS_KIND_QTY;
    }

    public void setDSTRCT_PLAS_KIND_QTY(float DSTRCT_PLAS_KIND_QTY) {
        this.DSTRCT_PLAS_KIND_QTY = DSTRCT_PLAS_KIND_QTY;
    }

    public float getDSTRCT_FMDRSNS_KIND_QTY() {
        return DSTRCT_FMDRSNS_KIND_QTY;
    }

    public void setDSTRCT_FMDRSNS_KIND_QTY(float DSTRCT_FMDRSNS_KIND_QTY) {
        this.DSTRCT_FMDRSNS_KIND_QTY = DSTRCT_FMDRSNS_KIND_QTY;
    }

    public float getDSTRCT_ELEPDT_KIND_QTY() {
        return DSTRCT_ELEPDT_KIND_QTY;
    }

    public void setDSTRCT_ELEPDT_KIND_QTY(float DSTRCT_ELEPDT_KIND_QTY) {
        this.DSTRCT_ELEPDT_KIND_QTY = DSTRCT_ELEPDT_KIND_QTY;
    }

    public float getDSTRCT_BATTERY_KIND_QTY() {
        return DSTRCT_BATTERY_KIND_QTY;
    }

    public void setDSTRCT_BATTERY_KIND_QTY(float DSTRCT_BATTERY_KIND_QTY) {
        this.DSTRCT_BATTERY_KIND_QTY = DSTRCT_BATTERY_KIND_QTY;
    }

    public float getDSTRCT_TIRE_KIND_QTY() {
        return DSTRCT_TIRE_KIND_QTY;
    }

    public void setDSTRCT_TIRE_KIND_QTY(float DSTRCT_TIRE_KIND_QTY) {
        this.DSTRCT_TIRE_KIND_QTY = DSTRCT_TIRE_KIND_QTY;
    }

    public float getDSTRCT_LUBRICANT_QTY() {
        return DSTRCT_LUBRICANT_QTY;
    }

    public void setDSTRCT_LUBRICANT_QTY(float DSTRCT_LUBRICANT_QTY) {
        this.DSTRCT_LUBRICANT_QTY = DSTRCT_LUBRICANT_QTY;
    }

    public float getDSTRCT_F_LIGHT_QTY() {
        return DSTRCT_F_LIGHT_QTY;
    }

    public void setDSTRCT_F_LIGHT_QTY(float DSTRCT_F_LIGHT_QTY) {
        this.DSTRCT_F_LIGHT_QTY = DSTRCT_F_LIGHT_QTY;
    }

    public float getDSTRCT_S_IRON_KIND_QTY() {
        return DSTRCT_S_IRON_KIND_QTY;
    }

    public void setDSTRCT_S_IRON_KIND_QTY(float DSTRCT_S_IRON_KIND_QTY) {
        this.DSTRCT_S_IRON_KIND_QTY = DSTRCT_S_IRON_KIND_QTY;
    }

    public float getDSTRCT_CLOTHES_KIND_QTY() {
        return DSTRCT_CLOTHES_KIND_QTY;
    }

    public void setDSTRCT_CLOTHES_KIND_QTY(float DSTRCT_CLOTHES_KIND_QTY) {
        this.DSTRCT_CLOTHES_KIND_QTY = DSTRCT_CLOTHES_KIND_QTY;
    }

    public float getDSTRCT_FARM_KIND_QTY() {
        return DSTRCT_FARM_KIND_QTY;
    }

    public void setDSTRCT_FARM_KIND_QTY(float DSTRCT_FARM_KIND_QTY) {
        this.DSTRCT_FARM_KIND_QTY = DSTRCT_FARM_KIND_QTY;
    }

    public float getDSTRCT_FURN_KIND_QTY() {
        return DSTRCT_FURN_KIND_QTY;
    }

    public void setDSTRCT_FURN_KIND_QTY(float DSTRCT_FURN_KIND_QTY) {
        this.DSTRCT_FURN_KIND_QTY = DSTRCT_FURN_KIND_QTY;
    }

    public float getDSTRCT_WTC_OIL_KIND_QTY() {
        return DSTRCT_WTC_OIL_KIND_QTY;
    }

    public void setDSTRCT_WTC_OIL_KIND_QTY(float DSTRCT_WTC_OIL_KIND_QTY) {
        this.DSTRCT_WTC_OIL_KIND_QTY = DSTRCT_WTC_OIL_KIND_QTY;
    }

    public float getDSTRCT_ETC_KIND_QTY() {
        return DSTRCT_ETC_KIND_QTY;
    }

    public void setDSTRCT_ETC_KIND_QTY(float DSTRCT_ETC_KIND_QTY) {
        this.DSTRCT_ETC_KIND_QTY = DSTRCT_ETC_KIND_QTY;
    }

    public float getDSTRCT_RSDL_KIND_QTY() {
        return DSTRCT_RSDL_KIND_QTY;
    }

    public void setDSTRCT_RSDL_KIND_QTY(float DSTRCT_RSDL_KIND_QTY) {
        this.DSTRCT_RSDL_KIND_QTY = DSTRCT_RSDL_KIND_QTY;
    }

    public float getFOOD_SUM() {
        return FOOD_SUM;
    }

    public void setFOOD_SUM(float FOOD_SUM) {
        this.FOOD_SUM = FOOD_SUM;
    }

    private float FOOD_SUM;


    protected GraphDTO(Parcel in) {
        CITY_JIDT_NM = in.readString();
        CTS_JIDT_NM = in.readString();
        DATA_TM_NM = in.readString();
        TOT_SUM = in.readFloat();
        SPEC_SUM = in.readFloat();
        COMB_SUM = in.readFloat();
        COMB_FOOD_VEGET = in.readFloat();
        COMB_PPR_KIND = in.readFloat();
        COMB_WOOD_KIND = in.readFloat();
        COMB_RUBBER_KIND = in.readFloat();
        COMB_PLAS_KIND = in.readFloat();
        COMB_ETC_ETC_KIND = in.readFloat();
        NCMB_SUM = in.readFloat();
        NCMB_GLSS_KIND = in.readFloat();
        NCMB_MET_KIND = in.readFloat();
        NCMB_SAND_KIND = in.readFloat();
        NCMB_ETC_KIND = in.readFloat();
        ETC_KIND = in.readFloat();
        DSTRCT_SUM = in.readFloat();
        DSTRCT_PPR_KIND_QTY = in.readFloat();
        DSTRCT_GLSS_KIND_QTY = in.readFloat();
        DSTRCT_CAN_KIND_QTY = in.readFloat();
        DSTRCT_SYNRSNS_KIND = in.readFloat();
        DSTRCT_PLAS_KIND_QTY = in.readFloat();
        DSTRCT_FMDRSNS_KIND_QTY = in.readFloat();
        DSTRCT_ELEPDT_KIND_QTY = in.readFloat();
        DSTRCT_BATTERY_KIND_QTY = in.readFloat();
        DSTRCT_TIRE_KIND_QTY = in.readFloat();
        DSTRCT_LUBRICANT_QTY = in.readFloat();
        DSTRCT_F_LIGHT_QTY = in.readFloat();
        DSTRCT_S_IRON_KIND_QTY = in.readFloat();
        DSTRCT_CLOTHES_KIND_QTY = in.readFloat();
        DSTRCT_FARM_KIND_QTY = in.readFloat();
        DSTRCT_FURN_KIND_QTY = in.readFloat();
        DSTRCT_WTC_OIL_KIND_QTY = in.readFloat();
        DSTRCT_ETC_KIND_QTY = in.readFloat();
        DSTRCT_RSDL_KIND_QTY = in.readFloat();
        FOOD_SUM = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(CITY_JIDT_NM);
        dest.writeString(CTS_JIDT_NM);
        dest.writeString(DATA_TM_NM);
        dest.writeFloat(TOT_SUM);
        dest.writeFloat(SPEC_SUM);
        dest.writeFloat(COMB_SUM);
        dest.writeFloat(COMB_FOOD_VEGET);
        dest.writeFloat(COMB_PPR_KIND);
        dest.writeFloat(COMB_WOOD_KIND);
        dest.writeFloat(COMB_RUBBER_KIND);
        dest.writeFloat(COMB_PLAS_KIND);
        dest.writeFloat(COMB_ETC_ETC_KIND);
        dest.writeFloat(NCMB_SUM);
        dest.writeFloat(NCMB_GLSS_KIND);
        dest.writeFloat(NCMB_MET_KIND);
        dest.writeFloat(NCMB_SAND_KIND);
        dest.writeFloat(NCMB_ETC_KIND);
        dest.writeFloat(ETC_KIND);
        dest.writeFloat(DSTRCT_SUM);
        dest.writeFloat(DSTRCT_PPR_KIND_QTY);
        dest.writeFloat(DSTRCT_GLSS_KIND_QTY);
        dest.writeFloat(DSTRCT_CAN_KIND_QTY);
        dest.writeFloat(DSTRCT_SYNRSNS_KIND);
        dest.writeFloat(DSTRCT_PLAS_KIND_QTY);
        dest.writeFloat(DSTRCT_FMDRSNS_KIND_QTY);
        dest.writeFloat(DSTRCT_ELEPDT_KIND_QTY);
        dest.writeFloat(DSTRCT_BATTERY_KIND_QTY);
        dest.writeFloat(DSTRCT_TIRE_KIND_QTY);
        dest.writeFloat(DSTRCT_LUBRICANT_QTY);
        dest.writeFloat(DSTRCT_F_LIGHT_QTY);
        dest.writeFloat(DSTRCT_S_IRON_KIND_QTY);
        dest.writeFloat(DSTRCT_CLOTHES_KIND_QTY);
        dest.writeFloat(DSTRCT_FARM_KIND_QTY);
        dest.writeFloat(DSTRCT_FURN_KIND_QTY);
        dest.writeFloat(DSTRCT_WTC_OIL_KIND_QTY);
        dest.writeFloat(DSTRCT_ETC_KIND_QTY);
        dest.writeFloat(DSTRCT_RSDL_KIND_QTY);
        dest.writeFloat(FOOD_SUM);
    }

    public static final Parcelable.Creator<GraphDTO> CREATOR = new Parcelable.Creator<GraphDTO>() {
        @Override
        public GraphDTO createFromParcel(Parcel in) {
            return new GraphDTO(in);
        }

        @Override
        public GraphDTO[] newArray(int size) {
            return new GraphDTO[size];
        }
    };
}