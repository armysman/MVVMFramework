package gnnt.practice.dailypage.vo.reqvo;

import gnnt.mebs.common.vo.BaseRequest;

/**********************************************************
 *  HistoryReqVO.java  2019-07-09
 *  <p>
 *  描述
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public class HistoryReqVO extends BaseRequest {
    public int pageNO;
    public int pageSize;
    public String startDate;
    public String endDate;
}
