package gnnt.practice.dailypage.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**********************************************************
 *  Project.java  2019-07-09
 *  <p>
 *  工作项目
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public class Project implements Parcelable {
    public String optionId;
    public String optionName;

    protected Project(Parcel in) {
        optionId = in.readString();
        optionName = in.readString();
    }

    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
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
