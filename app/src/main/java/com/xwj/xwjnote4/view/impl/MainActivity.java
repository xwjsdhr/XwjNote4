package com.xwj.xwjnote4.view.impl;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xwj.xwjnote4.R;
import com.xwj.xwjnote4.model.MyUser;
import com.xwj.xwjnote4.model.TextMsg;
import com.xwj.xwjnote4.presenter.MainPresenter;
import com.xwj.xwjnote4.presenter.impl.MainPresenterImpl;
import com.xwj.xwjnote4.receiver.ScreenReceiver;
import com.xwj.xwjnote4.view.MainView;
import com.xwj.xwjnote4.widget.MyActionBarDrawerToggle;

import cn.bmob.v3.Bmob;
import de.greenrobot.event.EventBus;

/**
 * 主界面的Activity
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        MainView, View.OnClickListener, SearchView.OnQueryTextListener, View.OnFocusChangeListener, SearchView.OnCloseListener {

    private FragmentManager mManager;
    public Toolbar mToolbar;
    public NavigationView mNavigationView;
    private MainPresenter mMainPresenter;
    public FloatingActionButton mFab;

    public DrawerLayout mDrawerLayout;
    public ImageView mIvIcon;
    public TextView mTvUserName;
    public View mHeaderView;


    public ActionBarDrawerToggle mActionBarDrawerToggle;
    public ScreenReceiver mScreenReceiver;
    public SearchView mSearchView;
    private String TAG = MainActivity.class.getSimpleName();
    private FavoriteNoteFragment mFavoriteNoteFragment;
    private TrashNoteFragment mTrashFragment;
    private EventBus mEventBus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "1cb9fad5114b9a6bebe365c5800db5e9");
        initView();
        setListener();
        mMainPresenter = new MainPresenterImpl(this, this);
        mEventBus = EventBus.getDefault();
        if (!mEventBus.isRegistered(this)) {
            mEventBus.register(this);
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(mScreenReceiver, intentFilter);
        this.toHomeFragment();
        mMainPresenter.getCurrentUser();

    }

    /**
     * 初始化视图。
     */
    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mManager = this.getSupportFragmentManager();
        mNavigationView = (NavigationView) this.findViewById(R.id.nv_drawer);
        mHeaderView = mNavigationView.inflateHeaderView(R.layout.header_layout);
        mFab = (FloatingActionButton) this.findViewById(R.id.fab);
        mDrawerLayout = (DrawerLayout) this.findViewById(R.id.dl_main);
        mIvIcon = (ImageView) mHeaderView.findViewById(R.id.iv_user_icon);
        mTvUserName = (TextView) mHeaderView.findViewById(R.id.tv_user_username);
        mActionBarDrawerToggle = new MyActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        mFavoriteNoteFragment = new FavoriteNoteFragment();
        mTrashFragment = new TrashNoteFragment();
        mScreenReceiver = new ScreenReceiver();
        mDrawerLayout.setEnabled(false);

    }

    /**
     * 设置监听器。
     */
    private void setListener() {
         mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mFab.setOnClickListener(this);
        mNavigationView.setNavigationItemSelectedListener(this);
        mIvIcon.setOnClickListener(this);
        mManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
            }
        });
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        mActionBarDrawerToggle.syncState();
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        mMainPresenter.switchFragmentById(item.getItemId());
        return true;
    }

    @Override
    public void toHomeFragment() {
        mManager.beginTransaction()
                .setCustomAnimations(R.anim.scale_in, R.anim.scale_out)
                .replace(R.id.container_main, new HomeNoteFragment(), HomeNoteFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void toFavoriteFragment() {
        Fragment homeFragment = mManager.findFragmentByTag(HomeNoteFragment.TAG);
        if (mFavoriteNoteFragment != null) {
            mManager.beginTransaction()
                    .replace(R.id.container_main, mFavoriteNoteFragment, FavoriteNoteFragment.TAG)
                    .commit();
        }
        if (homeFragment != null) {
            Log.e(TAG, "HOME != NULL");
        }
    }

    @Override
    public void toTrashFragment() {
        if (mTrashFragment != null) {
            mManager.beginTransaction()
                    .replace(R.id.container_main, mTrashFragment, TrashNoteFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void toSecurityFragment() {
        Fragment securityFragment = mManager.findFragmentByTag(SecurityNoteFragment.TAG);
        if (securityFragment != null) {
            mManager.beginTransaction()
                    .replace(R.id.container_main, securityFragment)
                    .commit();
        } else {
            mManager.beginTransaction()
                    .replace(R.id.container_main, new SecurityNoteFragment(), SecurityNoteFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void closeDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void openDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void setToolbarTitle(String title) {
        mToolbar.setTitle(title);
    }

    @Override
    public void setUserIcon(String imageUrl) {
        Picasso.with(this).load(imageUrl).into(mIvIcon);
    }

    @Override
    public void setUserName(String userName) {
        mTvUserName.setText(userName);
    }

    @Override
    public void changeFloatButtonIcon(int resId) {
        mFab.setImageResource(resId);
    }


    @Override
    public void hideFloatButton() {
        mFab.hide();
    }

    @Override
    public void showFloatButton() {
        mFab.show();
    }

    @Override
    public void bindUser(MyUser user) {
        setUserName(user.getUsername());
    }

    @Override
    public void mainStartActivity(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
    }

    @Override
    public void mainFinishActivity() {
        this.finish();
    }

    @Override
    public void setToolbarTitle(int count) {
        mToolbar.setTitle(String.format("便签(%d)", count));
    }

    @Override
    public void toAddFragment() {
        mManager.beginTransaction()
                .setCustomAnimations(R.anim.scale_in, R.anim.scale_out)
                .replace(R.id.container_main, new NoteDetailFragment(), NoteDetailFragment.class.getSimpleName())
                .remove(mManager.findFragmentByTag(HomeNoteFragment.class.getSimpleName()))
                .commit();
        hideFloatButton();
        disableDrawer();
    }

    @Override
    public void disableDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void enableDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
    }

    @Override
    public void onClick(View v) {
        mMainPresenter.handleClick(v);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mEventBus.isRegistered(this)) {
            mEventBus.unregister(this);
        }
        unregisterReceiver(mScreenReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnQueryTextFocusChangeListener(this);
        mSearchView.setOnCloseListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mMainPresenter.handleSelectMenuItem(item.getItemId());
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        TextMsg msg = new TextMsg();
        msg.setQueryText(newText);
        mEventBus.postSticky(msg);
        return true;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            TextMsg msg = new TextMsg();
            msg.setQueryText("");
            mEventBus.postSticky(msg);
        }
    }

    @Override
    public boolean onClose() {
        return true;
    }

    @Override
    public void onBackPressed() {
        Log.e(TAG, "ON BACK PRESS");
        if (mManager.findFragmentByTag(HomeNoteFragment.class.getSimpleName()) != null) {
            Log.e(TAG, "ON BACK PRESS1");
            this.finish();
        } else if (mManager.findFragmentByTag(NoteDetailFragment.class.getSimpleName()) != null) {
            toHomeFragment();
            Log.e(TAG, "ON BACK PRESS2");
            this.showFloatButton();
        }
    }

    public void onEvent(Integer count) {
        this.setToolbarTitle(count);
    }
}