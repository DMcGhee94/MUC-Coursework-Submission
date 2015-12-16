package com.example.darre.navdrawer;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OverallActivity extends MenuActivity {

    public static mucAsyncRSSParser parseAsync;

    LinearLayout mLinearLayout;
    LinearLayout mLinearLayoutHeader;
    ValueAnimator mAnimator;

    public TextView SUName, SUHigh, SUAvg, SULow;
    public TextView UName, UHigh, UAvg, ULow;
    public TextView LRPName, LRPHigh, LRPAvg, LRPLow;
    public TextView PDName, PDHigh, PDAvg, PDLow;
    public TextView DName, DHigh, DAvg, DLow;
    public TextView LPGName, LPGHigh, LPGAvg, LPGLow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overall_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        parseAsync = new mucAsyncRSSParser();
        parseAsync.execute();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mLinearLayout = (LinearLayout) findViewById(R.id.expandable);
        mLinearLayoutHeader = (LinearLayout) findViewById(R.id.header);

        mLinearLayout.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        mLinearLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                        mLinearLayout.setVisibility(View.GONE);

                        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        mLinearLayout.measure(widthSpec, heightSpec);

                        mAnimator = slideAnimator(0, mLinearLayout.getMeasuredHeight());
                        return true;
                    }
                });


        mLinearLayoutHeader.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mLinearLayout.getVisibility() == View.GONE) {
                    SUName = (TextView) findViewById(R.id.superUnleadedHeading);
                    SUName.setText(parseAsync.PAData.get(0).Name);
                    SUHigh = (TextView) findViewById(R.id.superUnleadedMax);
                    SUHigh.setText(parseAsync.PAData.get(0).HighestString);
                    SUAvg = (TextView) findViewById(R.id.superUnleadedAvg);
                    SUAvg.setText(parseAsync.PAData.get(0).AverageString);
                    SULow = (TextView) findViewById(R.id.superUnleadedMin);
                    SULow.setText(parseAsync.PAData.get(0).LowestString);

                    UName = (TextView) findViewById(R.id.unleadedHeading);
                    UName.setText(parseAsync.PAData.get(1).Name);
                    UHigh = (TextView) findViewById(R.id.unleadedMax);
                    UHigh.setText(parseAsync.PAData.get(1).HighestString);
                    UAvg = (TextView) findViewById(R.id.unleadedAvg);
                    UAvg.setText(parseAsync.PAData.get(1).AverageString);
                    ULow = (TextView) findViewById(R.id.unleadedMin);
                    ULow.setText(parseAsync.PAData.get(1).LowestString);

                    LRPName = (TextView) findViewById(R.id.lrpHeading);
                    LRPName.setText(parseAsync.PAData.get(2).Name);
                    LRPHigh = (TextView) findViewById(R.id.lrpMax);
                    LRPHigh.setText(parseAsync.PAData.get(2).HighestString);
                    LRPAvg = (TextView) findViewById(R.id.lrpAvg);
                    LRPAvg.setText(parseAsync.PAData.get(2).AverageString);
                    LRPLow = (TextView) findViewById(R.id.lrpMin);
                    LRPLow.setText(parseAsync.PAData.get(2).LowestString);

                    PDName = (TextView) findViewById(R.id.premiumDieselHeading);
                    PDName.setText(parseAsync.PAData.get(3).Name);
                    PDHigh = (TextView) findViewById(R.id.premiumDieselMax);
                    PDHigh.setText(parseAsync.PAData.get(3).HighestString);
                    PDAvg = (TextView) findViewById(R.id.premiumDieselAvg);
                    PDAvg.setText(parseAsync.PAData.get(3).AverageString);
                    PDLow = (TextView) findViewById(R.id.premiumDieselMin);
                    PDLow.setText(parseAsync.PAData.get(3).LowestString);

                    DName = (TextView) findViewById(R.id.dieselHeading);
                    DName.setText(parseAsync.PAData.get(4).Name);
                    DHigh = (TextView) findViewById(R.id.dieselMax);
                    DHigh.setText(parseAsync.PAData.get(4).HighestString);
                    DAvg = (TextView) findViewById(R.id.dieselAvg);
                    DAvg.setText(parseAsync.PAData.get(4).AverageString);
                    DLow = (TextView) findViewById(R.id.dieselMin);
                    DLow.setText(parseAsync.PAData.get(4).LowestString);

                    LPGName = (TextView) findViewById(R.id.lpgHeading);
                    LPGName.setText(parseAsync.PAData.get(5).Name);
                    LPGHigh = (TextView) findViewById(R.id.lpgMax);
                    LPGHigh.setText(parseAsync.PAData.get(5).HighestString);
                    LPGAvg = (TextView) findViewById(R.id.lpgAvg);
                    LPGAvg.setText(parseAsync.PAData.get(5).AverageString);
                    LPGLow = (TextView) findViewById(R.id.lpgMin);
                    LPGLow.setText(parseAsync.PAData.get(5).LowestString);
                    expand();
                } else {
                    collapse();
                }
            }
        });
    }


    private void expand() {
        mLinearLayout.setVisibility(View.VISIBLE);
        mAnimator.start();
    }

    private void collapse() {
        int finalHeight = mLinearLayout.getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                mLinearLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        mAnimator.start();
    }


    private ValueAnimator slideAnimator(int start, int end) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);


        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = mLinearLayout.getLayoutParams();
                layoutParams.height = value;
                mLinearLayout.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }
}
