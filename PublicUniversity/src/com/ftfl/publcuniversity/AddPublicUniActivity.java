package com.ftfl.publcuniversity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.publicuniversity_v2.R;
import com.ftfl.publcuniversity.database.DataSources;
import com.ftfl.publcuniversity.util.PublicUniversity;

public class AddPublicUniActivity extends ActionBarActivity implements
		OnClickListener {

	Button btns_save = null;
	EditText etName = null;
	EditText etDescription = null;
	EditText etAddress = null;
	EditText etLatitude = null;
	EditText etLongitude = null;
	EditText etCourse = null;
	EditText etStudent = null;
	EditText etTeacher = null;

	String mName = null;
	String mDescription = null;
	String mAddress = null;
	String mLatitude = null;
	String mLongitude = null;
	String mCourse = null;
	String mStudent = null;
	String mTeachers = null;

	TextView tvItem = null;

	String mStrProfileID = "";
	String mID = "";
	Long mLId;
	DataSources mPublicUniversityDS = null;
	PublicUniversity mUpdatePublicUniversity = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_university);

		etName = (EditText) findViewById(R.id.addName);
		etDescription = (EditText) findViewById(R.id.addDescription);
		etAddress = (EditText) findViewById(R.id.addAddress);
		etLatitude = (EditText) findViewById(R.id.addLatitude);
		etLongitude = (EditText) findViewById(R.id.addLongitude);
		etCourse = (EditText) findViewById(R.id.addCourses);
		etStudent = (EditText) findViewById(R.id.addStudent);
		etTeacher = (EditText) findViewById(R.id.addTeachers);
		btns_save = (Button) findViewById(R.id.btnSave);
		btns_save.setOnClickListener(this);
		Intent mActivityIntent = getIntent();
		mID = mActivityIntent.getStringExtra("id");

		if (mID != null) {
			mLId = Long.parseLong(mID);

			/*
			 * get the activity which include all data from database according
			 * profileId of the clicked item.
			 */
			mPublicUniversityDS = new DataSources(this);
			mUpdatePublicUniversity = mPublicUniversityDS
					.singlePublicUniversityData(mLId);

			String mName = mUpdatePublicUniversity.getmName();
			String mDescription = mUpdatePublicUniversity.getmDescription();
			String mAddress = mUpdatePublicUniversity.getmAddress();
			String mLatitude = mUpdatePublicUniversity.getmLatitude();
			String mLongitude = mUpdatePublicUniversity.getmLongitude();
			String mCourse = mUpdatePublicUniversity.getmCourse();
			String mStudent = mUpdatePublicUniversity.getmStudent();
			String mTeachers = mUpdatePublicUniversity.getmTeachers();

			// set the value of database to the text field.

			etName.setText(mName);
			etDescription.setText(mDescription);
			etAddress.setText(mAddress);
			etLatitude.setText(mLatitude);
			etLongitude.setText(mLongitude);
			etCourse.setText(mCourse);
			etStudent.setText(mStudent);
			etTeacher.setText(mTeachers);

			/*
			 * change button name
			 */
			btns_save.setText("Update");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Toast toast = null;
		switch (v.getId()) {

		case R.id.btnSave:

			mName = etName.getText().toString();
			mDescription = etDescription.getText().toString();
			mAddress = etAddress.getText().toString();
			mLatitude = etLatitude.getText().toString();
			mLongitude = etLongitude.getText().toString();

			mCourse = etCourse.getText().toString();
			mStudent = etStudent.getText().toString();
			mTeachers = etTeacher.getText().toString();

			PublicUniversity publicUniversityInsert = new PublicUniversity();
			publicUniversityInsert.setmName(mName);
			publicUniversityInsert.setmDescription(mDescription);
			publicUniversityInsert.setmAddress(mAddress);
			publicUniversityInsert.setmLatitude(mLatitude);
			publicUniversityInsert.setmLongitude(mLongitude);
			publicUniversityInsert.setmCourse(mCourse);
			publicUniversityInsert.setmStudent(mStudent);
			publicUniversityInsert.setmTeachers(mTeachers);
			// till ok

			/*
			 * if update is needed then update otherwise submit
			 */
			if (mID != null) {

				mLId = Long.parseLong(mID);

				mPublicUniversityDS = new DataSources(this);

				if (mPublicUniversityDS
						.updateData(mLId, publicUniversityInsert) == true) {
					toast = Toast.makeText(this, "Successfully Updated.",
							Toast.LENGTH_LONG);
					toast.show();
					startActivity(new Intent(AddPublicUniActivity.this,
							PublicUniListActivity.class));
					finish();
				} else {
					toast = Toast.makeText(this, "Not Updated.",
							Toast.LENGTH_LONG);
					toast.show();
				}
			} else {
				mPublicUniversityDS = new DataSources(this);
				if (mPublicUniversityDS.insert(publicUniversityInsert) == true) {
					toast = Toast.makeText(this, "Successfully Saved.",
							Toast.LENGTH_LONG);
					toast.show();

					startActivity(new Intent(AddPublicUniActivity.this,
							PublicUniListActivity.class));

					// finish();
				} else {
					toast = Toast.makeText(this, "Not Saved.",
							Toast.LENGTH_LONG);
					toast.show();
				}
			}
			break;
		}

	}
}
