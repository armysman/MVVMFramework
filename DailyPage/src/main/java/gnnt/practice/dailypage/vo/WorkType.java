package gnnt.practice.dailypage.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**********************************************************
 *  WorkType.java  2019-07-09
 *  <p>
 *  工作类型
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public class WorkType implements Parcelable {
    public String optionId;
    public String optionName;

    protected WorkType(Parcel in) {
        optionId = in.readString();
        optionName = in.readString();
    }

    public static final Creator<WorkType> CREATOR = new Creator<WorkType>() {
        @Override
        public WorkType createFromParcel(Parcel in) {
            return new WorkType(in);
        }

        @Override
        public WorkType[] newArray(int size) {
            return new WorkType[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(optionId);
        dest.writeString(optionName);
    }
}
