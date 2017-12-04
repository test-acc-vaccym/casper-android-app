# Casper ![alt text](https://i.imgur.com/BFezpxY.png)

Most companies that use Android phones have proprietary or classified information
stored somewhere on the phone. Casper is a Remote Administration Tool (RAT) that allows
admins to manipulate phones remotely to ensure data protection.

### Tested Phones
* Samsung Galaxy Note 4 (SM-N910H)
* Samsung Galaxy S3 (SPH-L710)
* Goldfish Emulator

### Samsung Setup
Download the correct OTA for your phone from https://firmware.mobi/  
Boot your phone into download mode (Volume Down + Home Button + Power Button)  
Flash the OTA via Odin (included in the OTA zip)  
Open the SuperSU app  
Open the Settings tab  
Set Default access to Grant  
Uncheck Show notifications  
Uncheck Re-authentication  
  
### Emulator Setup
This app currently uses the libsuperuser to ensure compatibility across various platforms. The library requires SuperSu to be installed. A full setup tutorial is [here](https://infosectrek.wordpress.com/2017/03/06/rooting-the-android-emulator/). I have started a manual solution (below) that will eventually be integrated into the code base at a later release. This goal is to eventually remove the SuperSU dependency from this project.  
  
### Future Emulator Setup
Set SELinux to permissive
```
$ setenforce 0
```
Download [Busybox](https://busybox.net/about.html)  
Push Busybox to /data/local/tmp and save it to /bin via adb  
```
$ ./adb push ./busybox-x86-64 /data/local/tmp/  
$ ./adb shell
$ mkdir -p /data/busybox
$ cp /data/local/tmp/busybox-x86_64 /bin/busybox
```
Go to your emulator path  
Example :C:\Users\User\AppData\Local\Android\sdk\system-images\android-23\google_apis\x86_64  
Make a backup of system.img and ramdisk.img  
Extract the ramdisk, change system partition to read/write in fstab and build.prop, and rebuild  
```
$ gunzip ramdisk // might need to rename to ramdisk.gz and use --force  
$ cpio -idv < ramdisk  
// change /system to rw in fstab.ranchu  
// change ro.secure from 1 to 0 in default.prop  
$ find . | cpio -o -H newc > ../ramdisk  
$ gzip ramdisk  
$ mv ramdisk.gz ramdisk.img // rename to ramdisk.img  
```
  
### Developer Notes
* Some phones might look at this long running process as a battery issues and disable it on boot. To remove this issue, go to Settings -> Batter -> Details and uncheck Casper

### TODO
- [ ] Finish manual root for emulator and implement a library to replace libsuperuser.
- [ ] Implement async server to remove Firebase Cloud Messaging dependency. This is an issue because it requires users to have Play Services installed and have a Google account on their phone.   
