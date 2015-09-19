package com.thinkful.noteloc;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by ardokusuma on 9/13/15.
 */
public class DrawerActivity extends AppCompatActivity {
    Toolbar mToolbar;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FrameLayout mContentFrame;
    int mCurrentSelectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setUpToolbar();
        setUpNavDrawer();

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mContentFrame = (FrameLayout) findViewById(R.id.main_content_frame);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.navigation_item_1:
                        Snackbar.make(mContentFrame, "Item One", Snackbar.LENGTH_SHORT).show();
                        mCurrentSelectedPosition = 0;
                        return true;
                    case R.id.navigation_item_2:
                        Snackbar.make(mContentFrame, "Item Two", Snackbar.LENGTH_SHORT).show();
                        mCurrentSelectedPosition = 1;
                        return true;
                    default:
                        return true;
                }
            }
        });
    }

    private void setUpToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    private void setUpNavDrawer() {
        if (mToolbar != null) {
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setDisplayHomeAsUpEnabled(false);
            }
            mToolbar.setNavigationIcon(R.drawable.ic_action_drawer);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
