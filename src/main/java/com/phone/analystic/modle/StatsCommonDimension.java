package com.phone.analystic.modle;

import com.phone.analystic.modle.base.*;
import com.phone.common.KpiType;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

/**
 * @ClassName StatsCommonDimension
 * @Author lyd
 * @Date $ $
 * @Vesion 1.0
 * @Description 公共维度类
 **/
public class StatsCommonDimension extends StatsBaseDimension {
    public DateDimension dateDimension = new DateDimension();
    public PlatformDimension platformDimension = new PlatformDimension();
    public KpiDimension kpiDimension = new KpiDimension();

    public StatsCommonDimension(){}

    public StatsCommonDimension(DateDimension dateDimension, PlatformDimension platformDimension, KpiDimension kpiDimension) {
        this.dateDimension = dateDimension;
        this.platformDimension = platformDimension;
        this.kpiDimension = kpiDimension;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        this.dateDimension.write(dataOutput);
        this.platformDimension.write(dataOutput);
        this.kpiDimension.write(dataOutput);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.dateDimension.readFields(dataInput);
        this.platformDimension.readFields(dataInput);
        this.kpiDimension.readFields(dataInput);
    }

    /**
     * 克隆一个实例
     * @param dimension
     * @return
     */
    public static StatsCommonDimension clone(StatsCommonDimension dimension){
        KpiDimension kpi = new KpiDimension(dimension.kpiDimension.getKpiName());
        PlatformDimension platform = new PlatformDimension(dimension.platformDimension.getPlatformName());
        DateDimension date = new DateDimension(dimension.dateDimension.getYear(),
                dimension.dateDimension.getSeason(),
                dimension.dateDimension.getMonth(),
                dimension.dateDimension.getWeek(),
                dimension.dateDimension.getDay(),
                dimension.dateDimension.getType(),
                dimension.dateDimension.getCalendar());
        return new StatsCommonDimension(date,platform,kpi);
    }

    @Override
    public int compareTo(BaseDimension o) {
        if(this == o){
            return 0;
        }

        StatsCommonDimension other = (StatsCommonDimension) o;
        int tmp = this.dateDimension.compareTo(other.dateDimension);
        if(tmp != 0){
            return tmp;
        }
        tmp = this.platformDimension.compareTo(other.platformDimension);
        if(tmp != 0){
            return tmp;
        }
        return this.kpiDimension.compareTo(kpiDimension);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatsCommonDimension that = (StatsCommonDimension) o;
        return Objects.equals(dateDimension, that.dateDimension) &&
                Objects.equals(platformDimension, that.platformDimension) &&
                Objects.equals(kpiDimension, that.kpiDimension);
    }

    @Override
    public int hashCode() {

        return Objects.hash(dateDimension, platformDimension, kpiDimension);
    }

    public DateDimension getDateDimension() {
        return dateDimension;
    }

    public void setDateDimension(DateDimension dateDimension) {
        this.dateDimension = dateDimension;
    }

    public PlatformDimension getPlatformDimension() {
        return platformDimension;
    }

    public void setPlatformDimension(PlatformDimension platformDimension) {
        this.platformDimension = platformDimension;
    }

    public KpiDimension getKpiDimension() {
        return kpiDimension;
    }

    public void setKpiDimension(KpiDimension kpiDimension) {
        this.kpiDimension = kpiDimension;
    }
}