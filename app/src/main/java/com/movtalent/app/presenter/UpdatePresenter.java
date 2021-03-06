package com.movtalent.app.presenter;

import android.widget.Toast;

import com.azhon.appupdate.dialog.UpdateDialog;
import com.azhon.appupdate.utils.ApkUtil;
import com.azhon.appupdate.utils.LogUtil;
import com.movtalent.app.App;
import com.movtalent.app.http.ApiService;
import com.movtalent.app.http.BaseApi;
import com.movtalent.app.model.dto.UpdateDto;
import com.movtalent.app.presenter.iview.IUpdate;
import com.movtalent.app.util.ToastUtil;

/**
 * @author huangyong
 * createTime 2019-10-09
 */
public class UpdatePresenter {

    private IUpdate iUpdate;

    public UpdatePresenter(IUpdate iUpdate) {
        this.iUpdate = iUpdate;
    }

    public void getUpdate(){
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getUpdate(), new BaseApi.IResponseListener<UpdateDto>() {
                    @Override
                    public void onSuccess(UpdateDto data) {

                        //对版本进行判断，是否显示升级对话框
                        if (data.getData().getVersionCode() > ApkUtil.getVersionCode(App.getContext())) {
                            if (iUpdate!=null){
                                iUpdate.loadDone(data);
                            }
                        } else {
                            ToastUtil.showMessage("当前已是最新版本");
                        }



                    }

                    @Override
                    public void onFail() {
                    }
                }
        );
    }
    public void getRepair(){
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .checkRepair(), new BaseApi.IResponseListener<UpdateDto>() {
                    @Override
                    public void onSuccess(UpdateDto data) {

                        //对当前APP进行修复，远程接口不要修改
                        if (data.getData().getVersionCode() > ApkUtil.getVersionCode(App.getContext())) {
                            if (iUpdate!=null){
                                iUpdate.loadDone(data);
                            }
                        } else {
                            ToastUtil.showMessage("当前APP无错误");
                        }



                    }

                    @Override
                    public void onFail() {
                    }
                }
        );
    }


}
