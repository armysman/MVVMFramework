package gnnt.practice.dailypage;

import androidx.annotation.NonNull;

import gnnt.mebs.common.CommonApp;
import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;

/**********************************************************
 *  DailyApp.java  2019-07-08
 *  <p>
 *  application
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public class DailyApp  extends CommonApp {
    @NonNull
    @Override
    public Converter.Factory getHttpConverterFactory() {
        return GsonConverterFactory.create();
    }
}
