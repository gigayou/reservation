package com.giga.ehospital.reservation.fragment.hosadmin;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.container.NormalContainer;
import com.giga.ehospital.reservation.fragment.standard.StandardWithTobBarLayoutFragment;
import com.giga.ehospital.reservation.manager.hosadmin.CalendarDataManager;
import com.giga.ehospital.reservation.manager.hosadmin.DoctorDataManager;
import com.giga.ehospital.reservation.model.hospital.Doctor;
import com.giga.ehospital.reservation.model.hospital.Hospital;
import com.linxiao.framework.common.GsonParser;
import com.linxiao.framework.net.ApiResponse;
import com.linxiao.framework.rx.RxSubscriber;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CalendarAddFragment  extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.scrollview)
    NestedScrollView scrollView;
    @BindView(R.id.tv_visiting_docotr)
    TextView tvVisitingDoctor;
    @BindView(R.id.tv_visiting_date)
    TextView tvVisitingDate;
    @BindView(R.id.tv_visiting_period)
    TextView tvVisitingPeriod;
    @BindView(R.id.et_visiting_num)
    EditText etVisitingNum;
    @BindView(R.id.tv_visiting_rnum)
    TextView tvVisitingRnum;
    @BindView(R.id.et_visiting_rnum)
    EditText etVisitingRnum;
    @BindView(R.id.llt_visiting_rnum)
    LinearLayout lltVisitingRnum;
    @BindView(R.id.tv_visiting_valid)
    TextView tvVisitingValid;
    @BindString(R.string.fragment_calendar_add_title)
    String title;
    @BindString(R.string.LOADING_MESSAGE)
    String LOADING_MESSAGE;

    private Doctor tDoctor = new Doctor();

    private DoctorDataManager doctorDataManager;
    private CalendarDataManager calendarDataManager;
    private String hospitalId;

    private List<Doctor> doctorOptions;
    private List<String> periodOptions = new ArrayList<>();
    private List<String> validOptions = new ArrayList<>();

    private Integer sDoctorIndex;
    private Integer sPeriodIndex;
    private Integer sValidIndex;
    private String sVisitingDate;

    private TimePickerView pvCustomLunar;


    @Override
    protected String getTopBarTitle() {
        return title;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hosadmin_calendar_add;
    }


    @Override
    protected void initMiddleCustom() {
        etVisitingRnum.setEnabled(false);
    }

    @Override
    protected void initLastCustom() {
        if (doctorDataManager == null)
            doctorDataManager = new DoctorDataManager();
        if (calendarDataManager == null)
            calendarDataManager = new CalendarDataManager();
        Hospital hospital = NormalContainer.get(NormalContainer.HOSPITAL);
        hospitalId = hospital.getHospitalId();
        tDoctor = new Doctor();
        tDoctor.setHospitalId(hospitalId);

        initDoctorOptions();
        initPeriodOptions();
        initValidOptions();
        initLunarPickerView();
    }

    @OnClick({R.id.tv_visiting_docotr, R.id.tv_visiting_date, R.id.tv_visiting_period, R.id.tv_visiting_valid, R.id.btn_save})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_visiting_docotr:
                showDoctorPickerView();
                break;
            case R.id.tv_visiting_date:
                pvCustomLunar.show();
                break;
            case R.id.tv_visiting_period:
                showVisitingPeriodPickerView();
                break;
            case R.id.tv_visiting_valid:
                showVisitingValidPickerView();
                break;
            case R.id.btn_save:
                pushInfo();
                break;
        }
    }

    private void pushInfo() {
        com.giga.ehospital.reservation.model.hospital.Calendar calendar = new com.giga.ehospital.reservation.model.hospital.Calendar();
        // hospitalId
        Hospital hospital = NormalContainer.get(NormalContainer.HOSPITAL);
        String hospitalId = hospital.getHospitalId();
        calendar.setHospitalId(hospitalId);
        // doctor id
        if (sDoctorIndex == null) {
            Toasty.warning(getContext(), "请选择接诊医生", Toasty.LENGTH_SHORT, true).show();
            return;
        }
        Doctor doctor = doctorOptions.get(sDoctorIndex);
        String doctorId = doctor.getDoctorId();
        calendar.setDoctorId(doctorId);
        // visiting date
        String visitingDate = tvVisitingDate.getText().toString().trim();
        if (StringUtils.isBlank(visitingDate)) {
            Toasty.warning(getContext(), "请选择接诊日期", Toasty.LENGTH_SHORT, true).show();
            return;
        }
        calendar.setAdmissionDate(visitingDate);
        // period
        if (sPeriodIndex == null) {
            Toasty.warning(getContext(), "请选择接诊时段", Toasty.LENGTH_SHORT, true).show();
            return;
        }
        calendar.setAdmissionPeriod(sPeriodIndex + "");
        // visiting num & remaining num
        Integer num = 0;
        try {
            num = Integer.valueOf(etVisitingNum.getText().toString().trim());
        } catch (Exception e) {
            Toasty.error(getContext(), "请在接诊人数上输入有效数字", Toasty.LENGTH_SHORT, true).show();
            Log.e("error", "cast exception");
            return;
        }
        if (num < 0) {
            Toasty.warning(getContext(), "接诊人数不能为负数", Toasty.LENGTH_SHORT, true).show();
            return;
        }
        calendar.setAdmissionNum(num);
        calendar.setRemainingNum(num);
        // valid
        if (sValidIndex == null) {
            Toasty.warning(getContext(), "请选择接诊有效性", Toasty.LENGTH_SHORT, true).show();
            return;
        }
        calendar.setIsValid(sValidIndex);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        calendarDataManager.add(calendar)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    progressDialog.setMessage(LOADING_MESSAGE);
                    progressDialog.show();
                })
                .doOnComplete(() -> progressDialog.dismiss())
                .subscribe(new RxSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        ApiResponse response = GsonParser.fromJSONObject(s, ApiResponse.class);
                        if (response.success()) {
                            getActivity().onBackPressed();
                            Toasty.success(getContext(), "添加成功", Toasty.LENGTH_SHORT, true).show();
                        } else {
                            Toasty.error(getContext(), response.message, Toast.LENGTH_LONG, true).show();
                        }
                    }
                });
    }

    /**
     * 农历时间已扩展至 ： 1900 - 2100年
     */
    private void initLunarPickerView() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();

        int year = startDate.get(Calendar.YEAR);
        int month = startDate.get(Calendar.MONTH);
        int day = startDate.get(Calendar.DAY_OF_MONTH);
        startDate.set(year, month, day);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2069, 2, 28);
        //时间选择器 ，自定义布局
        pvCustomLunar = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                sVisitingDate = getTime(date);
                tvVisitingDate.setText(sVisitingDate);
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_lunar, new CustomListener() {

                    @Override
                    public void customLayout(final View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.returnData();
                                pvCustomLunar.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.dismiss();
                            }
                        });
                        //公农历切换
                        CheckBox cb_lunar = (CheckBox) v.findViewById(R.id.cb_lunar);
                        cb_lunar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                pvCustomLunar.setLunarCalendar(!pvCustomLunar.isLunarCalendar());
                                //自适应宽
                                setTimePickerChildWeight(v, isChecked ? 0.8f : 1f, isChecked ? 1f : 1.1f);
                            }
                        });

                    }

                    /**
                     * 公农历切换后调整宽
                     * @param v
                     * @param yearWeight
                     * @param weight
                     */
                    private void setTimePickerChildWeight(View v, float yearWeight, float weight) {
                        ViewGroup timePicker = (ViewGroup) v.findViewById(R.id.timepicker);
                        View year = timePicker.getChildAt(0);
                        LinearLayout.LayoutParams lp = ((LinearLayout.LayoutParams) year.getLayoutParams());
                        lp.weight = yearWeight;
                        year.setLayoutParams(lp);
                        for (int i = 1; i < timePicker.getChildCount(); i++) {
                            View childAt = timePicker.getChildAt(i);
                            LinearLayout.LayoutParams childLp = ((LinearLayout.LayoutParams) childAt.getLayoutParams());
                            childLp.weight = weight;
                            childAt.setLayoutParams(childLp);
                        }
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build();
    }

    //可根据需要自行截取数据显示
    private String getTime(Date date) {
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private void initValidOptions() {
        validOptions.add("有效");
        validOptions.add("无效");
    }

    private void initPeriodOptions() {
        periodOptions.add("上午");
        periodOptions.add("下午");
        periodOptions.add("晚上");
    }

    private void initDoctorOptions() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        doctorDataManager.list(tDoctor)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    progressDialog.setMessage(LOADING_MESSAGE);
                    progressDialog.show();
                })
                .doOnComplete(() -> progressDialog.dismiss())
                .subscribe(new RxSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        ApiResponse response = GsonParser.fromJSONObject(s, ApiResponse.class);
                        if (response.success()) {
                            transform2DoctorList(response.data);
                        } else {
                            Toasty.error(getContext(), response.message, Toast.LENGTH_LONG, true).show();
                        }
                        onComplete();
                    }
                });
    }

    private void transform2DoctorList(String json) {
        JSONArray jsonArray = GsonParser.fromJSONObject(json, JSONArray.class);
        this.doctorOptions = GsonParser.fromJSONArray(jsonArray, Doctor.class);
    }

    private void showDoctorPickerView() {

        if (doctorOptions.size() == 0) {
            // 禁用选择医生tv控件
            tvVisitingDoctor.setEnabled(false);
            // 提醒
            Toasty.info(getContext(), "当前医院无医生信息，请先添加", Toasty.LENGTH_SHORT, true).show();
            return;
        }

        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String displayName = "";
                if (doctorOptions.size() > 0) {
                    String doctorName = doctorOptions.get(options1).getDoctorName();
                    String typeName = doctorOptions.get(options1).getTypeName();
                    if (StringUtils.isNotBlank(typeName))
                        displayName = typeName + "-" + doctorName;
                    else
                        displayName = doctorName;
                }

                String name =  doctorOptions.size() > 0 ? displayName : "";
                tvVisitingDoctor.setText(name);
                sDoctorIndex = options1;
            }
        })
                .setTitleText("医生选择选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(doctorOptions);
        pvOptions.show();
    }

    private void showVisitingValidPickerView() {

        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String valid = validOptions.size() > 0 ?
                        validOptions.get(options1) : "";
                tvVisitingValid.setText(valid);
                sValidIndex = options1;
            }
        })
                .setTitleText("有效性选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(validOptions);
        pvOptions.show();
    }

    private void showVisitingPeriodPickerView() {

        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String period = periodOptions.size() > 0 ?
                        periodOptions.get(options1) : "";
                tvVisitingPeriod.setText(period);
                sPeriodIndex = options1;
            }
        })
                .setTitleText("时间段选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(periodOptions);
        pvOptions.show();
    }
}
