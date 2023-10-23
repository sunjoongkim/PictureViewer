package com.report.pictureviewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ImageView mainImage;
    private ImageView prevImage;
    private ImageView nextImage;
    private EditText editPage;

    private Button btnPrev;
    private Button btnNext;


    private List<Integer> imageList = new ArrayList<>();
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainImage = findViewById(R.id.image_main);
        prevImage = findViewById(R.id.image_prev);
        nextImage = findViewById(R.id.image_next);
        editPage = findViewById(R.id.edit_page);
        editPage.setOnEditorActionListener(onEditorActionListener);
        btnPrev = findViewById(R.id.btn_prev);
        btnPrev.setOnClickListener(onClickListener);
        btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(onClickListener);

        initImageList();
    }

    // prev, next 버튼을 누를때 처리
    private View.OnClickListener onClickListener = v -> {
        if (v.equals(btnNext)) {
            if (currentIndex != 19)
                currentIndex++;
            selectMainImage();
        } else if (v.equals(btnPrev)) {
            if (currentIndex != 0)
                currentIndex--;
            selectMainImage();
        }
    };

    // index값을 사용자가 입력할때 처리
    private TextView.OnEditorActionListener onEditorActionListener = (v, actionId, event) -> {

        String input = ((EditText) v).getText().toString();
        int number;

        // 정수값일땐 그대로 저장
        // 실수값일땐 반올림해 저장
        try {
            number = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            number = (int) Math.round(Double.parseDouble(input));
        }

        // 1~20 사이의 숫자를 입력하면 그대로 반영후 이미지 선택 함수 호출
        // 범위를 벗어나면 Toast를 띄운 후 1 이나 20 으로 이동후 호출
        if (number >= 1 && number <= 20) {
            currentIndex = number - 1;
        } else {
            Toast.makeText(MainActivity.this, "사진은 1~20까지만 있습니다.", Toast.LENGTH_SHORT).show();

            if (number < 1)
                currentIndex = 0;
            else if (number > 20)
                currentIndex = 19;
        }
        selectMainImage();

        return false;
    };

    // 앱 실행시 이미지 id 리스트를 초기화 한다. (image_1~20 까지 for문을 돌며 res id값 저장)
    // 초기화후 첫번째 이미지 선택
    private void initImageList() {
        imageList.clear();
        currentIndex = 0;

        for (int i = 0; i < 20; i++) {
            int id = getResources().getIdentifier("image_" + (i + 1),"drawable", getPackageName());
            imageList.add(id);
        }
        selectMainImage();
    }

    // 3개의 ImageView에 image를 그려주는 함수
    private void selectMainImage() {
        mainImage.setImageResource(imageList.get(currentIndex));

        // 처음과 마지막 이미지에서는 각각 버튼 보여주기와 이미지 리소스 설정을 다르게 한다.
        if (currentIndex == 0) {
            btnPrev.setVisibility(View.INVISIBLE);
            prevImage.setImageResource(0);
        } else {
            btnPrev.setVisibility(View.VISIBLE);
            prevImage.setImageResource(imageList.get(currentIndex - 1));
        }

        if (currentIndex == 19) {
            btnNext.setVisibility(View.INVISIBLE);
            nextImage.setImageResource(0);
        } else {
            btnNext.setVisibility(View.VISIBLE);
            nextImage.setImageResource(imageList.get(currentIndex + 1));
        }

        // 선택된 이미지의 index를 표시
        editPage.setText(String.valueOf(currentIndex + 1));
    }

}