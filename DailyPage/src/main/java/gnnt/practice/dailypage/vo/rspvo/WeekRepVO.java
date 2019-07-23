package gnnt.practice.dailypage.vo.rspvo;

import java.util.List;

import gnnt.mebs.common.vo.BaseResponse;
import gnnt.practice.dailypage.vo.Project;
import gnnt.practice.dailypage.vo.WorkType;

/**********************************************************
 *  WeekRepVO.java  2019-07-09
 *  <p>
 *  描述
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public class WeekRepVO extends BaseResponse {
    public List<ThisWeek> thisWeek;
    public List<LastWeek> lastWeek;

    public class ThisWeek{
        public String dailyId;
        public WorkType workType;
        public Project project;
        public String content;
        public String startTime;
        public String endTime;
    }

    public class LastWeek{
        public String dailyId;
        public WorkType workType;
        public Project project;
        public String content;
        public String startTime;
        public String endTime;
    }
}
