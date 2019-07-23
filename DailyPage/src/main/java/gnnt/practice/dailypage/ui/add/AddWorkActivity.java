package gnnt.practice.dailypage.ui.add;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import gnnt.mebs.base.component.BaseActivity;
import gnnt.mebs.common.RouteMap;
import gnnt.practice.dailypage.MemeoryData;
import gnnt.practice.dailypage.R;
import gnnt.practice.dailypage.ui.current.CurrentWorkFragment;
import gnnt.practice.dailypage.vo.TodayWorkInfo;
import gnnt.practice.dailypage.vo.reqvo.AddReqVO;
import gnnt.practice.dailypage.vo.reqvo.UpdateReqVO;
import gnnt.practice.dailypage.vo.rspvo.ConfigRepVO;

/**********************************************************
 *  AddWorkActivity.java  2019-07-09
 *  <p>
 *  描述
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/

@Route(path = RouteMap.DailyPage.ADD)
public class AddWorkActivity extends BaseActivity<AddWorkViewModel> {
    public static final String TAG = "AddWorkActivity";

    @BindView(R.id.project)
    TextView project;
    @BindView(R.id.spin_project)
    Spinner spinProject;
    @BindView(R.id.work_type)
    TextView workType;
    @BindView(R.id.spin_work_type)
    Spinner spinWorkType;
    @BindView(R.id.start)
    TextView start;
    @BindView(R.id.end)
    TextView end;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.submit)
    ImageButton submit;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /**
     * 当前工作传入并编辑
     */
    @Autowired
    public TodayWorkInfo.Work work;

    @Autowired
    public String test;

    private String tag = "";

    private ArrayAdapter<String> mProjectAdapter;
    private ArrayAdapter<String> mWorkTypeAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_add;
    }

    @NonNull
    @Override
    protected Class<? extends AddWorkViewModel> getViewModelClass() {
        return AddWorkViewModel.class;
    }

    @Override
    protected void setupView() {
        super.setupView();
        mProjectAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        mWorkTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        mProjectAdapter.setDropDownViewResource(R.layout.item_spinner);
        mWorkTypeAdapter.setDropDownViewResource(R.layout.item_spinner);
        spinProject.setAdapter(mProjectAdapter);
        spinWorkType.setAdapter(mWorkTypeAdapter);

        setSupportActionBar(toolbar);
//        toolbar.setTitle("添加工作");
        toolbar.setNavigationOnClickListener(view -> {
            setResult(RESULT_OK);
            finish();
        });
    }

    @Override
    protected void setupViewModel(@NonNull AddWorkViewModel viewModel) {
        super.setupViewModel(viewModel);
        initProject();
        //查询当前工作日是否已添加工作,反填
        mViewModel.queryCurrent();
        mViewModel.queryConfig();

        mViewModel.getLastWork().observe(this, dailyWork -> {
            if (dailyWork != null) {
//                project.setText(dailyWork.project.optionName);
//                workType.setText(dailyWork.workType.optionName);
                start.setText(dailyWork.endTime);
            }
        });

        mViewModel.getConfig().observe(this, configRepVO -> {
            if (configRepVO != null) {
//                if (TextUtils.isEmpty(project.getText().toString())) {
//                    project.setText(configRepVO.projectList.get(0).projectName);
//                    workType.setText(configRepVO.workTypeList.get(0).optionName);
//                }
                mProjectAdapter.addAll(mViewModel.getFormatData(configRepVO.projectList));
                mWorkTypeAdapter.addAll(mViewModel.getFormatWorkData(configRepVO.workTypeList));
                if (work != null) {
                    startHour = Integer.parseInt(work.startTime.substring(10, 12));
                    startMinute = Integer.parseInt(work.endTime.substring(13, 15));
                    content.setText(work.content);
                    endHour = Integer.parseInt(work.endTime.substring(10, 12));
                    endMinute = Integer.parseInt(work.endTime.substring(13, 15));
                    start.setText(work.startTime.substring(9, 15));
                    end.setText(work.endTime.substring(9, 15));
                    List<String> project = mViewModel.getFormatData(configRepVO.projectList);
                    for (int i = 0; i < project.size(); i++) {
                        String projectStr = project.get(i);
                        if (work.project.optionName.equals(projectStr)) {
                            spinProject.setSelection(i);
                        }
                    }
                    List<String> workType = mViewModel.getFormatWorkData(configRepVO.workTypeList);
                    for (int i = 0; i < workType.size(); i++) {
                        String workTypeStr = workType.get(i);
                        if (work.workType.optionName.equals(workTypeStr)) {
                            spinWorkType.setSelection(i);
                        }
                    }
                }
            }
        });
        mViewModel.getSuccess().observe(this, success -> {
            if (success) {
                if (tag.equals(CurrentWorkFragment.ADD)) {
                    showMessage("添加成功");
                    content.setText("");
                } else {
                    showMessage("更新成功");
                    content.setText("");
                }

            }
        });
    }

    private void initProject() {
        Intent intent = getIntent();
        tag = intent.getStringExtra("tag");
        Bundle bundle = intent.getBundleExtra("work");
        if (bundle != null) {
            work = bundle.getParcelable("work");
        }
    }

    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private ConfigRepVO.Project mCurProject;
    private ConfigRepVO.WorkType mCurWorkType;


    @OnClick(R.id.start)
    public void onStartTimeOnClick() {
        new TimePickerDialog(this,
                (TimePicker view, int hourOfDay, int minute) -> {
                    startHour = hourOfDay;
                    startMinute = minute;
                    String curHour;
                    String curMinute;
                    if (hourOfDay < 10) {
                        curHour = "0" + hourOfDay;
                    } else {
                        curHour = "" + hourOfDay;
                    }
                    if (minute < 10) {
                        curMinute = "0" + minute;
                    } else {
                        curMinute = "" + minute;
                    }
                    start.setText(curHour + ":" + curMinute);
                }, startHour, startMinute, true).show();
    }

    @OnClick(R.id.end)
    public void onEndTimeOnClick() {
        new TimePickerDialog(this,
                //AlertDialog.THEME_HOLO_LIGHT,
                (TimePicker view, int hourOfDay, int minute) -> {
                    endHour = hourOfDay;
                    endMinute = minute;
                    String curHour;
                    String curMinute;
                    if (hourOfDay < 10) {
                        curHour = "0" + hourOfDay;
                    } else {
                        curHour = "" + hourOfDay;
                    }
                    if (minute < 10) {
                        curMinute = "0" + minute;
                    } else {
                        curMinute = "" + minute;
                    }
                    end.setText(curHour + ":" + curMinute);
                }, endHour, endMinute, true).show();
    }


    @OnItemSelected(R.id.spin_project)
    public void onProjectSpinOnclick(int position) {
        mCurProject = mViewModel.getConfig().getValue().projectList.get(position);
    }

    @OnItemSelected(R.id.spin_work_type)
    public void onWorkTypeSpinOnclick(int position) {
        mCurWorkType = mViewModel.getConfig().getValue().workTypeList.get(position);
    }


    @OnClick(R.id.submit)
    public void submit() {
       /* if (TextUtils.isEmpty(project.getText().toString())) {
            showMessage("请选择所属项目");
            return;
        }
        if (TextUtils.isEmpty(workType.getText().toString())) {
            showMessage("请选择工作性质");
            return;
        }*/
        if (TextUtils.isEmpty(start.getText().toString())) {
            showMessage("请选择开始时间");
            return;
        }
        if (TextUtils.isEmpty(end.getText().toString())) {
            showMessage("请选择结束时间");
            return;
        }
        if (TextUtils.isEmpty(content.getText().toString())) {
            showMessage("请填写工作类容");
            return;
        }
        if (tag.equals(CurrentWorkFragment.ADD)) {
            AddReqVO reqVO = new AddReqVO();
            reqVO.projectId = mCurProject.projectId;
            reqVO.managerId = mCurProject.managerId;
            reqVO.managerName = mCurProject.managerName;
            reqVO.workTypeId = mCurWorkType.optionId;
            reqVO.startTime = start.getText().toString();
            reqVO.endTime = end.getText().toString();
            reqVO.content = content.getText().toString();
            reqVO.cookie = MemeoryData.getInstance().getCookie();
            mViewModel.getReqVO().setValue(reqVO);
            mViewModel.addWork();
        } else if (tag.equals(CurrentWorkFragment.UPDATE)) {
            //更新
            UpdateReqVO reqVO = new UpdateReqVO();
            reqVO.projectId = mCurProject.projectId;
            reqVO.managerId = mCurProject.managerId;
            reqVO.managerName = mCurProject.managerName;
            reqVO.workTypeId = mCurWorkType.optionId;
            reqVO.startTime = start.getText().toString();
            reqVO.endTime = end.getText().toString();
            reqVO.content = content.getText().toString();
            reqVO.cookie = MemeoryData.getInstance().getCookie();
            reqVO.dailyId = work.dailyId;
            mViewModel.getUpdateReqVO().setValue(reqVO);
            mViewModel.updateWork();
        }
    }
}
