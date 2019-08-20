package com.example.personalapplication.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.personalapplication.MyApplication;
import com.example.personalapplication.R;
import com.example.personalapplication.ui.custom.CustomCircleView;
import com.example.personalapplication.ui.custom.MouldView;
import com.example.personalapplication.ui.dialog.NicknameDialog;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NicknameDialog.onNicknameEditedListener {

    private CustomCircleView head_sculpture;
    private MouldView personal_mouldView;
    private MouldView order_mouldView;
    private MouldView car_mouldView;
    private MouldView wallet_mouldView;
    private EditText editText;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private NicknameDialog nicknameDialog;
    private boolean isTwoPressed;
    private String currentUsername;

    private static final int OPEN_ALBUM = 1;
    private static final int CHOOSE_PHOTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.isFirstTimeLogin();
        setContentView(R.layout.activity_main);
        MyApplication.addActivity(this);
        head_sculpture = findViewById(R.id.head_sculpture);
        personal_mouldView = findViewById(R.id.personal);
        order_mouldView=findViewById(R.id.order);
        car_mouldView=findViewById(R.id.car);
        wallet_mouldView=findViewById(R.id.wallet);
        editText = findViewById(R.id.nickname);
        editText.setCursorVisible(false);
        this.loadImageAndNickname();
        order_mouldView.setOnClickListener(this);
        head_sculpture.setOnClickListener(this);
        personal_mouldView.setOnClickListener(this);
        car_mouldView.setOnClickListener(this);
        car_mouldView.setOnClickListener(this);
        wallet_mouldView.setOnClickListener(this);
        editText.setOnClickListener(this);
    }

    private void isFirstTimeLogin() {
        pref = getSharedPreferences("currentUsername", MODE_PRIVATE);
        currentUsername=pref.getString("currentUsername", "");
        if (TextUtils.isEmpty(currentUsername)) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_sculpture:
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, OPEN_ALBUM);
                } else {
                    openAlbum();
                }
                break;
            case R.id.personal:
                Intent intent = new Intent(MainActivity.this, PersonalDetailsActivity.class);
                startActivity(intent);
                break;
            case R.id.nickname:
                nicknameDialog = new NicknameDialog();
                nicknameDialog.setonNickNameEditedListener(this);
                nicknameDialog.show(getSupportFragmentManager(), "show");
                break;
            case R.id.order:
                Intent intent1 =new Intent(this,MyOrdersActivity.class);
                startActivity(intent1);
                break;
            case R.id.car:
                Intent intent2=new Intent(this,CarDetailsActivity.class);
                startActivity(intent2);
                break;
            case R.id.wallet:
                Intent intent3=new Intent(this,WalletActivity.class);
                startActivity(intent3);
                break;
            default:
                break;
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case OPEN_ALBUM:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    handleImageOnKitKat(data);
                }
                break;
            default:
                break;
        }
    }

    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                imagePath = getImagePath(uri, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                imagePath = uri.getPath();
            }
            displayImage(imagePath);
        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            head_sculpture.setImageBitmap(bitmap);
            editor = getSharedPreferences(currentUsername+"image", MODE_PRIVATE).edit();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
            String imageBase64 = (Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT));
            editor.putString("head_sculpture", imageBase64);
            editor.apply();
            editor.clear();
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadImageAndNickname() {
        pref = getSharedPreferences(currentUsername+"nickname", MODE_PRIVATE);
        String nickname = pref.getString("nickname", "");
        editText.setText(nickname);
        pref = getSharedPreferences(currentUsername+"image", MODE_PRIVATE);
        String headPic = pref.getString("head_sculpture", "");
        Bitmap bitmap;
        if (headPic != "") {
            byte[] bytes = Base64.decode(headPic.getBytes(), 1);
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            head_sculpture.setImageBitmap(bitmap);
        }
    }

    //nickname回调函数
    @Override
    public void onNicknameEdited(String nickname) {
        editText.setText(nickname);
        pref=getSharedPreferences(currentUsername+"nickname",MODE_PRIVATE);
        editor=pref.edit();
        editor.putString("nickname",nickname);
        editor.apply();
        editor.clear();
    }

    @Override
    public void onBackPressed() {
        if (!isTwoPressed) {
            isTwoPressed = true;
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                        isTwoPressed = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            MyApplication.finishAll();
        }
    }
}
