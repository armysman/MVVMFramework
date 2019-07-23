package gnnt.practice.dailypage.vo.rspvo;

import java.util.List;

import gnnt.mebs.common.vo.BaseResponse;

/**********************************************************
 *  ConfigRepVO.java  2019-07-08
 *  <p>
 *  描述
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public class ConfigRepVO extends BaseResponse {

    public List<Project> projectList;
    public List<WorkType> workTypeList;

    public class Project {

        public String projectId;
        public String projectName;
        public String managerId;
        public String managerName;
    }

    public class WorkType {
        public String optionId;
        public String optionName;
    }
}
