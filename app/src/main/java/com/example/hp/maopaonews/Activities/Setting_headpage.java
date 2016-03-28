package com.example.hp.maopaonews.Activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.maopaonews.R;
import com.example.hp.maopaonews.Setting_utils.SearchDB;
import com.example.hp.maopaonews.Setting_utils.TouXiangCache;
import com.example.hp.maopaonews.content_fragment.SheZhiFramen;
import com.example.hp.maopaonews.utils.QieYuanTu;
import com.umeng.socialize.utils.Log;

import java.io.File;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hp on 2016/1/14.
 */
public class Setting_headpage extends AppCompatActivity {
    private String user_name;
    private String pic_path;
    ImageView back;
    TextView mylable;
    CircleImageView header;
    TextView userName;
    TextView zhicheng;
    Bitmap bp;

    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 100;
    private static int output_Y = 100;
    private static final String IMAGE_FILE_NAME = "head_image.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hgz_head_page);
        InstanceActivtiyApplication();
        initView();
        pic_path = SearchDB.TouXiangDb(this, IMAGE_FILE_NAME);
        user_name = SearchDB.createDb(this, "userName");
        if (pic_path != null) {

            Bitmap getphoto = TouXiangCache.getphoto(pic_path);
            Log.d("HHH","getphoto="+getphoto);
            Log.d("HHH","pic_path="+pic_path);
            header.setImageBitmap(getphoto);
        }
        if (user_name != null) {

            userName.setText(user_name);
            zhicheng.setText("跟帖局科员");
        }
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        header = (CircleImageView)findViewById(R.id.header);
        mylable = (TextView) findViewById(R.id.mylable);
        userName = (TextView) findViewById(R.id.user_name);
        zhicheng = (TextView) findViewById(R.id.zhicheng);


    }


    public void OnClick(View view) {

        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.header:
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                builder.setTitle("设置图像");
                View view1 = LayoutInflater.from(this).inflate(R.layout.hgz_head_dialog, null);
                ImageView photo_selector = (ImageView) view1.findViewById(R.id.head_photo_selector);
                ImageView pic_selector = (ImageView) view1.findViewById(R.id.head_pic_selector);
                ImageView selector_sina = (ImageView) view1.findViewById(R.id.head_selector_sina);
                photo_selector.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        choseHeadImageFromCameraCapture();
                    }
                });
                pic_selector.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        choseHeadImageFromGallery();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setView(view1);
                builder.create();
                builder.show();
                break;

        }

    }

    private void choseHeadImageFromGallery(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,CODE_GALLERY_REQUEST);

    }

    public void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {

            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
        }
        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 用户没有进行有效的设置操作，返回
        if(requestCode==RESULT_CANCELED){

            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }

        switch(requestCode){
            case CODE_CAMERA_REQUEST:
                if(hasSdcard()){
                    File templie=new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);

                    cropRawPhoto(Uri.fromFile(templie));
                }else {

                    Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG).show();
                }

                break;
            case CODE_GALLERY_REQUEST:
                //cropRawPhoto(data.getData());
                Uri url=data.getData();
                try {
                    Bitmap  bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(url));
                    setImageToHeadView(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                break;
            case CODE_RESULT_REQUEST:
                if(data!=null){
                    setImageToHeadView(data);
                }
                break;


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setImageToHeadView(Bitmap photo){

        bp=photo;
        Bitmap circlBitmap= QieYuanTu.getRoundedCornerBitmap(bp);
        pic_path = IMAGE_FILE_NAME;
        TouXiangCache.saveMyBitmap(circlBitmap,pic_path);

        String touxiang=SearchDB.TouXiangDb(this,pic_path);
        if(touxiang==null){
            SharedPreferences preferences=getSharedPreferences("useInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("pic_path",pic_path).commit();
        }

        header.setImageBitmap(circlBitmap);
        Message message=new Message();
        message.what=2;
        message.obj=bp;
        SheZhiFramen.handler.sendMessage(message);

    }




    private void setImageToHeadView(Intent intent){
        Bundle extras=intent.getExtras();
        if(extras!=null){
            final  Bitmap photo=extras.getParcelable("data");
            bp=photo;
            Bitmap circlBitmap= QieYuanTu.getRoundedCornerBitmap(bp);
            pic_path = IMAGE_FILE_NAME;
            TouXiangCache.saveMyBitmap(circlBitmap,pic_path);

            String touxiang=SearchDB.TouXiangDb(this,pic_path);
            if(touxiang==null){
                SharedPreferences preferences=getSharedPreferences("useInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("pic_path",pic_path).commit();
            }

            header.setImageBitmap(circlBitmap);
            Message message=new Message();
            message.what=2;
            message.obj=bp;
            SheZhiFramen.handler.sendMessage(message);

        }


    }

    public void cropRawPhoto(Uri uri){
        Intent intent=new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        //设置裁剪
        intent.putExtra("crop",true);
        // aspectX , aspectY :宽高的比例
       // intent.putExtra("aspectX", 1);
        //intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    public static boolean hasSdcard() {

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {

            return true;
        } else {

            return false;
        }

    }


    private void InstanceActivtiyApplication(){

        ActivityManager activityManager= ActivityManager.getInstance();
        activityManager.addActivity(this);
    }
    ///////////////
}
