package gnnt.practice.dailypage.vo.reqvo;

import gnnt.mebs.common.vo.BaseRequest;

/**********************************************************
 *  UpdateReqVO.java  2019-07-09
 *  <p>
 *  描述
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public class UpdateReqVO extends BaseRequest {
    public String projectId;
    public String managerId;
    public String managerName;
    public String workTypeId;
    public String content;
    public String startTime;
    public String endTime;
    public String dailyId;
}
