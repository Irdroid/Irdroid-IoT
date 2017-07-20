package com.microcontrollerbg.irdroid.cloud;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import dev.dworks.libs.awizard.WizardActivity;

public class OrderWizardActivity extends WizardActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		String wilcomen;
		String yourinfonen;
		
		wilcomen = "OK";
		yourinfonen = "well, well";
		mWizardModel = new SandwichWizardModel(wilcomen,yourinfonen,this);
		//mWizardModel = new SandwichWizardModel(Resources.getSystem().getString(R.string.welcome),Resources.getSystem().getString(R.string.yourinfo),this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wizard);
		setWizardModel(mWizardModel);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}


	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.order, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.action_cancel:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
