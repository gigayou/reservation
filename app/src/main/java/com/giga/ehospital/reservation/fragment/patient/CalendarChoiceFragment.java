package com.giga.ehospital.reservation.fragment.patient;

import android.app.ProgressDialog;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Toast;

import com.giga.ehospital.ReservationApplication;
import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.adapter.hosadmin.CalendarManageAdapter;
import com.giga.ehospital.reservation.container.NormalContainer;
import com.giga.ehospital.reservation.fragment.standard.StandardWithTobBarLayoutFragment;
import com.giga.ehospital.reservation.manager.hosadmin.CalendarDataManager;
import com.giga.ehospital.reservation.manager.patient.ReservationDataManager;
import com.giga.ehospital.reservation.model.Patient;
import com.giga.ehospital.reservation.model.User;
import com.giga.ehospital.reservation.model.hospital.Calendar;
import com.giga.ehospital.reservation.model.hospital.CalendarDao;
import com.giga.ehospital.reservation.model.hospital.Doctor;
import com.giga.ehospital.reservation.model.hospital.Hospital;
import com.giga.ehospital.reservation.model.reservation.Reservation;
import com.linxiao.framework.common.GsonParser;
import com.linxiao.framework.net.ApiResponse;
import com.linxiao.framework.rx.RxSubscriber;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindString;
import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CalendarChoiceFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.calendar_choice_recyclerview)
    RecyclerView mCalendarChoiceRecyclerView;
    @BindView(R.id.scrollview)
    NestedScrollView scrollView;
    @BindString(R.string.patient_calendar_choice_fragment_title)
    String title;
    @BindString(R.string.LOADING_MESSAGE)
    String LOADING_MESSAGE;

    private Calendar tCalendar = new Calendar();
    private String hospitalId;
    private String doctorId;
    private CalendarDao calendarDao;
    private CalendarDataManager calendarDataManager;
    private ReservationDataManager reservationDataManager;
    private List<Calendar> calendarList;

    // qmui dialog style
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    @Override
    protected String getTopBarTitle() {
        return title;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_patient_calendar_choice;
    }

    @Override
    protected void initLastCustom() {
        Hospital hospital = NormalContainer.get(NormalContainer.SELECTED_HOSPITAL);
        Doctor doctor = NormalContainer.get(NormalContainer.SELECTED_DOCTOR);
        if (hospital == null || doctor == null) {
            getActivity().onBackPressed();
            Toasty.warning(getContext(), "数据同步错误，请重新选择医生", Toasty.LENGTH_SHORT, true).show();
        }

        if (calendarDao == null)
            calendarDao = ReservationApplication.getInstances().getDaoSession().getCalendarDao();
        if (calendarDataManager == null)
            calendarDataManager = new CalendarDataManager();
        if (reservationDataManager == null)
            reservationDataManager = new ReservationDataManager();
        initData();
    }

    @Override
    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(true);
        mPullRefreshLayout.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                initData();
                mTopBar.postDelayed(() -> mPullRefreshLayout.finishRefresh(), 1500l);
            }
        });
    }

    private void initData() {
        Hospital hospital = NormalContainer.get(NormalContainer.SELECTED_HOSPITAL);
        this.hospitalId = hospital.getHospitalId();
        Doctor doctor = NormalContainer.get(NormalContainer.SELECTED_DOCTOR);
        this.doctorId = doctor.getDoctorId();
        tCalendar.setHospitalId(this.hospitalId);
        tCalendar.setDoctorId(doctorId);
        rxReceiveJsonData();
        readAllData();
        initListView();
    }

    private void initListView() {
        mCalendarChoiceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        CalendarManageAdapter adapter = new CalendarManageAdapter(getContext(), this.calendarList);

        adapter.setOnItemClickListener((itemView, pos) -> {

            Calendar calendar = this.calendarList.get(pos);
            final String[] items = new String[]{"预约", "取消"};
            List<String> options = Arrays.asList(items);
            showMenuDialog(options, (dialog, which) -> {
                switch (which) {
                    case 0:
                        Patient patient = NormalContainer.get(NormalContainer.PATIENT);
                        User user = NormalContainer.get(NormalContainer.USER);
                        if (patient == null || patient.getIdCard() == null) {
                            Toasty.warning(getContext(), "请先确认你是否添加了诊疗卡", Toasty.LENGTH_SHORT, true).show();
                            dialog.dismiss();
                            return;
                        }
                        Reservation reservation = new Reservation();

                        StringBuilder uuid = new StringBuilder();
                        String[] strSegment = UUID.randomUUID().toString().split("-");
                        for (String s : strSegment) {
                            uuid.append(s);
                        }
                        String reservationId = uuid.toString().substring(0, 20);
                        String userPhone = user.getUserPhone();
                        String patientId = patient.getPatientId();
                        String admissionId = calendar.getAdmissionId();
                        int isAdmission = 0;

                        reservation.setReservationId(reservationId);
                        reservation.setUserPhone(userPhone);
                        reservation.setPatientId(patientId);
                        reservation.setAdmissionId(admissionId);
                        reservation.setIsAdmission(isAdmission);

                        final ProgressDialog progressDialog = new ProgressDialog(getContext());

                        reservationDataManager.add(reservation)
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
                                            Toasty.success(getContext(), "预约成功", Toasty.LENGTH_SHORT, true).show();
                                        } else {
                                            Toasty.error(getContext(), response.message, Toast.LENGTH_LONG, true).show();
                                        }
                                    }
                                });
                        dialog.dismiss();
                        break;
                    case 1:
                        dialog.dismiss();
                        break;
                    default:
                        break;
                }
            });
        });
        mCalendarChoiceRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mCalendarChoiceRecyclerView.setAdapter(adapter);
    }

    private void readAllData() {
        this.calendarList = calendarDao.loadAll();
    }

    private void rxReceiveJsonData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        Map map = new HashMap();
        map.put(NormalContainer.KEY_CALENDAR, tCalendar);

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sdf.format(now);
        map.put(NormalContainer.KEY_BEGIN, nowDate);

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.add(java.util.Calendar.MONTH, 1);
        Date endDate = calendar.getTime();
        String endDateStr = sdf.format(endDate);
        map.put(NormalContainer.KEY_END, endDateStr);

        calendarDataManager.getListByDateRange(map)
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
                            dumpAllData(response.data);
                        } else {
                            Toasty.error(getContext(), response.message, Toast.LENGTH_LONG, true).show();
                        }
                    }
                });
    }

    /**
     * 接收服务端的json数据后序列化成hospital对象列表
     * 然后将这些对象存储到SQLite中
     *
     * @param json json数据
     */
    private void dumpAllData(String json) {
        JSONArray jsonArray = GsonParser.fromJSONObject(json, JSONArray.class);
        List<Calendar> calendars = GsonParser.fromJSONArray(jsonArray, Calendar.class);
        calendarDao.deleteAll();
        calendarDao.insertInTx(calendars);
    }
}
