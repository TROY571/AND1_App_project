package com.outsideweather.cn.BasePermit;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class PermitUtil
{
	public static final PermitItem[] PS={
			new PermitItem(true,Manifest.permission.ACCESS_FINE_LOCATION, "地理位置"),

	};

	public static final int REQ_PERMS=999;
	
	public static void checkPerms(Activity ctxt, List<String> perms){
		if(Build.VERSION.SDK_INT >= 23){
			if(perms==null){
				perms=new ArrayList<>();
				for(PermitItem item: PS) if(item.need) perms.add(item.name);
			}
			String[] ps=perms.toArray(new String[perms.size()]);
			boolean ok=EasyPermissions.hasPermissions(ctxt, ps);
			if(!ok){
				perms.add(Manifest.permission.READ_CONTACTS); // 首次安装时提示
				ps = perms.toArray(new String[]{});
				EasyPermissions.requestPermissions(ctxt, "为了更好的体验,请设置APP权限", REQ_PERMS, ps);
			}
		}
	}
	
	public static void onPermsResult(
            int reqCode, String[] ps, int[] res, EasyPermissions.PermissionCallbacks obj){
		EasyPermissions.onRequestPermissionsResult(reqCode, ps, res, obj);
	}
	
	public static void onPermsDenied(final Activity ctxt, List<String> perms){
		if(EasyPermissions.somePermissionPermanentlyDenied(ctxt, perms)){
			List<String> tips=new ArrayList<>();
			for(PermitItem item: PS){
				if(item.need && !TextUtils.isEmpty(item.tip)) for(String p: perms){
					if(p.equals(item.name)){
						tips.add(item.tip);
						break;
					}
				}
			}
			StringBuilder builder=new StringBuilder();
			builder.append("检测到重要权限被拒绝,为了更好的体验，请开放相关权限");
			for(String item : tips){
				if(builder.length()>0) builder.append("\n");
				builder.append(tips);
			}
			String msg=builder.toString();
			new AlertDialog.Builder(ctxt)
					.setCancelable(false)
					.setMessage(msg)
					.setNegativeButton("退出", null)
					.setPositiveButton("去设置", new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which){
							try{
								Uri uri= Uri.fromParts("package", ctxt.getPackageName(), null);
								Intent intent=new Intent(
										Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
								intent.setData(uri);
								ctxt.startActivity(intent);
							}catch(Throwable ex){
								ex.printStackTrace();
							}
						}
					})
					.show();
		}
	}

	/**
	 * 检测权限 相机和相册的权限
	 *
	 * @return
	 */
	public static  boolean checkPhonePermission(Activity activity) {
		try {
			List<String> permissionsList = new ArrayList<>();
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				//相机权限
				if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
					permissionsList.add(Manifest.permission.CAMERA);
				//录音权限
				if (ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
					permissionsList.add(Manifest.permission.RECORD_AUDIO);
				//写存储权限
				if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
					permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

				if (permissionsList.size() > 0) {
					ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]), 100);
					return false;
				}
			}
		} catch (Exception e) {
			Log.i("QCS", "异常:" + e.getMessage());
		}
		return true;
	}

	/**
	 * 检测地理位置权限
	 *
	 * @return
	 */
	public static  boolean checkPositionPermission(Activity activity) {
		try {
			List<String> permissionsList = new ArrayList<>();
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				//相机权限
				if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
					permissionsList.add(Manifest.permission.ACCESS_FINE_LOCATION);
				if (permissionsList.size() > 0) {
					ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]), 100);
					return false;
				}
			}
		} catch (Exception e) {
			Log.i("QCS", "异常:" + e.getMessage());
		}
		return true;
	}

	public static boolean checkPermission(Context context, String string) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;
		if (ContextCompat.checkSelfPermission(context, string) != PackageManager.PERMISSION_GRANTED) {
			return false;
		}
		return true;
	}

//	public static void showDialog(Context context, String msg) {
//		AlertConfirmDialog.confirm(context)
//				.setContent(msg)
//				.setCancelBtnText("取消")
//				.setConfirmBtnText("设置")
//				.setOnClickListener((AlertConfirmDialog dialog, boolean confirm) -> {
//					if (!confirm) return;
//					AppUtil.gotoAppSettingActivity(context);
//				}).show();
//	}

}
