# Android Users Permission
    ```String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE
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
            });```
               
    Then, add to your manifest `Manifest.xml`
        ```<activity android:name="com.android.rb.helper.PermissionActivity"
            android:theme="@style/Permissions.TransparentTheme" />```
                        
# Load PDF File From ImageView 

    ```String pdfUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";
    loadNetworkPDF(pdfUrl,
        (ImageView) findViewById(R.id.iv));```
