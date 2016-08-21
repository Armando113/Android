package com.blackdroidstudios.finkelme;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.SearchView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
{
    //This is the main class for the app
    //Toolbar parameters
    protected Toolbar toolbar;
    protected ActionBar myToolbar;
    protected MenuItem toolbarSearch;
    protected MenuItem toolbarAddCard;

    //Saved parameters
    protected static String CURRENT_FRAG = "Current_Frag";
    protected Configuration myConfig;

    //Navigation Drawer Parameters
    protected DrawerLayout navDrawer;
    protected ScrollView leftDrawer;
    protected ActionBarDrawerToggle drawerToggle;
    public NavItem profileItem;
    public NavItem collectionItem;
    public NavItem inboxItem;
    public NavItem settingsItem;
    public NavItem helpItem;
    public NavItem aboutItem;

    //Search Filters
    protected ScrollView rightDrawer;
    protected CheckBox nameCBX;
    protected CheckBox emailCBX;
    protected CheckBox addressCBX;
    protected CheckBox phoneCBX;
    protected CheckBox companyCBX;
    protected CheckBox professionCBX;
    protected CheckBox websiteCBX;

    //Fragment parameters
    protected int currentFrag;
    protected FragmentManager fraggyManager;
    protected LoginFragment loginFrag;
    protected RegisterFragment registerFrag;
    protected ProfileFragment profileFrag;
    protected CollectionFragment collectionFrag;
    protected InboxFragment inboxFrag;
    protected SearchFragment searchFrag;

    //Search Parameters
    protected SearchManager mySearchManager;
    protected SearchView finkelSearchSV;
    protected String searchQuery;

    //Animation Parameters
    protected int animDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Restore saved settings
        onRestoreInstanceState(savedInstanceState);

        //Setup the Toolbar
        toolbar = (Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.onyx));
        myToolbar = getSupportActionBar();

        //Setup the Navigation Drawer
        navDrawer = (DrawerLayout)findViewById(R.id.main_navdrawer);
        navDrawer.setDrawerShadow(R.mipmap.drawer_shadow, GravityCompat.END);
        profileItem = new NavItem(this, R.id.navdrawer_profilerow, R.id.navdrawer_profileIV, R.id.navdrawer_profileTV, 2);
        collectionItem = new NavItem(this, R.id.navdrawer_collectionrow, R.id.navdrawer_collectionIV, R.id.navdrawer_collectionTV, 3);
        inboxItem = new NavItem(this, R.id.navdrawer_inboxrow, R.id.navdrawer_inboxIV, R.id.navdrawer_inboxTV, 4);
        settingsItem = new NavItem(this, R.id.navdrawer_settingsrow, R.id.navdrawer_settingsIV, R.id.navdrawer_settingsTV, 5);
        helpItem = new NavItem(this, R.id.navdrawer_helprow, R.id.navdrawer_helpIV, R.id.navdrawer_helpTV, 6);
        aboutItem = new NavItem(this, R.id.navdrawer_aboutrow, R.id.navdrawer_aboutIV, R.id.navdrawer_aboutTV, 7);
        drawerToggle = new ActionBarDrawerToggle(this, navDrawer, toolbar, R.string.toolbar_drawer_string, R.string.toolbar_drawer_string)
        {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        rightDrawer = (ScrollView)findViewById(R.id.finkel_navDrawer);
        leftDrawer = (ScrollView)findViewById(R.id.finkel_searchFilters);

        //Search Filter setup


        //Setup Fragments
        fraggyManager = getFragmentManager();
        loginFrag = new LoginFragment();
        registerFrag = new RegisterFragment();
        profileFrag = new ProfileFragment();
        collectionFrag = new CollectionFragment();
        inboxFrag = new InboxFragment();
        searchFrag = new SearchFragment();

        //Animation Setup
        animDuration = getResources().getInteger(R.integer.abc_config_activityShortDur);

        //Set the initial fragment as the Login
        changeFrag(currentFrag);
    }

    //Save all of the main settings
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        //All info to be stored, goes here
        savedInstanceState.putInt(CURRENT_FRAG, currentFrag);
    }

    //Restore all settings from previous session
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        //Retrieve all of the saved data
        //Make sure we DO have saved data from the past login
        if(savedInstanceState != null)
        {
            currentFrag = savedInstanceState.getInt(CURRENT_FRAG);
        }else //If there isn't, go for the defaults
        {
            currentFrag = 0;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //Get the Search parameters in the Toolbar
        mySearchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        finkelSearchSV = (SearchView)menu.findItem(R.id.toolbar_search).getActionView();
        SearchableInfo mySearchInfo;
        mySearchInfo = mySearchManager.getSearchableInfo(getComponentName());
        finkelSearchSV.setSearchableInfo(mySearchInfo);
        finkelSearchSV.setIconifiedByDefault(true);

        //Get all menu items
        toolbarSearch = menu.findItem(R.id.toolbar_search);
        toolbarAddCard = menu.findItem(R.id.toolbar_newcard);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(item.getItemId())
        {
            case R.id.toolbar_settings:
                break;

            case R.id.toolbar_search:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        /*Next, we will invalidate options in each page they don't belong in each page.
        For example, in the Login Page we don't need any actions in the toolbar because we are not even logged in,
        but if we are in the Profile page, we'll need the Search, settings, and Add new card actions
        */
        //Let us disable all action buttons, except the search one
        disableToolbarAction();
        switch(currentFrag)
        {
            //Login Fragment
            case 0:
                break;
            //Register Fragment
            case 1:
                break;
            //Profile Fragment
            case 2:
                toolbarAddCard.setVisible(true);
                break;
            //Collection Fragment
            case 3:
                break;
            //Inbox Fragment
            case 4:
                break;
            //Settings Fragment
            case 5:
                break;
            //Help Fragment
            case 6:
                break;
            //About Fragment
            case 7:
                break;
            //Search Fragment
            case 8:
                break;
        }

        return super.onPrepareOptionsMenu(menu);
    }

    //Radio Button commands
    public void onRadioButtonClicked(View view)
    {
        //Checking if the button is checked
        Boolean radioBool = ((RadioButton)view).isChecked();

        //All right, let's check which radio button is checked
        switch(view.getId())
        {
            //Search in category
            case R.id.searchfilter_myCollectionRBN:
                if(radioBool)
                {

                }
                break;
            case R.id.searchfilter_myInboxRBN:
                if(radioBool)
                {

                }
                break;
            case R.id.searchfilter_worldwideRBN:
                if(radioBool)
                {

                }
                break;
            //Order by category
            case R.id.searchfilter_ascendingRBN:
                if(radioBool)
                {

                }
                break;
            case R.id.searchfilter_descendingRBN:
                if(radioBool)
                {

                }
                break;
        }
    }

    /*@Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);

        myConfig = newConfig;
    }*/

    //Handle new Intents
    @Override
    protected void onNewIntent(Intent newIntent)
    {
        handleIntent(newIntent);
    }

    private void disableToolbarAction()
    {
        toolbarAddCard.setVisible(false);
    }

    private void handleIntent(Intent newIntent)
    {
        if(Intent.ACTION_SEARCH.equals(newIntent.getAction()))
        {
            searchQuery = newIntent.getStringExtra(SearchManager.QUERY);
            searchFinkel(searchQuery);
        }
    }

    private void lockDrawer()
    {
        navDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }
    private void lockDrawer(View viewToLock)
    {
        navDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, viewToLock);
    }

    private void unlockDrawer()
    {
        navDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        toolbar.setTitle(R.string.app_name);
    }
    private void unlockDrawer(View viewToUnlock)
    {
        navDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, viewToUnlock);
    }

    //Configuration methods
    public int getOrientation()
    {
        return myConfig.orientation;
    }

    //Fragment Change method
    public void changeFrag(int index)
    {
        //Before anything happens, lets invalidate and prepare the Toolbar
        invalidateOptionsMenu();
        switch(index)
        {
            //Login fragment
            case 0:
                fraggyManager.beginTransaction().replace(R.id.content_frame, loginFrag).commit();
                lockDrawer();
                myToolbar.hide();
                currentFrag = 0;
                break;
            //Register Fragment
            case 1:
                fraggyManager.beginTransaction().replace(R.id.content_frame, registerFrag).commit();
                lockDrawer();
                myToolbar.hide();
                currentFrag = 1;
                break;
            //ProfileFragment
            case 2:
                fraggyManager.beginTransaction().replace(R.id.content_frame, profileFrag).commit();
                unlockDrawer();
                myToolbar.show();
                currentFrag = 2;
                profileItem.selectItem();
                collectionItem.deselectItem();
                inboxItem.deselectItem();
                settingsItem.deselectItem();
                helpItem.deselectItem();
                aboutItem.deselectItem();
                navDrawer.closeDrawers();
                break;
            //Collection Fragment
            case 3:
                fraggyManager.beginTransaction().replace(R.id.content_frame, collectionFrag).commit();
                unlockDrawer();
                myToolbar.show();
                currentFrag = 3;
                profileItem.deselectItem();
                collectionItem.selectItem();
                inboxItem.deselectItem();
                settingsItem.deselectItem();
                helpItem.deselectItem();
                aboutItem.deselectItem();
                navDrawer.closeDrawers();
                break;
            //Inbox Fragment
            case 4:
                fraggyManager.beginTransaction().replace(R.id.content_frame, inboxFrag).commit();
                unlockDrawer();
                myToolbar.show();
                currentFrag = 4;
                profileItem.deselectItem();
                collectionItem.deselectItem();
                inboxItem.selectItem();
                settingsItem.deselectItem();
                helpItem.deselectItem();
                aboutItem.deselectItem();
                navDrawer.closeDrawers();
                break;
            //Settings Fragment
            case 5:
                currentFrag = 5;
                profileItem.deselectItem();
                collectionItem.deselectItem();
                inboxItem.deselectItem();
                settingsItem.selectItem();
                helpItem.deselectItem();
                aboutItem.deselectItem();
                navDrawer.closeDrawers();
                break;
            //Help Fragment
            case 6:
                currentFrag = 6;
                profileItem.deselectItem();
                collectionItem.deselectItem();
                inboxItem.deselectItem();
                settingsItem.deselectItem();
                helpItem.selectItem();
                aboutItem.deselectItem();
                navDrawer.closeDrawers();
                break;
            //About Fragment
            case 7:
                currentFrag = 7;
                profileItem.deselectItem();
                collectionItem.deselectItem();
                inboxItem.deselectItem();
                settingsItem.deselectItem();
                helpItem.deselectItem();
                aboutItem.selectItem();
                navDrawer.closeDrawers();
                break;
            //Search Fragment
            case 8:
                fraggyManager.beginTransaction().replace(R.id.content_frame, searchFrag).commit();
                unlockDrawer();
                myToolbar.show();
                currentFrag = 8;
                profileItem.deselectItem();
                collectionItem.deselectItem();
                inboxItem.deselectItem();
                settingsItem.deselectItem();
                helpItem.deselectItem();
                aboutItem.deselectItem();
                navDrawer.closeDrawers();
                break;
        }

    }

    //All of the following methods will not do anything, until the proper Web services are up.
    //Login method
    public void Login()
    {

    }

    //Recover Password
    public void recoverPassword()
    {

    }

    //Register new User
    public void registerNewUser()
    {
        profileItem.changeFragment(2);
    }

    //Animation Methods
    private void crossfadeAnim(Fragment fragIn, Fragment fragOut)
    {

    }

    //Search Method
    protected void searchFinkel(String newQuery)
    {
        changeFrag(8);
        searchFrag.setSearchQuery(newQuery);
        Toast.makeText(this, newQuery, Toast.LENGTH_LONG).show();
    }

    //Web ServiceASyncTask
    private class WebServiceTask extends AsyncTask<String, Void, String>
    {
        protected void onPreExecute()
        {

        }

        @Override
        protected String doInBackground(String... params)
        {
            String command = params[0];

            switch(command)
            {
                case "":
                    break;
            }
            return null;
        }

        protected void onProgressUpdate(Void... params)
        {

        }

        protected void onPostExecute(String result)
        {

        }
    }
}
