package gnnt.practice.dailypage.vo;

import androidx.annotation.Nullable;

import java.util.List;

/**********************************************************
 *  HistoryWorkInfo.java  2019-07-11
 *  <p>
 *  描述
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public class HistoryWorkInfo {

    public String date;
    public String week;
    public List<HisWork> hisWorkList;

    public class HisWork {
        public String dailyId;
        public WorkType workType;
        public Project project;
        public String content;
        public String startTime;
        public String endTime;
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (date.equals(((HistoryWorkInfo) other).date)) {
            return true;
        } else {
            return false;
        }
    }
}
