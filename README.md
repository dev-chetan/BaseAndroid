#Base Classes


# Android Users Permission
```java
String[] perms = {Manifest.permission.CALL_PHONE};
        PermissionUtil.permission(((AppCompatActivity) context), perms, new PermissionHandler() {
            @Override
            public void onGranted() {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                context.startActivity(intent);
            }

            @Override
            public void onDenied() {
                toast(context.getString(R.string.txt_permission_deny), context);
            }
        });
```   
Then, add to your manifest `Manifest.xml`
```xml
        <activity android:name="com.android.rb.helper.PermissionActivity"
            android:theme="@style/Permissions.TransparentTheme" />
```
                        
# Load Image Functions

Load PDF form ImageView
```java
String pdfUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";
loadNetworkPDF(pdfUrl, (ImageView) findViewById(R.id.iv));
```

Load Image form ImageView
```java
String imageUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.png";

loadNetworkImage(imageUrl, (ImageView) findViewById(R.id.iv));
loadNetworkProfile(imageUrl, (ImageView) findViewById(R.id.iv));

String localImageUrl = "<Local Image Path>";
oadStorageImage("", (ImageView) findViewById(R.id.iv));
```
