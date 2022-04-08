package com.outsideweather.cn.permit;

import android.app.Activity;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;


/**
 */
public class PermitCallback implements EasyPermissions.PermissionCallbacks
{
	Activity mContext;
	
	public PermitCallback(Activity context){
		mContext=context;
	}
	
	@Override
	public void onPermissionsGranted(int requestCode, List<String> perms){
	}
	
	@Override
	public void onPermissionsDenied(int requestCode, List<String> perms){
		PermitUtil.onPermsDenied(mContext, perms);
	}
	
	@Override
	public void onRequestPermissionsResult(int reqCode, String[] ps, int[] res){
	}
}
