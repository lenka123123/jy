package sinia.com.baihangeducation.club.clubcomment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import sinia.com.baihangeducation.R;
import sinia.com.baihangeducation.club.clubcomment.model.CommentModel;
import sinia.com.baihangeducation.club.clubcomment.model.MyApplyinfo;
import sinia.com.baihangeducation.supplement.base.BaseActivity;

public class CommentActivity extends BaseActivity {

    private CommentModel clubSendAnnounceModel;

    private int difficulty_point = 5;
    private int job_accord_point = 5;
    private int money_accord_point = 5;

    private String is_comment;
    private String job_city_name;
    private String job_title;
    private String job_money;
    private ImageView difficult_ont_star;
    private ImageView difficult_two_star;
    private ImageView difficult_three_star;
    private ImageView difficult_four_star;
    private ImageView difficult_five_star;
    private ImageView info_ont_star;
    private ImageView info_two_star;
    private ImageView info_three_star;
    private ImageView info_four_star;
    private ImageView info_five_star;
    private ImageView money_ont_star;
    private ImageView money_two_star;
    private ImageView money_three_star;
    private ImageView money_four_star;
    private ImageView money_five_star;
    private TextView send_ad;
    private int job_apply_id;


    public int initLayoutResID() {
        return R.layout.activity_club_comment;
    }


