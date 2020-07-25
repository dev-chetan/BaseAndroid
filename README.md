# Android Users Permission
            String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.CAMERA};

            PermissionUtil.permission(activity, perms, new PermissionHandler() {
                @Override
                public void onGranted() {
                    openGallery(activity, false, isCrop, context);
                }

                @Override
                public void onDenied() {
                    toast(context.getString(R.string.txt_permission_deny), context);
                }
            });

             # Add in manifest 
             <activity
                        android:name="com.android.rb.helper.PermissionActivity"
                        android:theme="@style/Permissions.TransparentTheme" />
                         
