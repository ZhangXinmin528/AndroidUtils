# AndroidUtils


### [AppCompat][appCompat] | AndroidX

---
[![License](https://img.shields.io/badge/License%20-Apache%202-337ab7.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=19)
[ ![Download](https://api.bintray.com/packages/zhangxinmin528/AndroidUtils/AndroidUtils/images/download.svg) ](https://bintray.com/zhangxinmin528/AndroidUtils/AndroidUtils/_latestVersion)

<p align="center">
  <img alt="logo" src="https://github.com/ZhangXinmin528/AndroidUtils/blob/master/app/src/main/assets/ic_launcher.png"/>
</p>

Introduction
---
AndroidUtils is a tool library commonly used by Android developers. It encapsulates logs, time, rich text, file operations, etc. commonly used in Android development, and is convenient for everyone to use.

Usage
---

Add dependencies in build.gradle.

	1. Maven Center

	Gradle has a built-in configuration to access the Central Repository via HTTP.
	
	repositories {  
		mavenCentral()
	 } 
	 
	To access the Central Repository with Gradle via HTTPS you can define a Maven repository specifying the URL.
	
	repositories {   
		maven {     url "https://repo1.maven.org/maven2"   } 
		}
	
	dependencies {
		implementation 'io.github.zhangxinmin528.androidutils:coreutils:1.0.6'
	}
	
	2.Jcenter (No longer maintained)
	
    Adaptation AndroidX
	
    dependencies {
    		implementation 'com.zhangxm.utils:lib.coreutils:3.0.4'
    	}
    	
    Using AppCompat
	
	dependencies {
    		implementation 'com.zhangxm.utils:lib.coreutils:1.0.4'
    	}

Please refer to the code in the App Module related Activity in the project.

Communication
---
Email : zhangxinmin528@sina.com

License
---

    Copyright 2019 ZhangXinmin528

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


The End
---
If you are interested in AndroidUtils, don't forget to STAR [AndroidUtils](https://github.com/ZhangXinmin528/AndroidUtils).

Thank you for reading ~^o^~


[appCompat]: https://github.com/ZhangXinmin528/AndroidUtils/blob/master/README.md
[androidx]: https://github.com/ZhangXinmin528/AndroidUtils/blob/androidX/README.md