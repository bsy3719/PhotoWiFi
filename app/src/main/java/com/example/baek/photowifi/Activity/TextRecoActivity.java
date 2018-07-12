package com.example.baek.photowifi.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.baek.photowifi.Adapter.WifiAdapter;
import com.example.baek.photowifi.Model.ParsingText;
import com.example.baek.photowifi.Model.Wifi;
import com.example.baek.photowifi.R;
import com.example.baek.photowifi.databinding.ActivityTextRecoBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions;
import com.google.firebase.ml.vision.cloud.text.FirebaseVisionCloudDocumentTextDetector;
import com.google.firebase.ml.vision.cloud.text.FirebaseVisionCloudText;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;
import com.sangcomz.fishbun.define.Define;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextRecoActivity extends AppCompatActivity {

    private ActivityTextRecoBinding mBinding;
    private List<Uri> mUriList;
    private List<String> mWordList = new ArrayList<>();

    private int mCount = 0;

    private static final int REQUEST_CODE_IMAGE_PICKER = 200;
    private static final String TAG = "TextRecoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_text_reco);
        checkPermission();

    }

    //클라우드 비전 api로 문자 인식
    private void runCloudTextRecognition(Bitmap bitmap) {

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionCloudDetectorOptions options =
                new FirebaseVisionCloudDetectorOptions.Builder()
                        .setModelType(FirebaseVisionCloudDetectorOptions.LATEST_MODEL)
                        .setMaxResults(15)
                        .build();

        FirebaseVisionCloudDocumentTextDetector detector = FirebaseVision.getInstance()
                .getVisionCloudDocumentTextDetector(options);
        detector.detectInImage(image)
                .addOnSuccessListener(
                        new OnSuccessListener<FirebaseVisionCloudText>() {
                            @Override
                            public void onSuccess(FirebaseVisionCloudText texts) {
                                processCloudTextRecognitionResult(texts);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                e.printStackTrace();
                            }
                        });
    }

    private void processCloudTextRecognitionResult(FirebaseVisionCloudText text) {
        mWordList.clear();
        // Task completed successfully
        if (text == null) {
            showToast("No text found");
            return;
        }
        List<FirebaseVisionCloudText.Page> pages = text.getPages();
        for (int i = 0; i < pages.size(); i++) {
            FirebaseVisionCloudText.Page page = pages.get(i);
            List<FirebaseVisionCloudText.Block> blocks = page.getBlocks();

            for (int j = 0; j < blocks.size(); j++) {
                List<FirebaseVisionCloudText.Paragraph> paragraphs = blocks.get(j).getParagraphs();

                for (int k = 0; k < paragraphs.size(); k++) {
                    FirebaseVisionCloudText.Paragraph paragraph = paragraphs.get(k);
                    List<FirebaseVisionCloudText.Word> words = paragraph.getWords();

                    for (int l = 0; l < words.size(); l++) {
                        FirebaseVisionCloudText.Word word = words.get(l);
                        List<FirebaseVisionCloudText.Symbol> symbols = word.getSymbols();
                        StringBuilder wordStr = new StringBuilder();

                        for (int m = 0; m < symbols.size(); m++) {
                            wordStr.append(symbols.get(m).getText());
                        }
                        mWordList.add(wordStr.toString());
                    }
                }
            }
        }

        parsingText(mWordList);

    }

    private void parsingText(List<String> wordList){

        String completeWord = "";

        //문자 인식이 제대로 이루어 지는지 테스트
        for (int i = 0; i < wordList.size(); i++){
            Log.d(TAG," index : " + Integer.toString(i) +
                    " text : " + wordList.get(i));
            completeWord = completeWord + wordList.get(i);
        }

        if (mCount%2 == 0){
            mBinding.idEditTextView.setText(completeWord);
        } else{
            mBinding.passwordEditTextView.setText(completeWord);
        }

        mCount = mCount +1;

    }

    //tedPermission을 이용하여 퍼미션 체크
    private void checkPermission(){
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                getPhoto();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                showToast("Permission Denied\n" + deniedPermissions.toString());
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    //fishbun을 이용하여 사진을 가져옴
    private void getPhoto() {
        FishBun.with(TextRecoActivity.this)
                .setImageAdapter(new GlideAdapter())
                .setMaxCount(1)
                .setIsUseDetailView(false)
                .setCamera(true)
                .exceptGif(true)
                .setButtonInAlbumActivity(true)
                .setRequestCode(REQUEST_CODE_IMAGE_PICKER)
                .setActionBarColor(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimaryDark), false)
                .startAlbum();
    }

    //사진 uri리스트를 가져옴
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent imageData) {
        super.onActivityResult(requestCode, resultCode, imageData);
        switch (requestCode) {
            case REQUEST_CODE_IMAGE_PICKER:
                if (resultCode == RESULT_OK) {
                    mUriList = imageData.getParcelableArrayListExtra(Define.INTENT_PATH);
                    //mSelectedImage = uriToBitmap(mUriList.get(0));
                    //mSelectedImage = resizeBitamp(mSelectedImage);
                    //Glide.with(this).load(mUriList.get(0)).into(mBinding.recoImageView);
                    mBinding.recoImageView.setImageUriAsync(mUriList.get(0));
                    //runCloudTextRecognition(mUriList.get(0));

                }
                break;
        }
    }

    // 메뉴를 연동하고
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.text_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_item_reco:
                runCloudTextRecognition(mBinding.recoImageView.getCroppedImage());
                break;
            case R.id.menu_item_check:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //토스트 메시지 호출
    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}