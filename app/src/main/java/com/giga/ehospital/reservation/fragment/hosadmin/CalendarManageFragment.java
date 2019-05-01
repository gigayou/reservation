package com.giga.ehospital.reservation.fragment.hosadmin;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import com.giga.ehospital.reservation.model.hospital.Calendar;
import com.giga.ehospital.reservation.model.hospital.CalendarDao;
import com.giga.ehospital.reservation.model.hospital.Hospital;
import com.linxiao.framework.common.GsonParser;
import com.linxiao.framework.net.ApiResponse;
import com.linxiao.framework.rx.RxSubscriber;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import org.json.JSONArray;

import java.util.Arrays;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CalendarManageFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.calendar_manage_recyclerview)
    RecyclerView mCalendarManageRecyclerView;
    @BindView(R.id.scrollview)
    NestedScrollView scrollView;
    @BindString(R.string.fragment_calendar_manage_title)
    String title;
    @BindString(R.string.common_right_btn_title)
    String RIGHT_BTN_TITLE;
    @BindString(R.string.LOADING_MESSAGE)
    String LOADING_MESSAGE;

    private static Calendar tCalendar = new Calendar();
    private String hospitalId;
    private CalendarDao calendarDao;
    private CalendarDataManager calendarDataManager;
    private List<Calendar> calendarList;

    // qmui dialog style
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    @Override
    protected void initTopBar() {
        super.initTopBar();
        mTopBar.addRightTextButton(RIGHT_BTN_TITLE, mTopBar.getId()).setOnClickListener(v -> {
            startFragment(new CalendarAddFragment());
        });
    }

    @Override
    protected String getTopBarTitle() {
        return title;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hosadmin_calendar_manage;
    }

    @Override
    protected void initLastCustom() {
        if (calendarDao == null)
            calendarDao = ReservationApplication.getInstances().getDaoSession().getCalendarDao();
        if (calendarDataManager == null)
            calendarDataManager = new CalendarDataManager();
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
        Hospital hospital = NormalContainer.get(NormalContainer.HOSPITAL);
        this.hospitalId = hospital.getHospitalId();
        tCalendar.setHospitalId(this.hospitalId);
        rxReceiveJsonData();
        readAllData();
        initListView();
    }

    private void initListView() {
        mCalendarManageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        CalendarManageAdapter adapter = new CalendarManageAdapter(getContext(), this.calendarList);

        adapter.setOnItemClickListener((itemView, pos) -> {

            Calendar calendar = this.calendarList.get(pos);
            final String[] items = new String[]{"查看", "删除", "取消"};
            List<String> options = Arrays.asList(items);
            showMenuDialog(options, (dialog, which) -> {
                switch (which) {
                    case 0:
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("calendar", calendar);
                        CalendarModifyFragment calendarModifyFragment = new CalendarModifyFragment();
                        calendarModifyFragment.setArguments(bundle);
                        startFragment(calendarModifyFragment);
                        dialog.dismiss();
                        break;
                    case 1:
                        String title = "你确定删除部门医生为<" + calendar.getDepartmentName() + "-" + calendar.getDoctorName() + ">的接诊安排吗？";
                        String content = "确定删除";
                        String cancleMsg = "取消";
                        String confirmMsg = "确定";
                        boolean checked = true;
                        showConfirmMessageDialog(title, content,
                                cancleMsg, (dialog1, index) -> {
                                    dialog1.dismiss(); },
                                confirmMsg, (dialog12, index) -> {
                                    final ProgressDialog progressDialog = new ProgressDialog(getContext());
                                    calendarDataManager.delete(calendar)
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
                                                        Toasty.success(getContext(), "删除成功", Toasty.LENGTH_SHORT, true).show();
                                                    } else {
                                                        Toasty.error(getContext(), response.message, Toast.LENGTH_LONG, true).show();
                                                    }
                                                }
                                            });
                                    dialog12.dismiss();
                                    dialog.dismiss();
                                },
                                checked);
                        break;
                    case 2:
                        dialog.dismiss();
                        break;
                    default:
                        break;
                }
            });
        });
        mCalendarManageRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mCalendarManageRecyclerView.setAdapter(adapter);
    }

    private void readAllData() {
        this.calendarList = calendarDao.loadAll();
    }

    private void rxReceiveJsonData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        calendarDataManager.list(tCalendar)
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
