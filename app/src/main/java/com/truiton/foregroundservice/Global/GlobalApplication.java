/*
 * Copyright (c) 2018. Truiton (http://www.truiton.com/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 * Mohit Gupt (https://github.com/mohitgupt)
 *
 */

package com.truiton.foregroundservice.Global;

import android.app.Application;

/**
 * Created by prasannakumar.nair on 27-Nov-18.
 */

public class GlobalApplication extends Application {
   public static boolean isServiceOn=false;
   public static final String MyPREFERENCES = "MyPrefs" ;
   public static final String mUnlockTime = "Unlock";
   public static final String mLockTime = "Lock";
   public static final String mThreadAlive = "false";

}
