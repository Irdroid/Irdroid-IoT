/*
 * Copyright 2013 Hari Krishna Dulipudi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.microcontrollerbg.irdroid.cloud;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import dev.dworks.libs.awizard.WizardActivity;
import dev.dworks.libs.awizard.model.PageList;
import dev.dworks.libs.awizard.model.ReviewItem;
import dev.dworks.libs.awizard.model.WizardModel;
import dev.dworks.libs.awizard.model.page.BranchPage;
import dev.dworks.libs.awizard.model.page.CustomerInfoPage;
import dev.dworks.libs.awizard.model.page.MultipleFixedChoicePage;
import dev.dworks.libs.awizard.model.page.ReviewPage;
import dev.dworks.libs.awizard.model.page.SingleFixedChoicePage;
import dev.dworks.libs.awizard.model.page.WelcomePage;

public class SandwichWizardModel extends WizardModel {
	

	private String welcome1;
	private String yourinfo1;
	

	public SandwichWizardModel( String welcome,String yourinfo ,Context context) {
        super(context);
     
       welcome1 = welcome;
       yourinfo1 = yourinfo;
     //  System.out.println(welcome1);      
     //  System.out.println(yourinfo1); 
       
       
    }
	public String getwelcome_variable() {
	    return welcome1;
	}
	
	 public void getReviewItems(ArrayList<ReviewItem> dest) {
	    }

	public String getinfovariable() {
	    return yourinfo1;
	}
	
	
    public PageList onNewRootPageList() {
 
 
    
       //  System.out.println(info1); 
    	
  WelcomePage branchPage = new WelcomePage(this,  "Welcome");
   
       



        return new PageList(branchPage,
        		
                new CustomerInfoPage(this, "Device setup")
                        .setRequired(true),
                new ReviewPage(this, "Review")
        );
    }
    
  
	
}