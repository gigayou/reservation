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
import com.giga.ehospital.reservation.adapter.patient.ReservationManageAdapter;
import com.giga.ehospital.reservation.container.NormalContainer;
import com.giga.ehospital.reservation.fragment.standard.StandardWithTobBarLayoutFragment;
import com.giga.ehospital.reservation.manager.patient.ReservationDataManager;
import com.giga.ehospital.reservation.model.User;
import com.giga.ehospital.reservation.model.reservation.Reservation;
import com.giga.ehospital.reservation.model.reservation.ReservationDao;
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

public class ReservationHistoryFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.res_history_recyclerview)
    RecyclerView mPatientResHistoryRecyclerView;
    @BindView(R.id.scrollview)
    NestedScrollView scrollView;
    @BindString(R.string.patient_res_history_fragment_title)
    String title;
    @BindString(R.string.LOADING_MESSAGE)
    String LOADING_MESSAGE;
    @BindString(R.string.wait_please)
    String WAIT_PLEASE;

    private Reservation tReservation;
    private ReservationDataManager reservationDataManager;
    private ReservationDao reservationDao;
    private List<Reservation> reservationList;

    private String userPhone;

    @Override
    protected String getTopBarTitle() {
        return title;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_patient_res_history_manage;
    }

    @Override
    protected void initLastCustom() {
        User user = NormalContainer.get(NormalContainer.USER);
        userPhone = user.getUserPhone();
        if (user == null || userPhone == null) {
            Toasty.warning(getContext(), "数据获取失败，请重试", Toasty.LENGTH_SHORT, true).show();
            getActivity().onBackPressed();
        }
        if (tReservation == null)
            tReservation = new Reservation();
        if (reservationDataManager == null)
            reservationDataManager = new ReservationDataManager();
        if (reservationDao == null)
            reservationDao = ReservationApplication.getInstances().getDaoSession().getReservationDao();
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
        rxReceiveJsonData();
        readAllData();
        initListView();
    }

    private void initListView() {
        mPatientResHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });

        ReservationManageAdapter adapter = new ReservationManageAdapter(getContext(), reservationList);

        adapter.setOnItemClickListener((itemView, pos) -> {

            Reservation reservation = reservationList.get(pos);
            final String[] items = new String[]{"查看", "取消"};
            List<String> options = Arrays.asList(items);
            showMenuDialog(options, (dialog, which) -> {
                switch (which) {
                    case 0:
                        Toasty.info(getContext(), WAIT_PLEASE, Toasty.LENGTH_SHORT, true).show();
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
        mPatientResHistoryRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mPatientResHistoryRecyclerView.setAdapter(adapter);
    }

    private void readAllData() {
        reservationList = reservationDao.loadAll();
    }

    private void rxReceiveJsonData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        tReservation.setUserPhone(userPhone);
        reservationDataManager.list(tReservation)
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
        List<Reservation> reservations = GsonParser.fromJSONArray(jsonArray, Reservation.class);
        reservationDao.deleteAll();
        reservationDao.insertInTx(reservations);
    }
}