    @Override
    protected void initView() {

        Intent intent = getIntent();
        job_title = intent.getStringExtra("job_title");
        job_city_name = intent.getStringExtra("job_city_name");
        job_money = intent.getStringExtra("job_money");
        job_apply_id = intent.getIntExtra("job_apply_id", 0);
        is_comment = intent.getStringExtra("is_comment");


        TextView money = findViewById(R.id.money);
        TextView name = findViewById(R.id.name);
        TextView address = findViewById(R.id.address);

        difficult_ont_star = findViewById(R.id.difficult_ont_star);
        difficult_two_star = findViewById(R.id.difficult_two_star);
        difficult_three_star = findViewById(R.id.difficult_three_star);
        difficult_four_star = findViewById(R.id.difficult_four_star);
        difficult_five_star = findViewById(R.id.difficult_five_star);

        info_ont_star = findViewById(R.id.info_ont_star);
        info_two_star = findViewById(R.id.info_two_star);
        info_three_star = findViewById(R.id.info_three_star);
        info_four_star = findViewById(R.id.info_four_star);
        info_five_star = findViewById(R.id.info_five_star);

        money_ont_star = findViewById(R.id.money_ont_star);
        money_two_star = findViewById(R.id.money_two_star);
        money_three_star = findViewById(R.id.money_three_star);
        money_four_star = findViewById(R.id.money_four_star);
        money_five_star = findViewById(R.id.money_five_star);


        send_ad = findViewById(R.id.send_ad);
        send_ad.setOnClickListener(this);


        difficult_ont_star.setOnClickListener(this);
        difficult_two_star.setOnClickListener(this);
        difficult_three_star.setOnClickListener(this);
        difficult_four_star.setOnClickListener(this);
        difficult_five_star.setOnClickListener(this);

        info_ont_star.setOnClickListener(this);
        info_two_star.setOnClickListener(this);
        info_three_star.setOnClickListener(this);
        info_four_star.setOnClickListener(this);
        info_five_star.setOnClickListener(this);

        money_ont_star.setOnClickListener(this);
        money_two_star.setOnClickListener(this);
        money_three_star.setOnClickListener(this);
        money_four_star.setOnClickListener(this);
        money_five_star.setOnClickListener(this);


        money.setText(job_money);
        address.setText(job_city_name);
        name.setText(job_title);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void initData() {
        clubSendAnnounceModel = new CommentModel(this);

        if (is_comment.equals("已评价")) {
            send_ad.setVisibility(View.GONE);
            clubSendAnnounceModel.getMyJobApplyInfo(job_apply_id);

        }
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {

            case R.id.send_ad:
                clubSendAnnounceModel.setComment(job_apply_id, difficulty_point, job_accord_point, money_accord_point);

                break;

            case R.id.difficult_ont_star:
                difficulty_point = 1;
                changeDifficult(difficulty_point);
                break;
            case R.id.difficult_two_star:
                difficulty_point = 2;
                changeDifficult(difficulty_point);
                break;
            case R.id.difficult_three_star:
                difficulty_point = 3;
                changeDifficult(difficulty_point);
                break;
            case R.id.difficult_four_star:
                difficulty_point = 4;
                changeDifficult(difficulty_point);
                break;
            case R.id.difficult_five_star:
                difficulty_point = 5;
                changeDifficult(difficulty_point);
                break;
            ///////////////////
            case R.id.info_ont_star:
                job_accord_point = 1;
                changeInfo(job_accord_point);
                break;
            case R.id.info_two_star:
                job_accord_point = 2;
                changeInfo(job_accord_point);
                break;
            case R.id.info_three_star:
                job_accord_point = 3;
                changeInfo(job_accord_point);
                break;
            case R.id.info_four_star:
                job_accord_point = 4;
                changeInfo(job_accord_point);
                break;
            case R.id.info_five_star:
                job_accord_point = 5;
                changeInfo(job_accord_point);
                break;

            ///////////////////
            case R.id.money_ont_star:
                money_accord_point = 1;
                changeMoney(money_accord_point);
                break;
            case R.id.money_two_star:
                money_accord_point = 2;
                changeMoney(money_accord_point);
                break;
            case R.id.money_three_star:
                money_accord_point = 3;
                changeMoney(money_accord_point);
                break;
            case R.id.money_four_star:
                money_accord_point = 4;
                changeMoney(money_accord_point);
                break;
            case R.id.money_five_star:
                money_accord_point = 5;
                changeMoney(money_accord_point);
                break;
        }
    }

    public void changeDifficult(int point) {
        difficult_ont_star.setImageResource(point >= 1 ? R.drawable.comment_red : R.drawable.comment_gray);
        difficult_two_star.setImageResource(point >= 2 ? R.drawable.comment_red : R.drawable.comment_gray);
        difficult_three_star.setImageResource(point >= 3 ? R.drawable.comment_red : R.drawable.comment_gray);
        difficult_four_star.setImageResource(point >= 4 ? R.drawable.comment_red : R.drawable.comment_gray);
        difficult_five_star.setImageResource(point >= 5 ? R.drawable.comment_red : R.drawable.comment_gray);
    }

    public void changeInfo(int point) {
        info_ont_star.setImageResource(point >= 1 ? R.drawable.comment_red : R.drawable.comment_gray);
        info_two_star.setImageResource(point >= 2 ? R.drawable.comment_red : R.drawable.comment_gray);
        info_three_star.setImageResource(point >= 3 ? R.drawable.comment_red : R.drawable.comment_gray);
        info_four_star.setImageResource(point >= 4 ? R.drawable.comment_red : R.drawable.comment_gray);
        info_five_star.setImageResource(point >= 5 ? R.drawable.comment_red : R.drawable.comment_gray);
    }

    public void changeMoney(int point) {
        money_ont_star.setImageResource(point >= 1 ? R.drawable.comment_red : R.drawable.comment_gray);
        money_two_star.setImageResource(point >= 2 ? R.drawable.comment_red : R.drawable.comment_gray);
        money_three_star.setImageResource(point >= 3 ? R.drawable.comment_red : R.drawable.comment_gray);
        money_four_star.setImageResource(point >= 4 ? R.drawable.comment_red : R.drawable.comment_gray);
        money_five_star.setImageResource(point >= 5 ? R.drawable.comment_red : R.drawable.comment_gray);
    }


    public void setContentInfo(MyApplyinfo contentInfo) {
        changeDifficult(contentInfo.job_difficulty_point);
        changeInfo(contentInfo.job_accord_point);
        changeMoney(contentInfo.money_accord_point);
    }
}
