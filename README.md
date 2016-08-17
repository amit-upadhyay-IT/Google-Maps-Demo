# Google-Maps-Demo

While using compile 'com.google.android.gms:play-services:9.4.0' we have to add 

a) `multiDexEnabled true` in our `defaultConfig` in `build.gradle(app)`
b) The 9.4.0 or 9.0+ versions have some bug so downgrade the play-services version to 8.0+

Also to commit here again do : `git pull --rebase origin master`
