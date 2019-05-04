package com.giga.ehospital.reservation.fragment.patient;

import android.app.ProgressDialog;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.Toast;

import com.giga.ehospital.ReservationApplication;
import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.adapter.patient.PatientManageAdapter;
import com.giga.ehospital.reservation.container.NormalContainer;
import com.giga.ehospital.reservation.fragment.standard.StandardWithTobBarLayoutFragment;
import com.giga.ehospital.reservation.manager.patient.PatientDataManager;
import com.giga.ehospital.reservation.model.Patient;
import com.giga.ehospital.reservation.model.PatientDao;
import com.giga.ehospital.reservation.model.User;
import com.linxiao.framework.common.GsonParser;
import com.linxiao.framework.net.ApiResponse;
import com.linxiao.framework.rx.RxSubscriber;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import org.json.JSONArray;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import butterknife.BindString;
import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PatientManageFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.patient_manage_recyclerview)
    RecyclerView mPatientManageRecyclerView;
    @BindView(R.id.scrollview)
    NestedScrollView scrollView;
    @BindString(R.string.fragment_patient_manage_title)
    String title;
    @BindString(R.string.add_idcard_title)
    String dialogTitle;
    @BindString(R.string.common_right_btn_title)
    String RIGHT_BTN_TITLE;
    @BindString(R.string.LOADING_MESSAGE)
    String LOADING_MESSAGE;

    private List<Patient> patientList;

    private Patient tPatient = new Patient();
    private String userId;

    private PatientDataManager patientDataManager;
    private PatientDao patientDao;

    // qmui dialog style
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    @Override
    protected void initTopBar() {
        super.initTopBar();
        Patient patient =  NormalContainer.get(NormalContainer.PATIENT);
        if (patient == null || patient.getIdCard() == null) {
            mTopBar.addRightTextButton(RIGHT_BTN_TITLE, mTopBar.getId()).setOnClickListener(v -> {
                // 添加诊疗卡
                showEditTextDialog();
            });
        }
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

    @Override
    protected String getTopBarTitle() {
        return title;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_patient_patient_manage;
    }

    @Override
    protected void initLastCustom() {
        if (patientDataManager == null) {
            patientDataManager = new PatientDataManager();
        }
        if (patientDao == null) {
            patientDao = ReservationApplication.getInstances().getDaoSession().getPatientDao();
        }
        initData();
    }

    private void initData() {
        rxReceiveJsonData();
        readAllData();
        initListView();
    }

    private void initListView() {
        mPatientManageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        PatientManageAdapter adapter = new PatientManageAdapter(getContext(), patientList);

        adapter.setOnItemClickListener((itemView, pos) -> {

            Patient patient = patientList.get(pos);
            final String[] items = new String[]{"删除", "取消"};
            List<String> options = Arrays.asList(items);
            showMenuDialog(options, (dialog, which) -> {
                switch (which) {
                    case 0:
                        String title = "你确定删除卡号为<" + patient.getIdCard() + ">的诊疗卡吗？";
                        String content = "确定删除";
                        String cancleMsg = "取消";
                        String confirmMsg = "确定";
                        boolean checked = true;
                        showConfirmMessageDialog(title, content,
                                cancleMsg, (dialog1, index) -> {
                                    dialog1.dismiss(); },
                                confirmMsg, (dialog12, index) -> {
                                    final ProgressDialog progressDialog = new ProgressDialog(getContext());
                                    patientDataManager.delete(patient)
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
                                                        NormalContainer.put(NormalContainer.PATIENT, null);
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
                    case 1:
                        dialog.dismiss();
                        break;
                    default:
                        break;
                }
            });
        });
        mPatientManageRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mPatientManageRecyclerView.setAdapter(adapter);
    }

    private void rxReceiveJsonData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        User user = NormalContainer.get(NormalContainer.USER);
        String userId = user.getUserPhone();
        tPatient.setUserId(userId);
        patientDataManager.list(tPatient)
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

    private void readAllData() {
         patientList = patientDao.loadAll();
    }

    /**
     * 接收服务端的json数据后序列化成hospital对象列表
     * 然后将这些对象存储到SQLite中
     *
     * @param json json数据
     */
    private void dumpAllData(String json) {
        JSONArray jsonArray = GsonParser.fromJSONObject(json, JSONArray.class);
        List<Patient> patients = GsonParser.fromJSONArray(jsonArray, Patient.class);
        patientDao.deleteAll();
        patientDao.insertInTx(patients);
    }

    /**
     * 点击新建备份按钮后，显示的dialog
     */
    private void showEditTextDialog() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
        builder.setTitle(dialogTitle)
                .setPlaceholder("请输入诊疗卡号")
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("取消", (dialog, index) -> dialog.dismiss())
                .addAction("确定", (dialog, index) -> {
                    CharSequence text = builder.getEditText().getText();
                    if (text != null && text.length() > 0) {
                        addCard(text.toString().trim());
                        dialog.dismiss();
                    } else {
                        Toasty.info(getContext(), "请输入诊疗卡号", Toasty.LENGTH_SHORT, true).show();
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    private void addCard(String cardId) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        Patient patient = new Patient();

        StringBuilder sb = new StringBuilder();
        String[] uuids = UUID.randomUUID().toString().split("-");
        for (String uuid : uuids) {
            sb.append(uuid);
        }
        String patientId = sb.toString().substring(0,20);

        User user = NormalContainer.get(NormalContainer.USER);
        String userId = user.getUserPhone();

        patient.setPatientId(patientId);
        patient.setUserId(userId);
        patient.setIdCard(cardId);
        patient.setCardType("0");
        patient.setRelation("0");
        String patientName = user.getUserName();
        if (patientName != null)
            patient.setPatientName(user.getUserName());
        else
            patient.setPatientName("temp");
        patientDataManager.add(patient)
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
                            NormalContainer.put(NormalContainer.PATIENT, patient);
                            initData();
                            Toasty.success(getContext(), "添加成功", Toasty.LENGTH_SHORT, true).show();
                        } else {
                            Toasty.error(getContext(), response.message, Toast.LENGTH_LONG, true).show();
                        }
                    }
                });
    }
}
