# Lib version
```gradle
dependencies {
    implementation 'com.github.dev-chetan:BaseAndroid:1.2.5'
}
```

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

# Dialog Functions

Information Popup Dialog Single Button
```java
showInfoDialog(response.body().getMsg());
```

Information Popup Dialog Two Button With Action
```java
new DialogHelper("Are you sure you want to delete?", getContext().getString(R.string.txt_cancel), getContext().getString(R.string.txt_delete), new DialogHelper.DialogCallBack() {
                    @Override
                    public void onResult(int resultCode) {
                        if (resultCode == 1) {
                            
                        }
                    }
}, DialogStatus.DIALOG_DEFAULT).show(((AppCompatActivity) getContext()).getSupportFragmentManager(), "");
```

#Bottom Sheet Dialog

Types Of Bottom Sheet
**BottomSheetHelper.Type.multipleSelection** Multiple selection
**BottomSheetHelper.Type.singleSelection** Single selection
**BottomSheetHelper.Type.singleSearch** Single selection with search
**BottomSheetHelper.Type.multipleSearch** Multiple selection with search

```java
new BottomSheetHelper(getContext(), "" + text.getHint(), list, new BottomSheetHelper.OnBottomSheetResult() {
            @Override
            public void onResult(List<BottomSheetData> arrayList) {
            }
        }, BottomSheetHelper.Type.singleSearch);
```

# Set fields as required.
 - If you want to set asterisk sign(*) to input fields then use this method lik below
```java
   setRequired(textInputUser)
```
 - Output : Username*

 # Set cursor to last index in input field.
 ```java
    edtFirstName.setText("John");
    edtFirstName.setSelection(getSelection(edtFirstName));
 ```

 # This method is used to remove last char from string content
  ```java
     removeLastChar("Hello,"); // remove last char
     removeLastChar("Hello,,",2); // remove last no of 2 char
  ```