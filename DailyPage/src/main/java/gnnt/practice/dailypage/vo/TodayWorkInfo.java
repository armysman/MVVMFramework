package gnnt.practice.dailypage.vo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import java.util.List;

/**********************************************************
 *  TodayWorkInfo.java  2019-07-11
 *  <p>
 *  描述
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public class TodayWorkInfo {
    public Project project;
    public List<Work> workList;

    public static class Work implements Parcelable {
        public Project project;
        public String dailyId;
        public WorkType workType;
        public String content;
        public String startTime;
        public String endTime;

        public Work() {
        }

        protected Work(Parcel in) {
            dailyId = in.readString();
            content = in.readString();
            startTime = in.readString();
            endTime = in.readString();
            project = in.readParcelable(Project.class.getClassLoader());
            workType = in.readParcelable(WorkType.class.getClassLoader());
        }

        public static final Creator<Work> CREATOR = new Creator<Work>() {
            @Override
            public Work createFromParcel(Parcel in) {
                return new Work(in);
            }

            @Override
            public Work[] newArray(int size) {
                return new Work[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(dailyId);
            dest.writeString(content);
            dest.writeString(startTime);
            dest.writeString(endTime);
            dest.writeParcelable(project,flags);
            dest.writeParcelable(workType,flags);
        }
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (this.project.optionId.equals(((TodayWorkInfo) other).project.optionId)) {
            return true;
        } else {
            return false;
        }
    }
}